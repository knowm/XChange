package org.knowm.xchange.coinbaseex.dto.marketdata;

import java.math.BigDecimal;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExProductBookEntry {

  private final BigDecimal price;
  private final BigDecimal volume;
  private final int numberOfOrders;

  public CoinbaseExProductBookEntry(BigDecimal price, BigDecimal volume, int numberOfOrders) {

    this.price = price;
    this.volume = volume;
    this.numberOfOrders = numberOfOrders;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public int getNumberOfOrders() {

    return numberOfOrders;
  }
}
