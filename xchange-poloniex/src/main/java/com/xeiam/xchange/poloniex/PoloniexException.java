package com.xeiam.xchange.poloniex;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class PoloniexException extends RuntimeException {

  @JsonProperty("error")
  String error;

  public PoloniexException(@JsonProperty("error") String error) {

    super();
    this.error = error;
  }

  public String getError() {

    return error;
  }

  public void setError(String error) {

    this.error = error;
  }

}
