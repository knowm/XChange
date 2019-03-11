package org.knowm.xchange.itbit.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class ItBitException extends HttpStatusExceptionSupport {

  private String code;

  private String requestId;

  public ItBitException(
      @JsonProperty("code") String code,
      @JsonProperty("description") String description,
      @JsonProperty("requestId") String requestId) {
    super(description);
    this.code = code;
    this.requestId = requestId;
  }

  public String getCode() {
    return code;
  }

  public String getRequestId() {
    return requestId;
  }
}
