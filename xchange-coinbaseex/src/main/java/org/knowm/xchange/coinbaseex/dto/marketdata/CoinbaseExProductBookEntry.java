package org.knowm.xchange.coinbaseex.dto.marketdata;

import java.math.BigDecimal;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public abstract class CoinbaseExProductBookEntry {

  private final BigDecimal price;
  private final BigDecimal volume;

  public CoinbaseExProductBookEntry(BigDecimal price, BigDecimal volume) {

    this.price = price;
    this.volume = volume;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return volume;
  }

}
