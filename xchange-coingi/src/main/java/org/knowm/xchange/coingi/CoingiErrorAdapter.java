package org.knowm.xchange.coingi;

import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

public class CoingiErrorAdapter {
  public static ExchangeException adapt(CoingiException e) {
    String message = e.getMessage();
    if (message == null || "".equals(message)) {
      return new ExchangeException("Operation failed without any error message");
    }

    if (message.contains("Invalid value for parameter \"currencyPair\"")) {
      return new CurrencyPairNotValidException(message);
    }

    return new ExchangeException(message);
  }
}
