package org.knowm.xchange.gdax.dto.marketdata;

import java.math.BigDecimal;

public class GDAXProductBookEntryLevel1or2 extends GDAXProductBookEntry {

  private final int numOrdersOnLevel;

  public GDAXProductBookEntryLevel1or2(BigDecimal price, BigDecimal volume, int numOrdersOnLevel) {
    super(price, volume);
    this.numOrdersOnLevel = numOrdersOnLevel;
  }

  public int getNumOrdersOnLevel() {
    return numOrdersOnLevel;
  }
}
