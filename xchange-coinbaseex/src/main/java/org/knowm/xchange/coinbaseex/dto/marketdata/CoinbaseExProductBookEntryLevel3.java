package org.knowm.xchange.coinbaseex.dto.marketdata;

import java.math.BigDecimal;

public class CoinbaseExProductBookEntryLevel3 extends CoinbaseExProductBookEntry {

  private final String orderId;

  public CoinbaseExProductBookEntryLevel3(BigDecimal price, BigDecimal volume, String orderId) {
    super(price, volume);
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }

}