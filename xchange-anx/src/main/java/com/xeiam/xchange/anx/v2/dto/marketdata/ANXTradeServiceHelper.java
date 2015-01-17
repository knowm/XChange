package com.xeiam.xchange.anx.v2.dto.marketdata;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.BaseTradeServiceHelper;

import java.math.BigDecimal;

public class ANXTradeServiceHelper extends BaseTradeServiceHelper {
  private final BigDecimal amountMaximum;

  public ANXTradeServiceHelper(BigDecimal amountMinimum, BigDecimal amountMaximum, int priceScale) {
    super(amountMinimum, priceScale);
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
