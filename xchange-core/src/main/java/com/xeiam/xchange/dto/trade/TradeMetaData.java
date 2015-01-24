package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;

import com.xeiam.xchange.dto.Order;

public class TradeMetaData {

  private final BigDecimal tradingFee;
  private final BigDecimal amountMinimum;
  private final int priceScale;

  /**
   *
   * Constructor
   *
   * @param tradingFee
   * @param amountMinimum
   * @param priceScale
   */
  public TradeMetaData(BigDecimal tradingFee, BigDecimal amountMinimum, int priceScale) {

    this.tradingFee = tradingFee;
    this.amountMinimum = amountMinimum;
    this.priceScale = priceScale;
  }

  public BigDecimal getTradingFee() {
    return tradingFee;
  }

  public BigDecimal getAmountMinimum() {

    return amountMinimum;
  }

  public int getPriceScale() {

    return priceScale;
  }

  public BigDecimal getAmountStep() {

    return BigDecimal.ONE.movePointLeft(amountMinimum.scale());
  }

  public BigDecimal getPriceStep() {

    return BigDecimal.ONE.movePointLeft(priceScale);
  }

  public void verifyOrder(LimitOrder order) {

    verifyOrder((Order) order);

    BigDecimal price = order.getLimitPrice().stripTrailingZeros();

    if (price.scale() > priceScale) {
      throw new IllegalArgumentException("Unsupported price scale " + price.scale());
    }
  }

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

    return "BaseMarketMetadata{" + "amountMinimum=" + amountMinimum + ", priceScale=" + priceScale + '}';
  }
}
