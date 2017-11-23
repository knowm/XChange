package org.knowm.xchange.ccex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXBalanceResponse {

  private boolean success;
  private String message;
  private CCEXBalance result;

  public CCEXBalanceResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") CCEXBalance result) {
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

  public CCEXBalance getResult() {
    return result;
  }

  public void setResult(CCEXBalance result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return "CCEXBalancesResponse [success=" + success + ", message=" + message + ", result=" + result + "]";
  }
}
