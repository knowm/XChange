package com.xeiam.xchange.exceptions;

public class FundsExceededException extends ExchangeException {

  private static final long serialVersionUID = 2015041101L;

  public FundsExceededException(String message) {
    super(message);
  }

  public FundsExceededException() {
    super("Not enough funds are available.");
  }

}
