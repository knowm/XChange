package org.knowm.xchange.loyalbit.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.ExceptionalReturnContentException;

public class LoyalbitBaseResponse {

  private final Integer status;
  private final String message;

  protected LoyalbitBaseResponse(@JsonProperty("status") Integer status, @JsonProperty("message") String message) {
    if (Objects.equals(status, 0)) {
      throw new ExceptionalReturnContentException(message);
    }
    this.status = status;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public Integer getStatus() {
    return status;
  }
}
