package com.saechan.collectormarket.global.s3.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UploadFileFormat {
  JPG("image/jpeg"),
  JPEG("image/jpeg"),
  PNG("image/png");

  private final String mimeType;

  public static boolean contains(String contentType) {
    for (UploadFileFormat format : values()) {
      if (format.mimeType.equals(contentType)) {
        return true;
      }
    }
    return false;
  }
}