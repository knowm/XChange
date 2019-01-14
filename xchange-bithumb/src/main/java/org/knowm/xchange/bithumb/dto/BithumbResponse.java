package org.knowm.xchange.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class BithumbResponse<D> {

  private final String status;
  private final String message;
  private final D data;

  public BithumbResponse(
      @JsonProperty("status") String status,
      @JsonProperty("message") String message,
      @JsonProperty("data") D data) {
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

  public boolean isSuccess() {
    return StringUtils.equals(status, "0000");
  }

  public String getMessage() {
    return message;
  }
}
