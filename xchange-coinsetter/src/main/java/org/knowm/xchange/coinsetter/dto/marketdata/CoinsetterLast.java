package org.knowm.xchange.coinsetter.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The last available trades data.
 */
public class CoinsetterLast {

  private final CoinsetterTrade[] last;

  public CoinsetterLast(@JsonProperty("last") CoinsetterTrade[] last) {

    this.last = last;
  }

  public CoinsetterTrade[] getLast() {

    return last;
  }

  @Override
  public String toString() {

    return "CoinsetterLast [last=" + Arrays.toString(last) + "]";
  }

}
