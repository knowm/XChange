package org.knowm.xchange.ccex.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXGetopenordersResponse {

  private boolean success;
  private String message;
  private List<CCEXOpenorder> result;

  public CCEXGetopenordersResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message,
      @JsonProperty("result") List<CCEXOpenorder> result) {
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

  public List<CCEXOpenorder> getResult() {
    return result;
  }

  public void setResult(List<CCEXOpenorder> result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return "CCEXGetopenordersResponse [success=" + success + ", message=" + message + ", result=" + result + "]";
  }
}
