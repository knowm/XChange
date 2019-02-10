package org.knowm.xchange.btcturk.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author mertguner */
public class BTCTurkCancelOrderResult {

  private final Boolean result;

  public BTCTurkCancelOrderResult(@JsonProperty("result") boolean result) {
    this.result = result;
  }

  public Boolean getResult() {
    return result;
  }

  @Override
  public String toString() {
    return "BTCTurkCancelOrderResult [result=" + result + "]";
  }
}
