package com.saechan.collectormarket.global.s3;

import com.saechan.collectormarket.global.exception.CustomException;
import com.saechan.collectormarket.global.exception.ErrorCode;

public class FileStorageException extends CustomException {

  public FileStorageException(ErrorCode errorCode) {
    super(errorCode);
  }
}
