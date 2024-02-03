package com.saechan.collectormarket.product.dto.request;

import com.saechan.collectormarket.global.util.constraint.ValidName;
import com.saechan.collectormarket.product.model.type.DeliveryMethod;
import com.saechan.collectormarket.product.model.type.ProductCategory;
import com.saechan.collectormarket.product.model.type.ProductStatus;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCreateForm {

  @ValidName
  private String name;

  @NotNull
  @Size(max = 500, message = "500자를 넘으면안됩니다.")
  private String description;

  @NotNull
  private Double price;

  @NotNull
  private ProductStatus productStatus;

  private DeliveryMethod deliveryMethod;

  @NotNull
  private Double shippingCost;

  @NotNull
  private ProductCategory productCategory;

  private MultipartFile[] images;
}
