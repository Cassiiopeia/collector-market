package com.saechan.collectormarket.product.model.repository;

import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.product.model.type.ProductCategory;
import com.saechan.collectormarket.product.model.type.ProductStatus;
import com.saechan.collectormarket.transaction.model.type.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT product FROM Product product"
      + " WHERE (product.name LIKE %:name% )"
      + " AND (:minPrice IS NULL OR product.price >= :minPrice)"
      + " AND (:maxPrice IS NULL OR product.price <= :maxPrice)"
      + " AND (:productStatus IS NULL OR product.productStatus = :productStatus)"
      + " AND (:productCategory IS NULL OR product.productCategory = :productCategory)"
      + " AND (:transactionStatus IS NULL OR product.transactionStatus = :transactionStatus)")
  Page<Product> findByNameWithFiltering(
      @Param("name") String name,
      @Param("minPrice") Double minPrice,
      @Param("maxPrice") Double maxPrice,
      @Param("productStatus") ProductStatus productStatus,
      @Param("productCategory") ProductCategory productCategory,
      @Param("transactionStatus") TransactionStatus transactionStatus,
      Pageable pageable);
}
