package org.knowm.xchange.coinsetter.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cumulative available quantity in real time based on price level.
 */
public class CoinsetterLevels {

  private final CoinsetterLevel[] levels;

  public CoinsetterLevels(@JsonProperty("levels") CoinsetterLevel[] levels) {

    this.levels = levels;
  }

  public CoinsetterLevel[] getLevels() {

    return levels;
  }

  @Override
  public String toString() {

    return "CoinsetterLevels [levels=" + Arrays.toString(levels) + "]";
  }

}
