package org.knowm.xchange.kucoin;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class KucoinMarketDataService extends KucoinMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Set on calls to {@link #getOrderBook(CurrencyPair, Object...)} to return the full orderbook
   * rather than the default 100 prices either side.
   */
  public static final String PARAM_FULL_ORDERBOOK = "Full_Orderbook";

  /**
   * Set on calls to {@link #getOrderBook(CurrencyPair, Object...)} to return the shallow partial
   * orderbook depth of 20.
   */
  public static final String PARAM_PARTIAL_SHALLOW_ORDERBOOK = "Shallow_Orderbook";

  protected KucoinMarketDataService(
      KucoinExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return KucoinAdapters.adaptTickerFull(currencyPair, getKucoin24hrStats(currencyPair)).build();
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return KucoinAdapters.adaptAllTickers(getKucoinTickers());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    if (Arrays.asList(args).contains(PARAM_FULL_ORDERBOOK)) {
      return KucoinAdapters.adaptOrderBook(currencyPair, getKucoinOrderBookFull(currencyPair));
    } else {
      if (Arrays.asList(args).contains(PARAM_PARTIAL_SHALLOW_ORDERBOOK)) {
        return KucoinAdapters.adaptOrderBook(
            currencyPair, getKucoinOrderBookPartialShallow(currencyPair));
      }
      return KucoinAdapters.adaptOrderBook(currencyPair, getKucoinOrderBookPartial(currencyPair));
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return KucoinAdapters.adaptTrades(currencyPair, getKucoinTrades(currencyPair));
  }
}
