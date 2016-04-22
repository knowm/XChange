package org.knowm.xchange.cointrader.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.ExceptionalReturnContentException;

public class CointraderBaseResponse<D> {

  private final Boolean status;
  private final String message;
  private final D data;

  protected CointraderBaseResponse(@JsonProperty("success") Boolean success, @JsonProperty("message") String message, @JsonProperty("data") D data) {
    if (!Objects.equals(success, Boolean.TRUE)) {
      throw new ExceptionalReturnContentException(message);
    }
    this.status = success;
    this.message = message;
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public Boolean getStatus() {
    return status;
  }

  public D getData() {
    return data;
  }

  @Override
  public String toString() {
    return String.format("CointraderBaseResponse{status=%s, message='%s', data=%s}", status, message, data);
  }
}
