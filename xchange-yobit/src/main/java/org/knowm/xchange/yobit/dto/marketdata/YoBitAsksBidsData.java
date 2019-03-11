package org.knowm.xchange.yobit.dto.marketdata;

import java.math.BigDecimal;

public class YoBitAsksBidsData {
  private final BigDecimal quantity;
  private final BigDecimal rate;

  /**
   * @param rate
   * @param quantity
   */
  public YoBitAsksBidsData(BigDecimal quantity, BigDecimal rate) {
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
