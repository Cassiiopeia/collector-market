package com.saechan.collectormarket.global.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
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
    try {
      // 형식 검증
      FileStorageUtils.validateFile(file);

      // 파일 이름 설정
      String fileName = FileStorageUtils.getFileName(file, property, id);

      // 파일 업로드
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.getSize());
      metadata.setContentType(file.getContentType());
      amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

      // URL 반환
      String fileUrl = amazonS3.getUrl(bucket, fileName).toString();
      log.info("파일 업로드 완료 URL : {}", fileUrl);
      return fileUrl;

    } catch (IOException e) {
      log.error("파일 업로드 오류 : IOException : {}", e.getMessage());
      throw new FileStorageException(ErrorCode.FILE_UPLOAD_ERROR);
    } catch (AmazonServiceException e) {
      log.error("파일 업로드 오류 : AmazonServiceException : {}", e.getMessage());
      throw new FileStorageException(ErrorCode.AWS_SERVICE_EXCEPTION);
    } catch (AmazonClientException e) {
      log.error("파일 업로드 오류 : AmazonClientException : {}", e.getMessage());
      throw new FileStorageException(ErrorCode.AWS_CLIENT_EXCEPTION);
    }
  }

  public List<String> uploadFiles(
      List<MultipartFile> multipartFiles,
      String property,
      Long id
  ) {

    // 최대 업로드 개수 확인
    FileStorageUtils.validateMaxUploadCount(multipartFiles);

    // 각각 파일 업로드
    List<String> urls = new ArrayList<>();
    multipartFiles.forEach(multipartFile -> {
      urls.add(uploadFile(multipartFile, property, id));
    });
    log.info("복수 파일 업로드 완료 URL : {}", urls.toString());
    return urls;
  }

  public void deleteFile(String image) {
    try {
      // URL 에서 파일 이름 추출
      String fileName = FileStorageUtils.getFileNameFromUrl(image);

      // S3에서 파일 삭제
      amazonS3.deleteObject(bucket, fileName);
      log.info("파일 삭제완료 : {}", fileName);

    } catch (AmazonClientException e) {
      log.error("파일 삭제 오류 : AmazonClientException : {}", e.getMessage());
      throw new FileStorageException(ErrorCode.FILE_DELETE_ERROR);
    }
  }

  // S3에서 파일 존재 여부 확인
  private boolean isPresent(String bucket, String filename) {
    return amazonS3.doesObjectExist(bucket, filename);
  }


}
