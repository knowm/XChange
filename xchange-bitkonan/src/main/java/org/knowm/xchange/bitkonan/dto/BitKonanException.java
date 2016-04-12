package org.knowm.xchange.bitkonan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class BitKonanException extends RuntimeException {

  @JsonProperty("message")
  String message;

  public BitKonanException(@JsonProperty("message") String message) {

    super();
    this.message = message;
  }

  public String getMessage() {

    return message;
  }

}
