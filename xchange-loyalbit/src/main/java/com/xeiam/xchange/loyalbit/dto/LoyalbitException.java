package com.xeiam.xchange.loyalbit.dto;

import si.mazi.rescu.HttpStatusExceptionSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoyalbitException extends HttpStatusExceptionSupport {

  private Integer status;

  public LoyalbitException(@JsonProperty("message") String message, @JsonProperty("status") Integer status) {
    super(message);
    this.status = status;
  }

  public Integer getStatus() {
    return status;
  }
}
