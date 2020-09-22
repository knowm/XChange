package org.knowm.xchange.coinmarketcap.deprecated.v2.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinmarketcap.deprecated.v2.CoinMarketCap;
import org.knowm.xchange.coinmarketcap.deprecated.v2.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.deprecated.v2.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/** @author allenday */
class CoinMarketCapMarketDataServiceRaw extends BaseExchangeService implements BaseService {

  private final CoinMarketCap coinmarketcap;

  public CoinMarketCapMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.coinmarketcap =
        ExchangeRestProxyBuilder.forInterface(
                CoinMarketCap.class, exchange.getExchangeSpecification())
            .build();
  }

  public CoinMarketCapTicker getCoinMarketCapTicker(CurrencyPair pair) {

    return coinmarketcap.getTicker(new CoinMarketCap.Pair(pair));
  }

  /**
   * Unauthenticated resource that returns currencies supported on CoinMarketCap.
   *
   * @return A list of currency names and their corresponding ISO code.
   * @throws IOException
   */
  public List<CoinMarketCapCurrency> getCoinMarketCapCurrencies() throws IOException {

    List<CoinMarketCapTicker> tickers = getCoinMarketCapTickers();
    List<CoinMarketCapCurrency> currencies = new ArrayList<>();
    for (CoinMarketCapTicker ticker : tickers) currencies.add(ticker.getBaseCurrency());
    return currencies;
  }

  /** Retrieves all tickers from CoinMarketCap */
  public List<CoinMarketCapTicker> getCoinMarketCapTickers() throws IOException {
    return getCoinMarketCapTickers(0);
  }

  /**
   * Retrieves limited amount of tickers from CoinMarketCap
   *
   * @param limit count of tickers to be retrieved
   */
  public List<CoinMarketCapTicker> getCoinMarketCapTickers(final int limit) throws IOException {
    return coinmarketcap.getTickers(limit, "array").getData();
  }

  /**
   * Retrieves limited amount of tickers from CoinMarketCap
   *
   * @param limit count of tickers to be retrieved
   * @param convert currency to get price converted to
   */
  public List<CoinMarketCapTicker> getCoinMarketCapTickers(final int limit, Currency convert)
      throws IOException {
    return coinmarketcap.getTickers(limit, convert.toString(), "array").getData();
  }

  public List<CoinMarketCapTicker> getCoinMarketCapTickers(int start, int limit)
      throws IOException {

    return coinmarketcap.getTickers(start, limit, "array").getData();
  }

  public List<CoinMarketCapTicker> getCoinMarketCapTickers(int start, int limit, Currency convert)
      throws IOException {

    return coinmarketcap.getTickers(start, limit, convert.toString(), "array").getData();
  }
}
