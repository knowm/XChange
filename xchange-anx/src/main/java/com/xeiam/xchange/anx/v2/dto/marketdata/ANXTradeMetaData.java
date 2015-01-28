package com.xeiam.xchange.anx.v2.dto.marketdata;

import java.math.BigDecimal;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.TradeMetaInfo;

public class ANXTradeMetaData extends TradeMetaInfo {

  private final BigDecimal amountMaximum;

  public ANXTradeMetaData(BigDecimal amountMinimum, BigDecimal amountMaximum, int priceScale) {

    super(null, amountMinimum, priceScale, null);
    this.amountMaximum = amountMaximum;
  }

  @Override
  protected void verifyOrder(Order order) {

    super.verifyOrder(order);

    if (order.getTradableAmount().compareTo(amountMaximum) > 0) {
      throw new IllegalArgumentException("Order amount exceeds maximum");
    }
  }

  public BigDecimal getAmountMaximum() {

    return amountMaximum;
  }
}
