package com.saechan.collectormarket.transaction.model.entity;

import com.saechan.collectormarket.global.entity.BaseEntity;
import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.store.model.entity.Store;
import com.saechan.collectormarket.transaction.model.type.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
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
public class Transaction extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime transactionStartDt;

  private LocalDateTime transactionCompleteDt;

  @Enumerated(EnumType.STRING)
  private TransactionStatus transactionStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id")
  private Store buyer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

}
