package org.knowm.xchange.exceptions;

import org.knowm.xchange.instrument.Instrument;

/**
 * Exception indicating the {@link Instrument} was recognized by the exchange but their market is
 * suspended - either temporarly or permanently.
 *
 * <p>This exception does not suggest that the entire exhange is down (we have the {@link
 * ExchangeUnavailableException} for that
 *
 * @author walec51
 */
public class MarketSuspendedException extends ExchangeException {

  private static final String DEFAULT_MESSAGE = "Market is suspended";

  public MarketSuspendedException() {
    super(DEFAULT_MESSAGE);
  }

  public MarketSuspendedException(String message) {
    super(message);
  }

  public MarketSuspendedException(Throwable cause) {
    super(DEFAULT_MESSAGE, cause);
  }

  public MarketSuspendedException(String message, Throwable cause) {
    super(message, cause);
  }
}
