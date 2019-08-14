package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import si.mazi.rescu.HttpStatusExceptionSupport;

@Getter
public class DeribitException extends HttpStatusExceptionSupport {

  private final DeribitError error;

  public DeribitException(@JsonProperty("error") DeribitError error) {
    super(error.getCode() + ": " + error.getMessage() + ", " + error.getData());
    this.error = error;
  }
}
