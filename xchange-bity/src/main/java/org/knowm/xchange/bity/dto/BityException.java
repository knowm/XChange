package org.knowm.xchange.bity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class BityException extends HttpStatusExceptionSupport {
  public BityException(
      @JsonProperty("error") String error,
      @JsonProperty("error_description") String errorDescription) {
    super(errorDescription);
  }
}
