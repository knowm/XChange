package org.knowm.xchange.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class HitbtcException extends RuntimeException {

  @JsonProperty("message")
  String message;

  @JsonProperty("code")
  String code;

  public HitbtcException(@JsonProperty("code") String code, @JsonProperty("message") String message) {

    super();
    this.code = code;
    this.message = message;
  }

  public String getCode() {

    return code;
  }

  public String getMessage() {

    return message;
  }

}
