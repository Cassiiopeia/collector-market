package com.saechan.collectormarket.product.model.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "product")
@Setting(settingPath = "es/product-search-setting.json")
@Mapping(mappingPath = "es/product-search-mapping.json")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductSearch {
  @Id
  private String id;

  @Field(type = FieldType.Text, name = "name")
  private String name;

  @Field(type = FieldType.Text, name = "description")
  private String description;

  @Field(type = FieldType.Double, name = "price")
  private Double price;

  @Field(type = FieldType.Keyword, name = "productStatus")
  private String productStatus;

  @Field(type = FieldType.Keyword, name = "deliveryMethod")
  private String deliveryMethod;

  @Field(type = FieldType.Keyword, name = "productCategory")
  private String productCategory;

  @Field(type = FieldType.Keyword, name = "transactionStatus")
  private String transactionStatus;

  public static ProductSearch from(Product product){
    return ProductSearch.builder()
        .id(product.getId().toString())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .productStatus(product.getProductStatus().name())
        .deliveryMethod(product.getDeliveryMethod().name())
        .productCategory(product.getProductCategory().name())
        .transactionStatus(product.getTransactionStatus().name())
        .build();
  }
}
