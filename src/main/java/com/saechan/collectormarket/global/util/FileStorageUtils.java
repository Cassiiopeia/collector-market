package com.saechan.collectormarket.global.util;

import com.saechan.collectormarket.global.s3.type.UploadFileFormat;
import com.saechan.collectormarket.global.exception.ErrorCode;
import com.saechan.collectormarket.global.s3.FileStorageException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileStorageUtils {

  private static final Long MAX_FILE_SIZE = (long) 5 * 1024 * 1024; // 5MB 업로드 제한
  private static final int MAX_UPLOAD_COUNT = 10; // 최대 업로드 갯수

  // 파일 이름 생성
  public static String getFileName(MultipartFile file, String property, long id) {
    return property + "_"
        + Long.toString(id) + "_"
        + LocalDateTime.now().toString().replaceAll("[:\\-\\.]", "") + "_"
        + UUID.randomUUID().toString().replaceAll("-","").substring(0,9)+ "_"
        + file.getOriginalFilename();
  }

  // 파일 검증
  public static void validateFile(MultipartFile file) {
    // 파일 포맷 검증
    if (!UploadFileFormat.contains(file.getContentType())) {
      throw new FileStorageException(ErrorCode.INVALID_FILE_FORMAT);
    }

    // 파일 크기 검증
    if (file.getSize() > MAX_FILE_SIZE) {
      throw new FileStorageException(ErrorCode.FILE_SIZE_EXCEEDED);
    }
  }

  // 파일 업로드 최대 개수 검증
  public static void validateMaxUploadCount(List<MultipartFile> multipartFiles){
    if (multipartFiles.size() > MAX_UPLOAD_COUNT) {
      throw new FileStorageException(ErrorCode.FILE_UPLOAD_COUNT_LIMIT);
    }
  }

  // URL 에서 파일 이름 가져오기
  public static String getFileNameFromUrl(String imageUrl) {
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    log.info("fileName is : {}", fileName);
    return fileName;
  }

}
