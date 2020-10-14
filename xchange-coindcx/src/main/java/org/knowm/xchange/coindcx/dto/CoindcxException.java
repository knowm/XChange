package org.knowm.xchange.coindcx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoindcxException extends RuntimeException {

  @JsonProperty("message")
  private String message;

  public CoindcxException(@JsonProperty("message") String message) {

    super();
    this.message = message;
  }

  public String getMessage() {

    return message;
  }
}
