package org.knowm.xchange.coinjar;

import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public class CoinjarErrorAdapter {
  private CoinjarErrorAdapter() {}

  public static ExchangeException adaptCoinjarException(CoinjarException e) {
    switch (e.errorType) {
      case "NOT_FOUND":
        throw new CurrencyPairNotValidException(e.getFirstMessage(), e);
      case "NO_AUTHORITY":
      case "PRODUCT_NOT_PERMITTED":
        throw new ExchangeSecurityException(e.getFirstMessage(), e);
      default:
        throw new ExchangeException(e.getFirstMessage(), e);
    }
  }
}
