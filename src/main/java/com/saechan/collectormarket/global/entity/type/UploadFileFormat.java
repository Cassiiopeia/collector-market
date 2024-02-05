package com.saechan.collectormarket.global.entity.type;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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