package org.knowm.xchange.binance;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.ExchangeUnavailableException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.OperationTimeoutException;
import org.knowm.xchange.exceptions.OrderAmountUnderMinimumException;
import org.knowm.xchange.exceptions.OrderNotValidException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

/** @author walec51 */
public final class BinanceErrorAdapter {

  private BinanceErrorAdapter() {}

  public static ExchangeException adapt(BinanceException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      message = "Operation failed without any error message";
    }
    switch (e.getCode()) {
      case -1002:
        return new ExchangeSecurityException(message, e);
      case -1003:
        return new RateLimitExceededException(message, e);
      case -1010:
      case -2010:
      case -2011:
        if (e.getMessage().contains("insufficient balance")) {
          return new FundsExceededException(e.getMessage(), e);
        } else {
          return new ExchangeException(message, e);
        }
      case -1013:
        return createOrderNotValidException(message, e);
      case -1016:
        return new ExchangeUnavailableException(message, e);
      case -1021:
        return new OperationTimeoutException(message, e);
      case -1121:
        return new CurrencyPairNotValidException(message, e);
      case -1122:
        return new ExchangeSecurityException(message, e);
      default:
        return new ExchangeException(message, e);
    }
  }

  private static ExchangeException createOrderNotValidException(
      String message, BinanceException e) {
    if (e.getMessage().contains("MIN_NOTIONAL")) {
      return new OrderAmountUnderMinimumException(message, e);
    } else {
      return new OrderNotValidException(message, e);
    }
  }
}
