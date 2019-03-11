package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinBatchTradeResult {
  private final String success;
  private final String error;

  public OkCoinBatchTradeResult(
      @JsonProperty("success") final String success, @JsonProperty("error") final String error) {

    this.success = success;
    this.error = error;
  }

  public String getSuccess() {
    return success;
  }

  public String getError() {
    return error;
  }
}
