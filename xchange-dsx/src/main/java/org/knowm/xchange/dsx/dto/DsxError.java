package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DsxError {

  private final String code;
  private final String message;
  private final String description;

  public DsxError(
      @JsonProperty("code") String code,
      @JsonProperty("message") String message,
      @JsonProperty("description") String description) {
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

  @Override
  public String toString() {
    return "DsxError{"
        + "code='"
        + code
        + '\''
        + ", message='"
        + message
        + '\''
        + ", description='"
        + description
        + '\''
        + '}';
  }
}
