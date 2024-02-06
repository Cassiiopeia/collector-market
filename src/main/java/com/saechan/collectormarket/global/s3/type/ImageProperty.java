package com.saechan.collectormarket.global.s3.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageProperty {
  // 파일 생성 이름(속성)

  PRODUCT("product"),

  STORE("store"),

  REVIEW("review");

  private final String property;

}
