package org.knowm.xchange.lykke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeError {

  private String code;
  private String field;
  private String message;

  public LykkeError(
      @JsonProperty("Code") String code,
      @JsonProperty("Field") String field,
      @JsonProperty("Message") String message) {
    this.code = code;
    this.field = field;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Error{"
        + "code='"
        + code
        + '\''
        + ", field='"
        + field
        + '\''
        + ", message='"
        + message
        + '\''
        + '}';
  }
}
