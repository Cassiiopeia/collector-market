package com.saechan.collectormarket.product.dto.request;

import com.saechan.collectormarket.product.model.type.DeliveryMethod;
import com.saechan.collectormarket.product.model.type.ProductCategory;
import com.saechan.collectormarket.product.model.type.ProductStatus;
import com.saechan.collectormarket.transaction.model.type.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class ProductUpdateForm {

  @NotNull
  @Size(max = 20)
  private String name;

  @NotNull
  @Size(max = 500)
  private String description;

  private List<MultipartFile> images;

  @NotNull
  private Double price;

  @NotNull
  private ProductStatus productStatus;

  @NotNull
  private DeliveryMethod deliveryMethod;

  private Double shippingCost;

  @NotNull
  private ProductCategory productCategory;

  @NotNull
  private TransactionStatus transactionStatus;


}
