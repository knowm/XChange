package org.knowm.xchange.exceptions;

/**
 * Indicates that the currency pair you requested was never supported by the given exchange or it
 * got delisted.
 *
 * @author walec51
 */
public class InvalidCurrencyPairException extends ExchangeException {

  public InvalidCurrencyPairException() {
    super("Given currency pair is not supported");
  }

  public InvalidCurrencyPairException(String message) {
    super(message);
  }
}
