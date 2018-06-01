package org.knowm.xchange.cryptopia;

import java.util.regex.Pattern;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InvalidCurrencyPairException;

/**
 * Maps an error response to an appropriate ExchangeException class.
 *
 * <p>Unfortunately on Cryptopia we can only do this via error message pattern recognition
 *
 * @author walec51
 */
public class CryptopiaErrorAdapter {

  private static final Pattern MARKET_NOT_FOUND_PATTERN = Pattern.compile("^Market .*not found$");

  public static void throwIfErrorResponse(CryptopiaBaseResponse response) {
    if (response.getError() != null) {
      throwBasedOnErrorMessage(response.getError());
    }
    if (!response.isSuccess()) {
      throw new ExchangeException("Operation was unsuccessful for unknown reasons");
    }
  }

  private static void throwBasedOnErrorMessage(String message) {
    if (MARKET_NOT_FOUND_PATTERN.matcher(message).matches()) {
      throw new InvalidCurrencyPairException(message);
    }
    throw new ExchangeException(message);
  }
}
