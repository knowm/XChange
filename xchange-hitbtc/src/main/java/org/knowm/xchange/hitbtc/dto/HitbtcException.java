package org.knowm.xchange.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class HitbtcException extends RuntimeException {

  private final Error error;

  public HitbtcException(@JsonProperty("error") Error error) {

    super();
    this.error = error;
  }

  public String getCode() {
    return error.code;
  }

  public String getMessage() {
    return error.message + " [" + error.description + "]";
  }

  public Error getError() {
    return error;
  }

  public static class Error {

    private final String code;
    private final String message;
    private final String description;

    public Error(@JsonProperty("code") String code, @JsonProperty("message") String message, @JsonProperty("description")  String description) {
      this.code = code;
      this.message = message;
      this.description = description;
    }

    public String getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }

    public String getDescription() {
      return description;
    }
  }

}
