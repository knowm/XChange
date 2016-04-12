package org.knowm.xchange.coinbaseex.dto.marketdata;

import java.math.BigDecimal;

public class CoinbaseExProductBookEntryLevel1or2  extends CoinbaseExProductBookEntry {

  private final int numOrdersOnLevel;

  public CoinbaseExProductBookEntryLevel1or2(BigDecimal price, BigDecimal volume, int numOrdersOnLevel) {
    super(price, volume);
    this.numOrdersOnLevel = numOrdersOnLevel;
  }

  public int getNumOrdersOnLevel() {
    return numOrdersOnLevel;
  }




}