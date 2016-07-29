package org.knowm.xchange.exceptions;

public class NonceException extends ExchangeException {

  public NonceException(String message) {
    super(message);
  }

  public NonceException() {
    super("Something went wrong with using the provided Nonce.");
  }
}
