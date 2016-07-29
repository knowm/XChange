package org.knowm.xchange.exceptions;

public class FundsExceededException extends ExchangeException {

  public FundsExceededException(String message) {
    super(message);
  }

  public FundsExceededException() {
    super("Not enough funds are available.");
  }

}
