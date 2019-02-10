package org.knowm.xchange.dragonex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

@SuppressWarnings("serial")
public class DragonexException extends HttpStatusExceptionSupport {

  @JsonProperty("error")
  private String error;

  public DragonexException() {}

  public DragonexException(String error) {
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
