package org.knowm.xchange.bter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTERBaseResponse {

  private final boolean result;
  private final String message;

  protected BTERBaseResponse(@JsonProperty("result") final boolean result, @JsonProperty("msg") final String message) {

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

    return "BTERBaseResponse [result=" + result + ", message=" + message + "]";
  }
}
