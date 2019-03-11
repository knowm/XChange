package org.knowm.xchange.bitflyer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitflyerException extends RuntimeException {

  @JsonProperty("status")
  private int status;

  @JsonProperty("error_message")
  private String errorMessage;

  @JsonProperty("data")
  private String data;

  public BitflyerException(
      @JsonProperty("status") int status,
      @JsonProperty("error_message") String errorMessage,
      @JsonProperty("data") String data) {
    this.status = status;
    this.errorMessage = errorMessage;
    this.data = data;
  }

  public String getMessage() {
    return errorMessage == null ? (data == null ? String.valueOf(status) : data) : errorMessage;
  }
}
