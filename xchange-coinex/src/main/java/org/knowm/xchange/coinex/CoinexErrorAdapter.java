package org.knowm.xchange.coinex;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;

@UtilityClass
public class CoinexErrorAdapter {

  public final int INVALID_MARKET_CODE = 3639;

  public ExchangeException adapt(CoinexException e) {

    switch (e.getCode()) {
      case INVALID_MARKET_CODE:
        return new InstrumentNotValidException(e.getMessage(), e);

      default:
    }

    return new ExchangeException(e.getMessage(), e);
  }
}
