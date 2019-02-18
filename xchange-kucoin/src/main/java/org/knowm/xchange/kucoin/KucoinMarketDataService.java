package org.knowm.xchange.kucoin;

import java.io.IOException;
import java.util.Arrays;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KucoinMarketDataService extends KucoinMarketDataServiceRaw implements MarketDataService {

  /**
   * Kucoin returns ticker data in two distinct API calls; one returns the actual
   * price data and the other the 24h statistics. XChange returns both in the same
   * API, but this means making two API calls, which may not be necessary. Supply
   * this to {@link #getTicker(CurrencyPair, Object...)} if you don't need full
   * information, to avoid spamming the API.
   */
  public static final String PARAM_MINIMAL_TICKER = "Exclude_24h";

  /**
   * Set on calls to {@link #getOrderBook(CurrencyPair, Object...)} to return the
   * full orderbook rather than the default 100 prices either side.
   */
  public static final String PARAM_FULL_ORDERBOOK = "Full_Orderbook";

  public KucoinMarketDataService(KucoinExchange exchange) {
    super(exchange);
  }

  /**
   * Returns only partial information, omitting 24h statistics, unless called with
   * the optional parameter {@link #PARAM_DETAILED_TICKER}.
   */
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    if (Arrays.asList(args).contains(PARAM_MINIMAL_TICKER)) {
      return KucoinAdapters.adaptTickerPartial(currencyPair, getKucoinTicker(currencyPair))
        .build();
    } else {
      return KucoinAdapters.adaptTickerFull(
          currencyPair,
          getKucoinTicker(currencyPair),
          getKucoin24hrStats(currencyPair)
        ).build();
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    if (Arrays.asList(args).contains(PARAM_FULL_ORDERBOOK)) {
      return KucoinAdapters.adaptOrderBook(
        currencyPair,
        getKucoinOrderBookFull(currencyPair));
    } else {
      return KucoinAdapters.adaptOrderBook(
        currencyPair,
        getKucoinOrderBookPartial(currencyPair));
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return KucoinAdapters.adaptTrades(currencyPair, getKucoinTrades(currencyPair));
  }
}