package org.knowm.xchange.gateio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GateioBaseResponse {

  private final boolean result;
  private final String message;

  protected GateioBaseResponse(
      @JsonProperty("result") final boolean result, @JsonProperty("msg") final String message) {

    this.result = result;
    this.message = message;
  }

  public boolean isResult() {

    return result;
  }

  public String getMessage() {

    return message;
  }

  @Override
  public String toString() {

    return "GateioBaseResponse [result=" + result + ", message=" + message + "]";
  }
}
