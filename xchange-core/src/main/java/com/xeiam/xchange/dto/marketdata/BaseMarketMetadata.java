package com.xeiam.xchange.dto.marketdata;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

import java.math.BigDecimal;

public class BaseMarketMetadata implements MarketMetadata {
  private final int amountScale;
  private final BigDecimal amountMinimum;
  private final int priceScale;
  private final BigDecimal limitOrderFeeFactor;
  private final BigDecimal marketOrderFeeFactor;

  public BaseMarketMetadata(int amountScale, BigDecimal amountMinimum, int priceScale, BigDecimal limitOrderFeeFactor, BigDecimal marketOrderFeeFactor) {

    this.amountScale = amountScale;
    this.amountMinimum = amountMinimum.setScale(amountScale);
    this.priceScale = priceScale;
    this.limitOrderFeeFactor = limitOrderFeeFactor;
    this.marketOrderFeeFactor = marketOrderFeeFactor;
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
  public BigDecimal getMarketOrderFeeFactor() {
    return marketOrderFeeFactor;
  }

  @Override
  public BigDecimal getLimitOrderFeeFactor() {
    return limitOrderFeeFactor;
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

  @Override
  public String toString() {
    return "BaseMarketMetadata{" +
        "amountScale=" + amountScale +
        ", amountMinimum=" + amountMinimum +
        ", priceScale=" + priceScale +
        ", limitOrderFeeFactor=" + limitOrderFeeFactor +
        ", marketOrderFeeFactor=" + marketOrderFeeFactor +
        '}';
  }
}
