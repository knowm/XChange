package org.knowm.xchange.exceptions;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

/**
 * Exception indicating that a request was made with a {@link CurrencyPair} that is not supported on
 * this exchange.
 *
 * @author bryant_harris
 */
public class CurrencyPairNotValidException extends ExchangeException {
  private Instrument currencyPair;

  public CurrencyPairNotValidException() {
    super("Invalid currency pair for this operation");
  }

  public CurrencyPairNotValidException(String message, Throwable cause, Instrument currencyPair) {
    super(message, cause);
    this.currencyPair = currencyPair;
  }

  public CurrencyPairNotValidException(String message, Throwable cause) {
    super(message, cause);
  }

  public CurrencyPairNotValidException(String message) {
    super(message);
  }

  public CurrencyPairNotValidException(String message, Instrument currencyPair) {
    super(message);
    this.currencyPair = currencyPair;
  }

  public CurrencyPairNotValidException(Throwable cause) {
    super(cause);
  }

  public CurrencyPairNotValidException(Throwable cause, Instrument currencyPair) {
    super(currencyPair + " is not valid for this operation", cause);
    this.currencyPair = currencyPair;
  }

  public CurrencyPairNotValidException(Instrument currencyPair) {
    this(currencyPair + " is not valid for this operation");
    this.currencyPair = currencyPair;
  }

  /** @return The instrument that caused the exception. */
  public Instrument getInstrument() {
    return currencyPair;
  }

  /**
   * @param basePrice
   * @deprecated CurrencyPair is a subtype of Instrument <br>
   *     use {@link #getInstrument()} instead like this:
   *     <blockquote>
   *     <pre>
   * getInstrument()
   * </pre>
   *     </blockquote>
   */
  @Deprecated
  public CurrencyPair getCurrencyPair() {
    if (currencyPair instanceof CurrencyPair) return (CurrencyPair) currencyPair;
    return null;
  }
}
