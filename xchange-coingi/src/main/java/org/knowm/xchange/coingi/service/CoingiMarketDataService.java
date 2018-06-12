package org.knowm.xchange.coingi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.coingi.dto.marketdata.CoingiOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoingiMarketDataService extends CoingiMarketDataServiceRaw
    implements MarketDataService {
  public CoingiMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... objects) throws IOException {
    OrderBook orderBook = getOrderBook(currencyPair, 1, 1, 1);

    return new Ticker.Builder()
        .currencyPair(orderBook.getAsks().get(0).getCurrencyPair())
        .ask(orderBook.getAsks().get(0).getLimitPrice())
        .bid(orderBook.getBids().get(0).getLimitPrice())
        .build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... objects) throws IOException {
    int maxAskCount = (int) objects[0];
    int maxBidCount = (int) objects[1];
    int maxDepthRangeCount = (int) objects[2];
    CoingiOrderBook orderBook =
        getCoingiOrderBook(currencyPair, maxAskCount, maxBidCount, maxDepthRangeCount);
    return CoingiAdapters.adaptOrderBook(orderBook);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... objects) throws IOException {
    int maxCount = (int) objects[0];
    return CoingiAdapters.adaptTrades(this.getTransactions(currencyPair, maxCount), currencyPair);
  }
}
