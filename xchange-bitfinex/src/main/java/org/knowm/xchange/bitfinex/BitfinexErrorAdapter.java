package org.knowm.xchange.bitfinex;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

public class BitfinexErrorAdapter {

  private BitfinexErrorAdapter() {}

  public static ExchangeException adapt(BitfinexException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      return new ExchangeException(e);
    }
    message = message.toLowerCase();
    if (message.contains("unknown symbol") || message.contains("symbol: invalid")) {
      return new CurrencyPairNotValidException(message, e);
    } else if (message.contains("not enough exchange balance")) {
      return new FundsExceededException(message, e);
    } else if (message.contains("err_rate_limit")) {
      return new RateLimitExceededException(e);
    } else if (message.contains("nonce")) {
      return new NonceException(e);
    }
    return new ExchangeException(message, e);
  }
}
