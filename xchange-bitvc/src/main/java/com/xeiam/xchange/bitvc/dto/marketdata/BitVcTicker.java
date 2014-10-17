package com.xeiam.xchange.bitvc.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcTicker {

  private final BitVcTickerObject ticker;

  public BitVcTicker(@JsonProperty("ticker") final BitVcTickerObject ticker) {

    this.ticker = ticker;
  }

  public BitVcTickerObject getTicker() {

    return ticker;
  }

}
