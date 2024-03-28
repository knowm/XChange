package org.knowm.xchange.exceptions;

/** Exception indicating a requested deposit address was not found */
public class DepositAddressNotFoundException extends ExchangeException {

  public DepositAddressNotFoundException() {
    super("Deposit Address Not Found");
  }

  public DepositAddressNotFoundException(String message) {
    super(message);
  }

  public DepositAddressNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
