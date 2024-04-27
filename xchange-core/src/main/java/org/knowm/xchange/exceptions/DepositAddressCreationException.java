package org.knowm.xchange.exceptions;

/** Exception indicating a deposit address could not be created */
public class DepositAddressCreationException extends ExchangeException {

  public DepositAddressCreationException() {
    super("Deposit Address Could Not Be Created");
  }

  public DepositAddressCreationException(String message) {
    super(message);
  }

  public DepositAddressCreationException(String message, Throwable cause) {
    super(message, cause);
  }
}
