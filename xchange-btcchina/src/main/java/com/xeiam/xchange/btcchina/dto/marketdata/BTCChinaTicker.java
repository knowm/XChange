package com.xeiam.xchange.btcchina.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaTicker {

  private BTCChinaTickerObject ticker;

  /**
   * Constructor
   * 
   * @param ticker
   */
  public BTCChinaTicker(@JsonProperty("ticker") BTCChinaTickerObject ticker) {

    this.ticker = ticker;
  }

  public BTCChinaTickerObject getTicker() {

    return ticker;
  }

  @Override
  public String toString() {

    return "BTCChinaTicker [ticker=" + ticker.toString() + "]";
  }
}
