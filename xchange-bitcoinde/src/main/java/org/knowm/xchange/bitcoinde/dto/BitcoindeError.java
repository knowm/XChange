package org.knowm.xchange.bitcoinde.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeError {

  private final String message;
  private final String code;
  private final String field;

  public BitcoindeError(
      @JsonProperty("message") String message,
      @JsonProperty("code") String code,
      @JsonProperty("field") String field) {

    this.message = message;
    this.code = code;
    this.field = field;
  }

  public String getMessage() {
    return message;
  }

  public String getCode() {
    return code;
  }

  public String getField() {
    return field;
  }

  @Override
  public String toString() {
    return "BitcoindeError{"
        + "message='"
        + message
        + '\''
        + ", code='"
        + code
        + '\''
        + ", field='"
        + field
        + '\''
        + "}";
  }
}
