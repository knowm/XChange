package org.knowm.xchange.livecoin.service;

import java.math.BigDecimal;

public class LivecoinAsksBidsData {
  private final BigDecimal quantity;
  private final BigDecimal rate;

  /**
   * @param rate
   * @param quantity
   */
  public LivecoinAsksBidsData(BigDecimal quantity, BigDecimal rate) {
    this.quantity = quantity;
    this.rate = rate;
  }

  /** @return The quantity */
  public BigDecimal getQuantity() {
    return quantity;
  }

  /** @return The rate */
  public BigDecimal getRate() {
    return rate;
  }
}
