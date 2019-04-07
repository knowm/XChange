package org.knowm.xchange.globitex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class GlobitexError implements Serializable {

  @JsonProperty("code")
  private final int code;

  @JsonProperty("message")
  private final String message;

  public GlobitexError(@JsonProperty("code") int code, @JsonProperty("message") String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "GlobitexError{" + "code=" + code + ", message='" + message + '\'' + '}';
  }
}
