package com.xeiam.xchange.anx.v2.dto.meta;

import java.math.BigDecimal;

public class ANXMarketMetaData {
  public BigDecimal minimumAmount;
  public BigDecimal maximumAmount;
  public int priceScale;

  public ANXMarketMetaData() {
  }

  public ANXMarketMetaData(BigDecimal minimumAmount, BigDecimal maximumAmount, int priceScale) {
    this.minimumAmount = minimumAmount;
    this.maximumAmount = maximumAmount;
    this.priceScale = priceScale;
  }
}
