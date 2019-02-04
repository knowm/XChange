package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CoinMarketCapCurrencyResponse;
import org.knowm.xchange.currency.Currency;

import java.io.IOException;

/** @author allenday */
class CoinMarketCapMarketDataServiceRaw extends CoinMarketCapBaseService {

  public CoinMarketCapMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoinMarketCapCurrencyResponse getCoinMarketCapCurrencyInfo(Currency currency)
      throws IOException {

    return coinMarketCapAuthenticated.getCurrencyInfo(super.apiKey, currency.toString());
  }

  public CoinMarketCapCurrencyResponse getCoinMarketCapCurrencyInfo(int id) throws IOException {

    return coinMarketCapAuthenticated.getCurrencyInfo(super.apiKey, id);
  }
}
