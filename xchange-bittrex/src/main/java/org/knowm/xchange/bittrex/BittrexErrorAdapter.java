package org.knowm.xchange.bittrex;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

/** @author walec51 */
public class BittrexErrorAdapter {

  public static ExchangeException adapt(BittrexException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      return new ExchangeException("Operation failed without any error message", e);
    }
    if (message.contains("was throttled")) {
      return new RateLimitExceededException(message, e);
    }
    switch (message) {
      case "INVALID_MARKET":
        return new CurrencyPairNotValidException(e);
      case "INSUFFICIENT_FUNDS":
        return new FundsExceededException(e);
      default:
        return new ExchangeException(message, e);
    }
  }
}
