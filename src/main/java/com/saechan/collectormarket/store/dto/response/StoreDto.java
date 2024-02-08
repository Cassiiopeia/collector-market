package com.saechan.collectormarket.store.dto.response;

import com.saechan.collectormarket.chat.model.entity.ChatRoomStore;
import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.review.model.entity.Review;
import com.saechan.collectormarket.store.model.entity.Store;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreDto {

  private String name;

  private String description;

  private String image;

  private LocalDateTime openDt;

  private List<Long> productIds;

  private List<Long> reviewIds;

  private Double reviewRating;

  private Long memberId;

  private List<Long> chatRoomStoreIds;

  public static StoreDto from(Store store) {
    return StoreDto.builder()
        .name(store.getName())
        .description(store.getDescription())
        .image(store.getImageUrl())
        .openDt(store.getOpenDt())
        .productIds(store.getProducts().stream()
            .map(Product::getId)
            .collect(Collectors.toList()))
        .reviewIds(store.getReviews().stream()
            .map(Review::getId)
            .collect(Collectors.toList()))
        .reviewRating(store.getReviewRating())
        .memberId(store.getMember().getId())
        .chatRoomStoreIds(store.getChatRoomStores().stream()
            .map(ChatRoomStore::getId)
            .collect(Collectors.toList()))
        .build();
  }
}
