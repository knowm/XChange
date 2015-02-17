package com.xeiam.xchange.dto;

import java.math.BigDecimal;

public class MarketMetaData {
  /**
   * Trading fee (fraction)
   */
  private final BigDecimal tradingFee;

  /**
   * Minimum trade amount, the scale indicates price step
   */
  private final BigDecimal minimumAmount;

  public MarketMetaData(BigDecimal tradingFee, BigDecimal minimumAmount) {
    this.tradingFee = tradingFee;
    this.minimumAmount = minimumAmount;
  }

  public BigDecimal getTradingFee() {
    return tradingFee;
  }

  public BigDecimal getMinimumAmount() {
    return minimumAmount;
  }

}
