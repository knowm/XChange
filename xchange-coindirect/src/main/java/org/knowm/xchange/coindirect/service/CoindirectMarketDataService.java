package org.knowm.xchange.coindirect.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectAdapters;
import org.knowm.xchange.coindirect.dto.marketdata.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class CoindirectMarketDataService extends CoindirectMarketDataServiceRaw
    implements MarketDataService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoindirectMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    CoindirectOrderbook coindirectOrderbook = getCoindirectOrderbook(currencyPair);
    return CoindirectAdapters.adaptOrderBook(currencyPair, coindirectOrderbook);
  }

  @Override
  public Trades getTrades(CurrencyPair pair, Object... args) throws IOException {
    String history = "1h";

    try {
      if (args[0] != null) history = args[0].toString();
    } catch (Throwable ignored) {
    }

    CoindirectTrades coindirectTrades = getCoindirectTrades(pair, history);

    List<Trade> trades;

    if (coindirectTrades.data == null) {
      trades = new ArrayList<>();
    } else {
      trades =
          coindirectTrades.data.stream()
              .map(
                  at ->
                      new Trade.Builder()
                          .type(Order.OrderType.BID)
                          .originalAmount(at.volume)
                          .currencyPair(pair)
                          .price(at.price)
                          .timestamp(new Date(at.time))
                          .id(Long.toString(at.time))
                          .build())
              .collect(Collectors.toList());
    }

    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    String history = "24h";
    String grouping = "d";

    try {
      if (args[0] != null) history = args[0].toString();
    } catch (Throwable ignored) {
    }

    try {
      if (args[1] != null) grouping = args[1].toString();
    } catch (Throwable ignored) {
    }

    CoindirectTicker coindirectTicker = getCoindirectTicker(currencyPair, history, grouping);

    if (coindirectTicker.data.size() > 0) {
      CoindirectTickerData coindirectTickerData = coindirectTicker.data.get(0);
      return new Ticker.Builder()
          .currencyPair(currencyPair)
          .open(coindirectTickerData.open)
          .last(coindirectTickerData.close)
          .high(coindirectTickerData.high)
          .low(coindirectTickerData.low)
          .volume(coindirectTickerData.volume)
          .build();
    }

    return new Ticker.Builder().currencyPair(currencyPair).build();
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    List<CoindirectMarket> coindirectMarkets = getCoindirectMarkets(1000); /* default max */

    List<Ticker> tickerList = new ArrayList<>();
    for (int i = 0; i < coindirectMarkets.size(); i++) {
      tickerList.add(getTicker(CoindirectAdapters.toCurrencyPair(coindirectMarkets.get(i).symbol)));
    }

    return tickerList;
  }
}
