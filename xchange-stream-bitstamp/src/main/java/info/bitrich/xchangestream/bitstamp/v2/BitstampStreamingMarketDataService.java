package info.bitrich.xchangestream.bitstamp.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitstamp.dto.BitstampWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Flowable;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * Bitstamp WebSocket V2 Streaming Market Data Service implementation Created by Pavel Chertalev on
 * 15.03.2018.
 */
public class BitstampStreamingMarketDataService implements StreamingMarketDataService {
  private final BitstampStreamingService service;

  public BitstampStreamingMarketDataService(BitstampStreamingService service) {
    this.service = service;
  }

  public Flowable<OrderBook> getFullOrderBook(CurrencyPair currencyPair, Object... args) {
    return getOrderBook("diff_order_book", currencyPair, args);
  }

  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    return getOrderBook("order_book", currencyPair, args);
  }

  private Flowable<OrderBook> getOrderBook(
      String channelPrefix, CurrencyPair currencyPair, Object... args) {
    String channelName = channelPrefix + getChannelPostfix(currencyPair);

    return service
        .subscribeChannel(channelName, BitstampStreamingService.EVENT_ORDERBOOK)
        .map(
            s -> {
              ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
              BitstampOrderBook orderBook =
                  mapper.treeToValue(s.get("data"), BitstampOrderBook.class);
              return BitstampAdapters.adaptOrderBook(orderBook, currencyPair);
            });
  }

  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return getOrderBook(currencyPair, args)
        .map(orderBook -> mapOrderBookToTicker(currencyPair, orderBook));
  }

  private Ticker mapOrderBookToTicker(CurrencyPair currencyPair, OrderBook orderBook) {
    final LimitOrder ask = orderBook.getAsks().get(0);
    final LimitOrder bid = orderBook.getBids().get(0);

    return new Ticker.Builder()
        .instrument(currencyPair)
        .bid(bid.getLimitPrice())
        .bidSize(bid.getOriginalAmount())
        .ask(ask.getLimitPrice())
        .askSize(ask.getOriginalAmount())
        .timestamp(orderBook.getTimeStamp())
        .build();
  }

  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName = "live_trades" + getChannelPostfix(currencyPair);

    return service
        .subscribeChannel(channelName, BitstampStreamingService.EVENT_TRADE)
        .map(
            s -> {
              ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
              BitstampWebSocketTransaction transactions =
                  mapper.treeToValue(s.get("data"), BitstampWebSocketTransaction.class);
              return BitstampAdapters.adaptTrade(transactions, currencyPair, 1);
            });
  }

  private String getChannelPostfix(CurrencyPair currencyPair) {
    return "_"
        + currencyPair.base.toString().toLowerCase()
        + currencyPair.counter.toString().toLowerCase();
  }
}
