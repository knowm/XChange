package org.knowm.xchange.bittrex;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

/** @author walec51 */
public class BittrexErrorAdapter {

  public static ExchangeException adapt(BittrexException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      return new ExchangeException("Operation failed without any error message");
    }
    if (message.contains("was throttled")) {
      return new RateLimitExceededException(e.getMessage(), e);
    }
    switch (message) {
      case "INVALID_MARKET":
        return new CurrencyPairNotValidException();
    }
    return new ExchangeException(message);
  }
}
