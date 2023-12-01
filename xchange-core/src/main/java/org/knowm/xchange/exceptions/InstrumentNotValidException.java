package org.knowm.xchange.exceptions;

import org.knowm.xchange.instrument.Instrument;

/**
 * Exception indicating that a request was made with a {@link Instrument} that is not supported on
 * this exchange.
 *
 * @author bryant_harris
 */
public class InstrumentNotValidException extends ExchangeException {
  private Instrument instrument;

  public InstrumentNotValidException() {
    super("Invalid currency pair for this operation");
  }

  public InstrumentNotValidException(String message, Throwable cause, Instrument instrument) {
    super(message, cause);
    this.instrument = instrument;
  }

  public InstrumentNotValidException(String message, Throwable cause) {
    super(message, cause);
  }

  public InstrumentNotValidException(String message) {
    super(message);
  }

  public InstrumentNotValidException(String message, Instrument instrument) {
    super(message);
    this.instrument = instrument;
  }

  public InstrumentNotValidException(Throwable cause) {
    super(cause);
  }

  public InstrumentNotValidException(Throwable cause, Instrument instrument) {
    super(instrument + " is not valid for this operation", cause);
    this.instrument = instrument;
  }

  public InstrumentNotValidException(Instrument instrument) {
    this(instrument + " is not valid for this operation");
    this.instrument = instrument;
  }

  /**
   * @return The Instrument that caused the exception.
   */
  public Instrument getInstrument() {
    return instrument;
  }
}
