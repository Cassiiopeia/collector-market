package com.saechan.collectormarket.global.entity;

import jakarta.persistence.EntityListeners;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(value = {AuditingEntityListener.class})

public class BaseEntity {

  @CreatedDate
  private LocalDateTime createAt;

  @LastModifiedDate
  private LocalDateTime modifiedAt;

}
