package com.xeiam.xchange.hitbtc.dto.marketdata;

import com.xeiam.xchange.dto.marketdata.BaseTradeServiceHelper;

import java.math.BigDecimal;

public class HitbtcTradeServiceHelper extends BaseTradeServiceHelper {
  private final BigDecimal takeLiquidityRate;
  private final BigDecimal provideLiquidityRate;

  public HitbtcTradeServiceHelper(BigDecimal amountMinimum, int priceScale, BigDecimal takeLiquidityRate, BigDecimal provideLiquidityRate) {
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
