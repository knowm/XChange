package com.xeiam.xchange.coinbase.dto;

import si.mazi.rescu.HttpStatusExceptionSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinbaseException extends HttpStatusExceptionSupport {
  public CoinbaseException(@JsonProperty("error") String message, @JsonProperty("success") Boolean success) {
    super(message);
  }
}
