package org.knowm.xchange.btcchina.dto.marketdata;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaTicker extends LinkedHashMap<String, BTCChinaTickerObject> {

  private static final long serialVersionUID = 2014080301L;

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

    return "BTCChinaTicker [ticker=" + ticker + "]";
  }

}
