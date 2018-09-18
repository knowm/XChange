package org.knowm.xchange.coinbasepro.dto.marketdata;

import java.math.BigDecimal;

public class CoinbaseProProductBookEntryLevel1or2 extends CoinbaseProProductBookEntry {

  private final int numOrdersOnLevel;

  public CoinbaseProProductBookEntryLevel1or2(
      BigDecimal price, BigDecimal volume, int numOrdersOnLevel) {
    super(price, volume);
    this.numOrdersOnLevel = numOrdersOnLevel;
  }

  public int getNumOrdersOnLevel() {
    return numOrdersOnLevel;
  }
}
