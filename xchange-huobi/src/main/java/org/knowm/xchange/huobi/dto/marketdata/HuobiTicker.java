package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiTicker {

  private final HuobiTickerObject ticker;

  public HuobiTicker(@JsonProperty("ticker") final HuobiTickerObject ticker) {

    this.ticker = ticker;
  }

  public HuobiTickerObject getTicker() {

    return ticker;
  }

}
