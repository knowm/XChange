package org.knowm.xchange.exceptions;

/** An exception indicating there are not enough funds for the action requested */
public class FundsExceededException extends ExchangeException {

  public FundsExceededException(String message) {
    super(message);
  }

  public FundsExceededException(Throwable e) {
    super(e);
  }

  public FundsExceededException(String message, Throwable cause) {
    super(message, cause);
  }

  public FundsExceededException() {
    super("Not enough funds are available.");
  }
}
