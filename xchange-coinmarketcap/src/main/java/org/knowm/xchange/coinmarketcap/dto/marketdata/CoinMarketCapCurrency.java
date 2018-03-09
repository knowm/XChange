package org.knowm.xchange.coinmarketcap.dto.marketdata;

import org.knowm.xchange.currency.Currency;

public class CoinMarketCapCurrency {
  Currency currency;

  public CoinMarketCapCurrency(String code) {
    currency = Currency.getInstance(code);
  }

  public Currency getCurrency() {
    return currency;
  }
}
