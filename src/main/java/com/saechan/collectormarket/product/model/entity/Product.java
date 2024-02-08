package com.saechan.collectormarket.product.model.entity;

import com.saechan.collectormarket.global.entity.BaseEntity;
import com.saechan.collectormarket.product.model.type.DeliveryMethod;
import com.saechan.collectormarket.product.model.type.ProductCategory;
import com.saechan.collectormarket.product.model.type.ProductStatus;
import com.saechan.collectormarket.store.model.entity.Store;

import com.saechan.collectormarket.transaction.model.type.TransactionStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20, nullable = false)
  private String name;

  @Column(length = 500)
  private String description;

  @Column(nullable = false)
  private Double price;

  @Builder.Default
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<ProductImage> productImages = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private ProductStatus productStatus;

  @Enumerated(EnumType.STRING)
  private DeliveryMethod deliveryMethod;

  private Double shippingCost;

  @Enumerated(EnumType.STRING)
  private ProductCategory productCategory;

  @Enumerated(EnumType.STRING)
  private TransactionStatus transactionStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;
}

