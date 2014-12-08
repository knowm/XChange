package com.xeiam.xchange.btce.v3.dto.marketdata;

import com.xeiam.xchange.dto.marketdata.BaseMarketMetadata;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;

public class BTCEMarketMetadata extends BaseMarketMetadata {
  final private BigDecimal minPrice;
  final private BigDecimal maxPrice;

  public BTCEMarketMetadata(BigDecimal amountMinimum, int priceScale, BigDecimal orderFeeFactor, BigDecimal minPrice, BigDecimal maxPrice) {

    super(amountMinimum, priceScale, orderFeeFactor);
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
      throw new IllegalArgumentException("Price too low: minimum = " + minPrice + "; actual = " + limitPrice);
    }
  }

  public BigDecimal getMinPrice() {

    return minPrice;
  }

  public BigDecimal getMaxPrice() {

    return maxPrice;
  }
}
