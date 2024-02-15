package com.saechan.collectormarket.store.dto.response;

import com.saechan.collectormarket.store.model.entity.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreSearchDto {

  private Long id;

  private String name;

  private String imageUrl;

  private Integer productNumber;

  public static StoreSearchDto from(Store store){
    return StoreSearchDto.builder()
        .id(store.getId())
        .name(store.getName())
        .imageUrl(store.getImageUrl())
        .productNumber(store.getProducts().size())
        .build();
  }
}
