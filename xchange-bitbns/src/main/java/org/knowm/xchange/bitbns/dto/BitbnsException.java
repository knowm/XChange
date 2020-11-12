package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitbnsException extends RuntimeException {

  @JsonProperty("message")
  private String message;

  public BitbnsException(@JsonProperty("message") String message) {

    super();
    this.message = message;
  }

  public String getMessage() {

    return message;
  }
}
