package com.saechan.collectormarket.point.dto.response;

import com.saechan.collectormarket.point.model.entity.PointTransaction;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PointTransactionDto {

  private Double transactionAmount;

  private Double currentPoint;

  private LocalDateTime transactionDt;

  private Long memberId;

  public static PointTransactionDto from(PointTransaction pointTransaction){
    return PointTransactionDto.builder()
        .transactionAmount(pointTransaction.getTransactionAmount())
        .currentPoint(pointTransaction.getCurrentPoint())
        .transactionDt(pointTransaction.getTransactionDt())
        .memberId(pointTransaction.getMember().getId())
        .build();
  }
}

