package org.knowm.xchange.coinmarketcap.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.CoinMarketCap;

import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author allenday
 */
class CoinMarketCapMarketDataServiceRaw implements BaseService {
  private final CoinMarketCap coinmarketcap;

  public CoinMarketCapMarketDataServiceRaw(Exchange exchange) {
    this.coinmarketcap = RestProxyFactory.createProxy(CoinMarketCap.class, exchange.getExchangeSpecification().getSslUri());
  }

  public CoinMarketCapTicker getCoinMarketCapTicker(CurrencyPair pair) throws IOException {
    return coinmarketcap.getTicker(new CoinMarketCap.Pair(pair));
  }

  /**
   * Unauthenticated resource that returns currencies supported on CoinMarketCap.
   *
   * @return A list of currency names and their corresponding ISO code.
   * @throws IOException
   */
  public List<CoinMarketCapCurrency> getCoinMarketCapCurrencies() throws IOException {
    List<CoinMarketCapTicker> tickers = coinmarketcap.getTickers();
    List<CoinMarketCapCurrency> currencies = new ArrayList<>();
    for (CoinMarketCapTicker ticker : tickers)
      currencies.add(ticker.getBaseCurrency());
    return currencies;
  }

  public List<CoinMarketCapTicker> getCoinMarketCapTickers() throws IOException {
    return coinmarketcap.getTickers();
  }
}
