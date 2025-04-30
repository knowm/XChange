package org.knowm.xchange.coinex;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.exceptions.OrderAmountUnderMinimumException;
import org.knowm.xchange.exceptions.OrderNotValidException;

@UtilityClass
public class CoinexErrorAdapter {

  public final int AMOUNT_TOO_SMALL = 3127;
  public final int ORDER_NOT_FOUND = 3600;
  public final int INVALID_MARKET_CODE = 3639;

  public ExchangeException adapt(CoinexException e) {

    switch (e.getCode()) {
      case AMOUNT_TOO_SMALL:
        return new OrderAmountUnderMinimumException(e.getMessage(), e);

      case ORDER_NOT_FOUND:
        return new OrderNotValidException(e.getMessage(), e);

      case INVALID_MARKET_CODE:
        return new InstrumentNotValidException(e.getMessage(), e);

      default:
    }

    return new ExchangeException(e.getMessage(), e);
  }
}
