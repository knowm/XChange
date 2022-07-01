package org.knowm.xchange.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import si.mazi.rescu.ExceptionalReturnContentException;

public class BithumbResponse<D> {

  private final String status;
  private final String message;
  private final D data;

  public BithumbResponse(
      @JsonProperty("status") String status,
      @JsonProperty("message") String message,
      @JsonProperty("data") D data) {

    if (!StringUtils.equals(status, "0000")) {
      throw new ExceptionalReturnContentException(message);
    }
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public String getStatus() {
    return status;
  }

  public D getData() {
    return data;
  }

  public String getMessage() {
    return message;
  }
}
