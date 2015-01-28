package com.xeiam.xchange.btce.v3.dto.marketdata;

import java.math.BigDecimal;

import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.TradeMetaInfo;

public class BTCETradeMetaData extends TradeMetaInfo {

  final private BigDecimal minPrice;
  final private BigDecimal maxPrice;

  /**
   * Constructor
   *
   * @param fee
   * @param amountMinimum
   * @param priceScale
   * @param minPrice
   * @param maxPrice
   */
  public BTCETradeMetaData(BigDecimal fee, BigDecimal amountMinimum, int priceScale, BigDecimal minPrice, BigDecimal maxPrice) {

    super(fee, amountMinimum, priceScale, null);
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
