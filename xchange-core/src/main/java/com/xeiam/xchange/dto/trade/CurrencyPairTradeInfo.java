package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;

public class CurrencyPairTradeInfo {

  private final BigDecimal tradingFee;
  private final BigDecimal minimumAmount;
  private final Integer scaleFactor;

  /**
   * Constructor
   *
   * @param tradingFee
   * @param minimumAmount
   * @param scaleFactor
   */
  public CurrencyPairTradeInfo(BigDecimal tradingFee, BigDecimal minimumAmount, Integer scaleFactor) {

    this.tradingFee = tradingFee;
    this.minimumAmount = minimumAmount;
    this.scaleFactor = scaleFactor;
  }

  public BigDecimal getTradingFee() {
    return tradingFee;
  }

  public BigDecimal getMinimumAmount() {

    return minimumAmount;
  }

  public Integer getScaleFactor() {

    return scaleFactor;
  }

  @Override
  public String toString() {
    return "CurrencyPairTradeMetaInfo [tradingFee=" + tradingFee + ", minimumAmount=" + minimumAmount + ", scaleFactor=" + scaleFactor + "]";
  }

}
