package com.saechan.collectormarket.member.model.entity;

import com.saechan.collectormarket.global.entity.BaseEntity;
import com.saechan.collectormarket.member.model.type.UserRole;
import com.saechan.collectormarket.store.model.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  private String email;

  @Column(nullable = false)
  private String password;

  private String name;

  private String phone;

  private Double point;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  private String emailAuthCode;

  private LocalDateTime emailAuthCreateDt;

  private Boolean activated;

  @OneToOne(mappedBy = "member", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
  private Store store;

  public static Long getStoreId(Member member){
    return member.getStore() == null ? -1 : member.getStore().getId();
  }
}
