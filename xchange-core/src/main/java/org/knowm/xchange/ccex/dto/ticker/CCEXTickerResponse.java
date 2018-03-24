package org.knowm.xchange.ccex.dto.ticker;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXTickerResponse {

  private CCEXPriceResponse ticker;

  public CCEXTickerResponse(@JsonProperty("ticker") CCEXPriceResponse ticker) {
    super();
    this.ticker = ticker;
  }

  public CCEXPriceResponse getTicker() {
    return ticker;
  }

  public void setTicker(CCEXPriceResponse ticker) {
    this.ticker = ticker;
  }

  @Override
  public String toString() {
    return "CCEXTickerResponse [ticker=" + ticker + "]";
  }
}
