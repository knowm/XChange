package com.xeiam.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.xeiam.xchange.dto.trade.TradeMetaData;

public class HitbtcTradeMetaData extends TradeMetaData {

  private final BigDecimal takeLiquidityRate;
  private final BigDecimal provideLiquidityRate;

  public HitbtcTradeMetaData(BigDecimal amountMinimum, int priceScale, BigDecimal takeLiquidityRate, BigDecimal provideLiquidityRate) {
    super(amountMinimum, priceScale);
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
