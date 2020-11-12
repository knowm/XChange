package org.knowm.xchange.huobi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiException extends RuntimeException {

  @JsonProperty("message")
  private String message;

  public HuobiException(@JsonProperty("message") String message) {

    super();
    this.message = message;
  }

  public String getMessage() {

    return message;
  }
}
