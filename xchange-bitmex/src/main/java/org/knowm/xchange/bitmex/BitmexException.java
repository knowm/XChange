package org.knowm.xchange.bitmex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitmexException extends RuntimeException {

  static class Error {
    @JsonProperty("message")
    private String message;

    @JsonProperty("name")
    private String name;

    public Error(@JsonProperty("message") String message, @JsonProperty("name") String name) {
      this.message = message;
      this.name = name;
    }
  }

  @JsonProperty("error")
  private Error error;

  public BitmexException(@JsonProperty("error") Error error) {
    this.error = error;
  }

  public String getErrorName() {
    return error.name;
  }

  @Override
  public String getMessage() {
    return error.message == null ? error.name : error.message;
  }
}
