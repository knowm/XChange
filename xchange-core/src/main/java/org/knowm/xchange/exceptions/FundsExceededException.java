package org.knowm.xchange.exceptions;

public class FundsExceededException extends ExchangeException {

  public FundsExceededException(String message) {
    super(message);
  }

  public FundsExceededException(String message, Throwable cause) {
    super(message, cause);
  }

  public FundsExceededException() {
    super("Not enough funds are available.");
  }

}
