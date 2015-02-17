package com.xeiam.xchange.anx.v2.dto;

import java.math.BigDecimal;

public class ANXMarketMetaData {
  public BigDecimal minimumAmount;
  public BigDecimal maximumAmount;

  public ANXMarketMetaData(BigDecimal minimumAmount, BigDecimal maximumAmount) {
    this.minimumAmount = minimumAmount;
    this.maximumAmount = maximumAmount;
  }
}
