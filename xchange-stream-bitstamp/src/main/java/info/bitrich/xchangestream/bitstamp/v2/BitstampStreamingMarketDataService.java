package info.bitrich.xchangestream.bitstamp.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitstamp.dto.BitstampWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

/**
 * Bitstamp WebSocket V2 Streaming Market Data Service implementation Created by Pavel Chertalev on
 * 15.03.2018.
 */
public class BitstampStreamingMarketDataService implements StreamingMarketDataService {
  private final BitstampStreamingService service;

  public BitstampStreamingMarketDataService(BitstampStreamingService service) {
    this.service = service;
  }

  public Observable<OrderBook> getFullOrderBook(CurrencyPair currencyPair, Object... args) {
    return getOrderBook("diff_order_book", currencyPair, args);
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    return getOrderBook("order_book", currencyPair, args);
  }

  private Observable<OrderBook> getOrderBook(
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
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    // BitStamp has no live ticker, only trades.
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
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
