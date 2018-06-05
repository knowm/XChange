package org.knowm.xchange.poloniex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class PoloniexException extends HttpStatusExceptionSupport {

  @JsonProperty("error")
  private String error;

  public PoloniexException() {}

  public PoloniexException(String error) {
    this.error = error;
    setHttpStatusCode(200);
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public String getMessage() {
    return error;
  }
}
