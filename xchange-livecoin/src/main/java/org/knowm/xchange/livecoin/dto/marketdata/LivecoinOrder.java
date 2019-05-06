package org.knowm.xchange.livecoin.dto.marketdata;

import java.math.BigDecimal;

public class LivecoinOrder {
  private final BigDecimal rate;
  private final BigDecimal quantity;

  public LivecoinOrder(BigDecimal rate, BigDecimal quantity) {
    this.rate = rate;
    this.quantity = quantity;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }
}
