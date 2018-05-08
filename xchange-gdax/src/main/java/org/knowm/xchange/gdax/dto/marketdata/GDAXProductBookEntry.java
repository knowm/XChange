package org.knowm.xchange.gdax.dto.marketdata;

import java.math.BigDecimal;

/** Created by Yingzhe on 4/6/2015. */
public abstract class GDAXProductBookEntry {

  private final BigDecimal price;
  private final BigDecimal volume;

  public GDAXProductBookEntry(BigDecimal price, BigDecimal volume) {

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
    return "GDAXProductBookEntry [price=" + price + ", volume=" + volume + "]";
  }
}
