package org.knowm.xchange.exceptions;

public class NonceException extends ExchangeException {

  public NonceException(Throwable cause) {
    super(cause);
  }

  public NonceException(String message, Throwable cause) {
    super(message, cause);
  }

  public NonceException(String message) {
    super(message);
  }

  public NonceException() {
    super("Something went wrong with using the provided Nonce.");
  }
}
