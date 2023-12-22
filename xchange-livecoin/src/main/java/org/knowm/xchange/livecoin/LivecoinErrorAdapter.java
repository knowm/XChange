package org.knowm.xchange.livecoin;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.MarketSuspendedException;
import org.knowm.xchange.livecoin.dto.LivecoinException;

/**
 * @author walec51
 */
public class LivecoinErrorAdapter {

  private static final Pattern UNKNOWN_CURRENCY_MESSAGE =
      Pattern.compile(".*unknown currency pair.*", Pattern.CASE_INSENSITIVE);
  private static final Pattern TRADE_SUSPENDED_MESSAGE =
      Pattern.compile(".*trade on .* is temporarily unavailable.*", Pattern.CASE_INSENSITIVE);

  public static ExchangeException adapt(LivecoinException e) {
    String message = e.getErrorMessage();
    if (StringUtils.isEmpty(message)) {
      return new ExchangeException("Operation failed without any error message", e);
    }
    if (UNKNOWN_CURRENCY_MESSAGE.matcher(message).matches()) {
      return new CurrencyPairNotValidException(message, e);
    }
    if (TRADE_SUSPENDED_MESSAGE.matcher(message).matches()) {
      return new MarketSuspendedException(message, e);
    }
    return new ExchangeException(message, e);
  }
}
