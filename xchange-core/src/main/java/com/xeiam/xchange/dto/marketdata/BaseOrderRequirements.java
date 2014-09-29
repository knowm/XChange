package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;

public class BaseOrderRequirements implements OrderRequirements {
  public final int amountScale;
  public final BigDecimal amountMinimum;
  public final int priceScale;

  private BaseOrderRequirements(int amountScale, BigDecimal amountMinimum, int priceScale) {
    this.amountScale = amountScale;
    this.amountMinimum = amountMinimum;
    this.priceScale = priceScale;
  }

  @Override
  public int getAmountScale() {
    return amountScale;
  }

  @Override
  public BigDecimal getAmountMinimum() {
    return amountMinimum;
  }

  @Override
  public int getPriceScale() {
    return priceScale;
  }

  @Override
  public BigDecimal getAmountStep() {
    return BigDecimal.ONE.movePointLeft(amountScale);
  }

  @Override
  public BigDecimal getPriceStep() {
    return BigDecimal.ONE.movePointLeft(priceScale);
  }

}
