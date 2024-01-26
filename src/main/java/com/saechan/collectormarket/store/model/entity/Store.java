package com.saechan.collectormarket.store.model.entity;

import com.saechan.collectormarket.chat.model.entity.ChatRoomStore;
import com.saechan.collectormarket.global.entity.BaseEntity;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.review.model.entity.Review;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class Store extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20, nullable = false)
  private String name;

  @Column(length = 500)
  private String description;

  private String image;

  private LocalDateTime openDt;

  @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
  private List<Product> products;

  @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
  private List<Review> reviews;

  private Double reviewRating;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "store", orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<ChatRoomStore> chatRooms = new HashSet<>();
}

