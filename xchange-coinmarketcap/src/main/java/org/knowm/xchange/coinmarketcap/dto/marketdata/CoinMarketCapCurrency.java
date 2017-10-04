package org.knowm.xchange.coinmarketcap.dto.marketdata;

import org.knowm.xchange.currency.Currency;

public class CoinMarketCapCurrency {
  Currency currency;
  public CoinMarketCapCurrency(String code) {
    currency = new Currency(code);
  }

  public Currency getCurrency() {
    return currency;
  }
}