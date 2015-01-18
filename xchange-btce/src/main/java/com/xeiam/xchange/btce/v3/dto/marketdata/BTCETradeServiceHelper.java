package com.xeiam.xchange.btce.v3.dto.marketdata;

import com.xeiam.xchange.dto.marketdata.BaseTradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;

public class BTCETradeServiceHelper extends BaseTradeServiceHelper {
  final private BigDecimal minPrice;
  final private BigDecimal maxPrice;

  public BTCETradeServiceHelper(BigDecimal amountMinimum, int priceScale, BigDecimal minPrice, BigDecimal maxPrice) {

    super(amountMinimum, priceScale);
    assert minPrice != null;
    assert maxPrice != null;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
  }

  @Override
  public void verifyOrder(LimitOrder order) {

    super.verifyOrder(order);
    BigDecimal limitPrice = order.getLimitPrice();
    if (limitPrice.compareTo(minPrice) < 0) {
      throw new IllegalArgumentException("Price too low: minimum = " + minPrice + "; actual = " + limitPrice);
    }
    if (limitPrice.compareTo(maxPrice) > 0) {
      throw new IllegalArgumentException("Price too high: maximum = " + maxPrice + "; actual = " + limitPrice);
    }
  }

  public BigDecimal getMinPrice() {

    return minPrice;
  }

  public BigDecimal getMaxPrice() {

    return maxPrice;
  }
}
