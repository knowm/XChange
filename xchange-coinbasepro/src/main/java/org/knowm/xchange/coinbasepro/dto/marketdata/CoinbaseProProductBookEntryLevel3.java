package org.knowm.xchange.coinbasepro.dto.marketdata;

import java.math.BigDecimal;

public class CoinbaseProProductBookEntryLevel3 extends CoinbaseProProductBookEntry {

  private final String orderId;

  public CoinbaseProProductBookEntryLevel3(BigDecimal price, BigDecimal volume, String orderId) {
    super(price, volume);
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
