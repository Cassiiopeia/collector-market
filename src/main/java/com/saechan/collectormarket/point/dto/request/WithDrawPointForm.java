package com.saechan.collectormarket.point.dto.request;

import com.saechan.collectormarket.global.util.constraint.ValidAccount;
import com.saechan.collectormarket.global.util.constraint.ValidWithDrawPoint;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WithDrawPointForm {

  @ValidWithDrawPoint
  private Double withDrawAmount;

  @ValidAccount
  private String accountNumber;

}
