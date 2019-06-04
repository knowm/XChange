package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class DeribitException extends HttpStatusExceptionSupport {

  @JsonProperty("error")
  private DeribitError error;

  public DeribitError getError() {
    return error;
  }

  public void setError(DeribitError error) {
    this.error = error;
  }
}
