package org.knowm.xchange.dto.meta;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

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

  /**
   * Constructor
   *
   * @param tradingFee
   * @param minimumAmount
   * @param priceScale
   */
  public MarketMetaData(@JsonProperty("tradingFee") BigDecimal tradingFee, @JsonProperty("minAmount") BigDecimal minimumAmount,
      @JsonProperty("priceScale") int priceScale) {
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

  @Override
  public String toString() {
    return "MarketMetaData{" + "tradingFee=" + tradingFee + ", minimumAmount=" + minimumAmount + ", priceScale=" + priceScale + '}';
  }
}
