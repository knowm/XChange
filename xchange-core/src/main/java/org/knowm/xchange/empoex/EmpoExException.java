package org.knowm.xchange.empoex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpoExException extends RuntimeException {

  private static final long serialVersionUID = 5208208380093962699L;

  @JsonProperty("success")
  private boolean success;

  @JsonProperty("message")
  private String message;

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

}
