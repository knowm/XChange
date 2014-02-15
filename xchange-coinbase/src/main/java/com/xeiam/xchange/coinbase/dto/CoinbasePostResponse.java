package com.xeiam.xchange.coinbase.dto;

import java.util.List;

public abstract class CoinbasePostResponse {

  private final boolean success;
  private final List<String> errors;

  public CoinbasePostResponse(final boolean success, final List<String> errors) {

    this.success = success;
    this.errors = errors;
  }

  public boolean isSuccess() {

    return success;
  }

  public List<String> getErrors() {

    return errors;
  }

  @Override
  public String toString() {

    return "CoinbasePostResponse [success=" + success + ", errors=" + errors + "]";
  }
}
