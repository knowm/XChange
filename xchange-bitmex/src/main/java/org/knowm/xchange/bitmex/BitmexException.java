package org.knowm.xchange.bitmex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitmexException extends RuntimeException {

  @JsonProperty("message")
  private String message;

  @JsonProperty("name")
  private String name;

  public BitmexException(
      @JsonProperty("message") String message, @JsonProperty("name") String name) {
    this.message = message;
    this.name = name;
  }

  public String getMessage() {
    return message == null ? name : message;
  }
}
