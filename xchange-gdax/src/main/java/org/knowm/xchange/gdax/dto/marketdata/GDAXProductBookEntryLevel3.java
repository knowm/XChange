package org.knowm.xchange.gdax.dto.marketdata;

import java.math.BigDecimal;

public class GDAXProductBookEntryLevel3 extends GDAXProductBookEntry {

  private final String orderId;

  public GDAXProductBookEntryLevel3(BigDecimal price, BigDecimal volume, String orderId) {
    super(price, volume);
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
