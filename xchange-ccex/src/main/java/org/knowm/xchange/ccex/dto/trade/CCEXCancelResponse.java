package org.knowm.xchange.ccex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXCancelResponse {

  private boolean success;
  private String message;
  private String result;

  public CCEXCancelResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("message") String message,
      @JsonProperty("result") String result) {
    super();
    this.success = success;
    this.message = message;
    this.result = result;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return "CCEXCancelResponse [success="
        + success
        + ", message="
        + message
        + ", result="
        + result
        + "]";
  }
}
