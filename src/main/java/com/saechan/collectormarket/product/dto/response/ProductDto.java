package com.saechan.collectormarket.product.dto.response;

import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.product.model.type.DeliveryMethod;
import com.saechan.collectormarket.product.model.type.ProductCategory;
import com.saechan.collectormarket.product.model.type.ProductStatus;
import com.saechan.collectormarket.transaction.model.type.TransactionStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ProductDto {

  private Long id;

  private String name;

  private String description;

  private Double price;

  private List<ProductImageDto> images;

  private ProductStatus productStatus;

  private DeliveryMethod deliveryMethod;

  private Double shippingCost;

  private ProductCategory productCategory;

  private TransactionStatus transactionStatus;

  private Long storeId;

  public static ProductDto from(Product product){
    return ProductDto.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .images(product.getProductImages().stream()
            .map(ProductImageDto::from)
            .collect(Collectors.toList()))
        .productStatus(product.getProductStatus())
        .deliveryMethod(product.getDeliveryMethod())
        .shippingCost(product.getShippingCost())
        .productCategory(product.getProductCategory())
        .transactionStatus(product.getTransactionStatus())
        .storeId(product.getStore().getId())
        .build();
  }
}

