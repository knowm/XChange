package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitError {

  @JsonProperty("code")
  private int code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private Object data;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "DeribitError{"
        + "code="
        + code
        + ", message='"
        + message
        + '\''
        + ", data="
        + data
        + '}';
  }
}
