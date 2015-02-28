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

  private final int priceScale;

  public MarketMetaData(BigDecimal tradingFee, BigDecimal minimumAmount, int priceScale) {
    this.tradingFee = tradingFee;
    this.minimumAmount = minimumAmount;
    this.priceScale = priceScale;
  }

  public BigDecimal getTradingFee() {
    return tradingFee;
  }

  public BigDecimal getMinimumAmount() {
    return minimumAmount;
  }

  public int getPriceScale() {
    return priceScale;
  }
}
