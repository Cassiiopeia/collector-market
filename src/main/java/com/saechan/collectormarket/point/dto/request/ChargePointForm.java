package com.saechan.collectormarket.point.dto.request;

import com.saechan.collectormarket.global.util.constraint.ValidAccount;
import com.saechan.collectormarket.global.util.constraint.ValidChargePoint;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChargePointForm {

  @ValidChargePoint
  private Double chargeAmount;

  @ValidAccount
  private String accountNumber;

}
