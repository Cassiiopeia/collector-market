package com.saechan.collectormarket.store.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StoreUpdateForm {

  @NotNull
  @Size(max = 20, message = "이름의 길이는 20자 이하여야합니다.")
  private String name;

  @Size(max = 500, message = "상세정보의 길이는 500자 이하여합니다.")
  private String description;

  private String image;

}
