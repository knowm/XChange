package org.knowm.xchange.cryptopia;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.cryptopia.dto.CryptopiaException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * Maps an error response to an appropriate ExchangeException class.
 *
 * <p>Unfortunately on Cryptopia we can only do this via error message pattern recognition
 *
 * @author walec51
 */
public class CryptopiaErrorAdapter {

  private static final Pattern MARKET_NOT_FOUND_PATTERN = Pattern.compile("^Market .*not found$");

  public static ExchangeException adapt(CryptopiaException e) {
    String message = e.getError();
    if (StringUtils.isEmpty(message)) {
      return new ExchangeException("Operation failed without any error message");
    }
    if (MARKET_NOT_FOUND_PATTERN.matcher(message).matches()) {
      return new CurrencyPairNotValidException(message);
    }
    return new ExchangeException(message);
  }
}
