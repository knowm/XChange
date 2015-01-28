package com.xeiam.xchange.hitbtc.dto.trade;

import java.math.BigDecimal;

import com.xeiam.xchange.dto.trade.TradeMetaInfo;

public class HitbtcTradeMetaData extends TradeMetaInfo {

  private final BigDecimal takeLiquidityRate;
  private final BigDecimal provideLiquidityRate;

  /**
   * Constructor
   *
   * @param tradingFee
   * @param amountMinimum
   * @param priceScale
   * @param takeLiquidityRate
   * @param provideLiquidityRate
   */
  public HitbtcTradeMetaData(BigDecimal tradingFee, BigDecimal amountMinimum, int priceScale, BigDecimal takeLiquidityRate,
      BigDecimal provideLiquidityRate) {

    super(tradingFee, amountMinimum, priceScale, null);
    this.takeLiquidityRate = takeLiquidityRate;
    this.provideLiquidityRate = provideLiquidityRate;
  }

  public BigDecimal getTakeLiquidityRate() {
    return takeLiquidityRate;
  }

  public BigDecimal getProvideLiquidityRate() {
    return provideLiquidityRate;
  }
}
