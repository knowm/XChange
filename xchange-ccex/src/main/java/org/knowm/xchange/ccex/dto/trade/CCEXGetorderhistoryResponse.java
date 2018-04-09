package org.knowm.xchange.ccex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CCEXGetorderhistoryResponse {

  private boolean success;
  private String message;
  private List<CCEXOrderhistory> result;

  public CCEXGetorderhistoryResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("message") String message,
      @JsonProperty("result") List<CCEXOrderhistory> result) {
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

  public List<CCEXOrderhistory> getResult() {
    return result;
  }

  public void setResult(List<CCEXOrderhistory> result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return "CCEXGetorderhistoryResponse [success="
        + success
        + ", message="
        + message
        + ", result="
        + result
        + "]";
  }
}
