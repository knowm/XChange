package org.knowm.xchange.okcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinTickerResponse {

  private final OkCoinTicker ticker;

  private long date;

  public OkCoinTickerResponse(@JsonProperty("ticker") OkCoinTicker ticker) {

    this.ticker = ticker;
  }

  public OkCoinTicker getTicker() {

    return ticker;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }
}
