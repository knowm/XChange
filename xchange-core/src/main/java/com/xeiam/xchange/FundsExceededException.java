package com.xeiam.xchange;

public class FundsExceededException extends ExchangeException {
  private static final long serialVersionUID = 2406896439418334853L;

  public FundsExceededException(String s) {
    super(s);
  }

  public FundsExceededException(String message, Throwable cause) {
    super(message, cause);
  }

}
