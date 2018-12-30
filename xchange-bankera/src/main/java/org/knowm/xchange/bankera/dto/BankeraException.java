package org.knowm.xchange.bankera.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class BankeraException extends HttpStatusExceptionSupport {

  private final String error;

  public BankeraException(
      @JsonProperty("status") Integer status, @JsonProperty("error") String error) {
    super(error);
    this.error = error;
  }

  public String getError() {
    return error;
  }
}
