package org.knowm.xchange.gatecoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class GatecoinException extends HttpStatusExceptionSupport {
  protected final ResponseStatus responseStatus;

  public GatecoinException(@JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus.getMessage());
    this.responseStatus = responseStatus;
  }

  public String getErrorCode() {
    return responseStatus.getErrorCode();
  }
}
