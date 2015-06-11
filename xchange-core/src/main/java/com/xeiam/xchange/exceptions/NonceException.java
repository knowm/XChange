package com.xeiam.xchange.exceptions;

public class NonceException extends ExchangeException {

  private static final long serialVersionUID = 2015041101L;

  public NonceException(String message) {
    super(message);
  }

  public NonceException() {
    super("Something went wrong with using the provided Nonce.");
  }
}
