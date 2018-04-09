package org.knowm.xchange.btctrade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCTradeSecretResponse extends BTCTradeResult {

  private final BTCTradeSecretData data;

  public BTCTradeSecretResponse(
      @JsonProperty("result") boolean result,
      @JsonProperty("message") String message,
      @JsonProperty("data") BTCTradeSecretData data) {

    super(result, message);
    this.data = data;
  }

  public BTCTradeSecretData getData() {

    return data;
  }
}
