package com.xeiam.xchange.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class HitbtcException extends RuntimeException {

  @JsonProperty("message")
  String message;

  public HitbtcException(@JsonProperty("message") String message) {

    super();
    this.message = message;
  }

  public String getMessage() {

    return message;
  }

}
