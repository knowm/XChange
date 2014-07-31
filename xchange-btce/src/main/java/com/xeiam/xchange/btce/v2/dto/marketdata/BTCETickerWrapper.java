package com.xeiam.xchange.btce.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker Wrapper from BTC-E
 */
@Deprecated
public class BTCETickerWrapper {

  private BTCETicker ticker;

  /**
   * Constructor
   * 
   * @param ticker
   */
  public BTCETickerWrapper(@JsonProperty("ticker") BTCETicker ticker) {

    this.ticker = ticker;
  }

  public BTCETicker getTicker() {

    return ticker;
  }

  @Override
  public String toString() {

    return "BTCETicker [ticker=" + ticker.toString() + "]";
  }

}
