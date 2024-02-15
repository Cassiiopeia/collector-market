package com.saechan.collectormarket.product.dto.request;

import com.saechan.collectormarket.product.model.type.ProductCategory;
import com.saechan.collectormarket.product.model.type.ProductStatus;
import com.saechan.collectormarket.transaction.model.type.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Getter
@Builder
public class ProductSearchForm {

  @NotNull
  @Size(max = 20)
  private String name;

  @Default
  private Double minPrice = 0.0;

  @Default
  private Double maxPrice = Double.POSITIVE_INFINITY;

  private ProductStatus productStatus;

  private ProductCategory productCategory;

  private TransactionStatus transactionStatus;
}
