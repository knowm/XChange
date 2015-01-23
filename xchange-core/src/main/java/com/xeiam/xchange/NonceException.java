package com.xeiam.xchange;

public class NonceException extends ExchangeException {
  private static final long serialVersionUID = 565212065616860570L;

  public NonceException(String s) {
    super(s);
  }

  public NonceException(String message, Throwable cause) {
    super(message, cause);
  }

}
