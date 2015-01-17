package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

public class BaseTradeServiceHelper implements TradeServiceHelper {

  private final BigDecimal amountMinimum;
  private final int priceScale;

  public BaseTradeServiceHelper(BigDecimal amountMinimum, int priceScale) {

    this.amountMinimum = amountMinimum;
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

    return BigDecimal.ONE.movePointLeft(amountMinimum.scale());
  }

  @Override
  public BigDecimal getPriceStep() {

    return BigDecimal.ONE.movePointLeft(priceScale);
  }

  @Override
  public void verifyOrder(LimitOrder order) {

    verifyOrder((Order) order);

    BigDecimal price = order.getLimitPrice().stripTrailingZeros();

    if (price.scale() > priceScale) {
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
    }
    else if (amount.compareTo(amountMinimum) < 0) {
      throw new IllegalArgumentException("Order amount less than minimum");
    }
  }

  @Override
  public String toString() {

    return "BaseMarketMetadata{" + "amountMinimum=" + amountMinimum + ", priceScale=" + priceScale + '}';
  }
}
