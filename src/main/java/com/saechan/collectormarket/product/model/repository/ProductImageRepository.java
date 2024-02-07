package com.saechan.collectormarket.product.model.repository;

import com.saechan.collectormarket.product.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
}
