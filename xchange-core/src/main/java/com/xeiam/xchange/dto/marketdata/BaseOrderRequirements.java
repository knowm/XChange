package com.xeiam.xchange.dto.marketdata;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

import java.math.BigDecimal;

public class BaseOrderRequirements implements OrderRequirements {
  public final int amountScale;
  public final BigDecimal amountMinimum;
  public final int priceScale;

  private BaseOrderRequirements(int amountScale, BigDecimal amountMinimum, int priceScale) {
    this.amountScale = amountScale;
    this.amountMinimum = amountMinimum;
    amountMinimum.setScale(amountScale);
    this.priceScale = priceScale;
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


  @Override
  public void verifyOrder(LimitOrder order) {

    verifyOrder((Order)order);

    BigDecimal price = order.getLimitPrice().stripTrailingZeros();

    if (price.scale() > price.scale()) {
      throw new IllegalArgumentException("Unsupported price scale " + price.scale());
    }
  }

  @Override
  public void verifyOrder(MarketOrder order) {

    verifyOrder((Order) order);
  }

  protected void verifyOrder(Order order) {

    BigDecimal amount = order.getTradableAmount().stripTrailingZeros();

    if (amount.scale() > amountMinimum.scale()) {
      throw new IllegalArgumentException("Unsupported amount scale " + amount.scale());
    } else if (amount.compareTo(amountMinimum) < 0) {
      throw new IllegalArgumentException("Order amount less than minimum");
    }
  }
}
