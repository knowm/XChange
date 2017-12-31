package org.knowm.xchange.poloniex;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.HttpStatusExceptionSupport;

public class PoloniexException extends HttpStatusExceptionSupport {

  @JsonProperty("error")
  private String error;

  public PoloniexException(@JsonProperty("error") String error) {

    super(error);
    this.error = error;
  }

  public String getError() {

    return error;
  }
}
