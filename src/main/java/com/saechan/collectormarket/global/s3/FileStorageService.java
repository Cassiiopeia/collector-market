package com.saechan.collectormarket.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.saechan.collectormarket.global.exception.ErrorCode;
import com.saechan.collectormarket.global.util.FileStorageUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public String uploadFile(MultipartFile file, String property, Long id) {
    // 형식 검증
    FileStorageUtils.validateFile(file);

    // 파일 이름 설정
    String fileName = FileStorageUtils.getFileName(file, property, id);

    // 파일 업로드
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.getSize());
      metadata.setContentType(file.getContentType());
      amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
    } catch (IOException e) {
      throw new FileStorageException(ErrorCode.FILE_UPLOAD_ERROR);
    }

    // URL 반환
    String fileUrl = amazonS3.getUrl(bucket, fileName).toString();
    log.info("파일 업로드 완료 URL : {}", fileUrl);
    return fileUrl;
  }

  public List<String> uploadFiles(
      List<MultipartFile> multipartFiles,
      String property,
      Long id
  ) {
    List<String> urls = new ArrayList<>();
    multipartFiles.forEach(multipartFile -> {
      urls.add(uploadFile(multipartFile, property, id));
    });
    return urls;
  }

  public void deleteFile(String image) {
    try {
      // URL 에서 파일 이름 추출
      String fileName = FileStorageUtils.getFileNameFromUrl(image);

      // S3에서 파일 삭제
      amazonS3.deleteObject(bucket, fileName);
      log.info("파일 삭제 완료 : {}", fileName);

    } catch (Exception e) {
      throw new FileStorageException(ErrorCode.FILE_DELETE_ERROR);
    }
  }

}
