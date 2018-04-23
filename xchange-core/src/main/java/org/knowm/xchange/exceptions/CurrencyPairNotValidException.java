package org.knowm.xchange.exceptions;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * Exception indicating that a request was made with a {@link CurrencyPair} that is not supported on
 * this exchange.
 *
 * @author bryant_harris
 */
public class CurrencyPairNotValidException extends ExchangeException {
  private org.knowm.xchange.currency.CurrencyPair currencyPair;

  public CurrencyPairNotValidException(
      String message, Throwable cause, org.knowm.xchange.currency.CurrencyPair currencyPair) {
    super(message, cause);
    this.currencyPair = currencyPair;
  }

  public CurrencyPairNotValidException(String message, Throwable cause) {
    super(message, cause);
  }

  public CurrencyPairNotValidException(String message) {
    super(message);
  }

  public CurrencyPairNotValidException(
      String message, org.knowm.xchange.currency.CurrencyPair currencyPair) {
    super(message);
    this.currencyPair = currencyPair;
  }

  public CurrencyPairNotValidException(Throwable cause) {
    super(cause);
  }

  public CurrencyPairNotValidException(
      Throwable cause, org.knowm.xchange.currency.CurrencyPair currencyPair) {
    super(currencyPair + " Is not valid for this exchange", cause);
    this.currencyPair = currencyPair;
  }

  public CurrencyPairNotValidException(org.knowm.xchange.currency.CurrencyPair currencyPair) {
    this(currencyPair + " Is not valid for this exchange");
    this.currencyPair = currencyPair;
  }

  /** @return The currency pair that caused the exception. */
  public org.knowm.xchange.currency.CurrencyPair getCurrencyPair() {
    return currencyPair;
  }
}
