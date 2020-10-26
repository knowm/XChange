package org.knowm.xchange.bittrex;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author walec51 */
public class BittrexErrorAdapter {

  public static ExchangeException adapt(BittrexException e) {
    String code = e.getCode();
    if (StringUtils.isEmpty(code)) {
      return new ExchangeException("Operation failed without any error code", e);
    }
    switch (code) {
      case "MARKET_DOES_NOT_EXIST":
        return new CurrencyPairNotValidException(e);
      default:
        String message = e.getDetails();
        if (StringUtils.isEmpty(message)) {
          message = "Operation failed with error code: " + code;
        }
        return new ExchangeException(message, e);
    }
  }
}
