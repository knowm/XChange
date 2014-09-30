package com.xeiam.xchange.okcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinTickerResponse {

  private final OkCoinTicker ticker;

  public OkCoinTickerResponse(@JsonProperty("ticker") OkCoinTicker ticker) {

    this.ticker = ticker;
  }

  public OkCoinTicker getTicker() {

    return ticker;
  }

}
