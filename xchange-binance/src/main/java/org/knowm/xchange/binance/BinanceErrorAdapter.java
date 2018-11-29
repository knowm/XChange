package org.knowm.xchange.binance;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author walec51 */
public class BinanceErrorAdapter {

  public static ExchangeException adapt(BinanceException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      message = "Operation failed without any error message";
    }
    switch (e.getCode()) {
      case -1121:
        return new CurrencyPairNotValidException(message, e);
    }
    return new ExchangeException(message, e);
  }
}
