package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CoinMarketCapCurrencyInfoResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CoinMarketCapCurrencyMapResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CoinMarketCapTickerResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;


class CoinMarketCapMarketDataServiceRaw extends CoinMarketCapBaseService {

  public CoinMarketCapMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoinMarketCapCurrencyInfoResponse getCoinMarketCapCurrencyInfo(Currency currency)
      throws IOException {
    return coinMarketCapAuthenticated.getCurrencyInfo(super.apiKey, currency.toString());
  }

  public CoinMarketCapCurrencyMapResponse getCoinMarketCapCurrencyMap() throws IOException {
    return coinMarketCapAuthenticated.getCurrencyMap(super.apiKey, "active", 1, 5000);
  }

  public CoinMarketCapTickerResponse getCoinMarketCapCurrencyList()
          throws IOException {

    return coinMarketCapAuthenticated.getLatestForAllCurrency(super.apiKey, 1, 5000,
            Currency.USD.getCurrencyCode(), "symbol", "asc", "all");
  }

  public CoinMarketCapTickerResponse getCoinMarketCapCurrencyList(int startIndex, int limitIndex, )
          throws IOException {

    return coinMarketCapAuthenticated.getLatestForAllCurrency(super.apiKey, 1, 5000,
            Currency.USD.getCurrencyCode(), "symbol", "asc", "all");
  }

  public CoinMarketCapTickerResponse getCoinMarketCapLatestQuote(CurrencyPair currencyPair)
          throws IOException {
    return coinMarketCapAuthenticated.getLatestQuote(super.apiKey,
            currencyPair.base.getCurrencyCode(),
            currencyPair.counter.getCurrencyCode());
  }
}
