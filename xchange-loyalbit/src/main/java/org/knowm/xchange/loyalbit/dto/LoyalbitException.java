package org.knowm.xchange.loyalbit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.HttpStatusExceptionSupport;

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
