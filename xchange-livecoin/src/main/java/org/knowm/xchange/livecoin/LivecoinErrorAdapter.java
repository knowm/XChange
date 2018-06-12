package org.knowm.xchange.livecoin;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.livecoin.dto.LivecoinException;

/** @author walec51 */
public class LivecoinErrorAdapter {

  private static final String UNKNOWN_CURRENCY_MESSAGE_START = "Unknown currency pair";

  public static ExchangeException adapt(LivecoinException e) {
    String message = e.getErrorMessage();
    if (StringUtils.isEmpty(message)) {
      return new ExchangeException("Operation failed without any error message");
    }
    if (message.startsWith(UNKNOWN_CURRENCY_MESSAGE_START)) {
      return new CurrencyPairNotValidException(message);
    }
    return new ExchangeException(message);
  }
}
