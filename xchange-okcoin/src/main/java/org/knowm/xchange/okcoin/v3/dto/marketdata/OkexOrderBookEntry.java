package org.knowm.xchange.okcoin.v3.dto.marketdata;

import java.math.BigDecimal;

public class OkexOrderBookEntry {

  private final BigDecimal price;
  private final BigDecimal volume;
  private final String numOrdersOnLevel;

  public OkexOrderBookEntry(BigDecimal price, BigDecimal volume, String numOrdersOnLevel) {

    this.price = price;
    this.volume = volume;
    this.numOrdersOnLevel = numOrdersOnLevel;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public String getNumOrdersOnLevel() {
    return numOrdersOnLevel;
  }

  @Override
  public String toString() {
    return "OkexOrderBookEntry [price=" + price + ", volume=" + volume + "]";
  }
}
