package org.knowm.xchange.coinbasepro.dto.marketdata;

import java.math.BigDecimal;

public abstract class CoinbaseProProductBookEntry {

  private final BigDecimal price;
  private final BigDecimal volume;

  public CoinbaseProProductBookEntry(BigDecimal price, BigDecimal volume) {

    this.price = price;
    this.volume = volume;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  @Override
  public String toString() {
    return "CoinbaseProProductBookEntry [price=" + price + ", volume=" + volume + "]";
  }
}
