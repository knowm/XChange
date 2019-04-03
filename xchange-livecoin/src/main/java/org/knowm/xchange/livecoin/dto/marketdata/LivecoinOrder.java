package org.knowm.xchange.livecoin.dto.marketdata;

import java.math.BigDecimal;

public class LivecoinOrder {
  private final BigDecimal quantity;
  private final BigDecimal rate;

  public LivecoinOrder(BigDecimal quantity, BigDecimal rate) {
    this.quantity = quantity;
    this.rate = rate;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getRate() {
    return rate;
  }
}
