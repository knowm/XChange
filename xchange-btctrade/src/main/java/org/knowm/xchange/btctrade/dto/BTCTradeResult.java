package org.knowm.xchange.btctrade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCTradeResult {

  private final Boolean result;
  private final String message;

  public BTCTradeResult(
      @JsonProperty("result") Boolean result, @JsonProperty("message") String message) {

    this.result = result;
    this.message = message;
  }

  public Boolean getResult() {

    return result;
  }

  public String getMessage() {

    return message;
  }

  public boolean isSuccess() {

    return result == null || result.booleanValue();
  }
}
