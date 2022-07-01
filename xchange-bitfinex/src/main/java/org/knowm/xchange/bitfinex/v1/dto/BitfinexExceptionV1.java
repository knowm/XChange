package org.knowm.xchange.bitfinex.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitfinex.dto.BitfinexException;

public class BitfinexExceptionV1 extends BitfinexException {

  @JsonProperty("message")
  private String message;

  @JsonProperty("error")
  private String error;

  public BitfinexExceptionV1(@JsonProperty("message") String message) {

    super();
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message == null ? error : message;
  }

  public String getError() {
    return error;
  }
}
