package org.knowm.xchange.btc38.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 12/20/2014.
 */
public class Btc38TickerReturn {

  private final Btc38Ticker ticker;

  /**
   * Constructor for wrapper object for Btc38Ticker
   *
   * @param ticker
   */
  public Btc38TickerReturn(@JsonProperty("ticker") Btc38Ticker ticker) {

    this.ticker = ticker;
  }

  public Btc38Ticker getTicker() {

    return this.ticker;
  }

  @Override
  public String toString() {

    return String.format("Btc38TickerReturn[%s]", this.ticker);
  }
}
