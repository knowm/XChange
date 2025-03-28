package info.bitrich.xchangestream.bitmex;

import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitmex.dto.BitmexExecution;
import info.bitrich.xchangestream.bitmex.dto.BitmexFunding;
import info.bitrich.xchangestream.bitmex.dto.BitmexLimitOrder;
import info.bitrich.xchangestream.bitmex.dto.BitmexOrderbook;
import info.bitrich.xchangestream.bitmex.dto.BitmexTicker;
import info.bitrich.xchangestream.bitmex.dto.BitmexTrade;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketTransaction;
import info.bitrich.xchangestream.bitmex.dto.RawOrderBook;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitmexStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOG = LoggerFactory.getLogger(BitmexStreamingMarketDataService.class);

  private final ObjectMapper objectMapper = StreamingObjectMapperHelper.getObjectMapper();

  private final BitmexStreamingService streamingService;

  private final SortedMap<String, BitmexOrderbook> orderbooks = new TreeMap<>();

  public BitmexStreamingMarketDataService(BitmexStreamingService streamingService) {
    this.streamingService = streamingService;
    this.streamingService
        .subscribeConnectionSuccess()
        .subscribe(
            o -> {
              LOG.info("Bitmex connection succeeded. Clearing orderbooks.");
              orderbooks.clear();
            });
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String symbol = BitmexStreamingAdapters.toString(currencyPair);
    String channelName = String.format("orderBookL2:%s", symbol);
    boolean fullBook = false;
    if (args != null && args.length > 0) {
      if (args[0] instanceof String && "10".equals(args[0])) {
        channelName = String.format("orderBook10:%s", symbol);
        fullBook = true;
      } else {
        channelName = String.format("orderBookL2_25:%s", symbol);
      }
    }

    if (fullBook) {
      return streamingService
          .subscribeBitmexChannel(channelName)
          .map(
              s -> {
                RawOrderBook orderBook = s.toRawOrderBook();
                if (orderBook != null) {
                  List<LimitOrder> asks = new ArrayList<>(orderBook.getAsks().size());
                  List<LimitOrder> bids = new ArrayList<>(orderBook.getBids().size());
                  orderBook
                      .getAsks()
                      .forEach(
                          r -> {
                            LimitOrder order =
                                new LimitOrder.Builder(ASK, currencyPair)
                                    .originalAmount(r.getSize())
                                    .limitPrice(r.getPrice())
                                    .build();
                            asks.add(order);
                          });
                  orderBook
                      .getBids()
                      .forEach(
                          r -> {
                            LimitOrder order =
                                new LimitOrder.Builder(BID, currencyPair)
                                    .originalAmount(r.getSize())
                                    .limitPrice(r.getPrice())
                                    .build();
                            bids.add(order);
                          });
                  return new OrderBook(new Date(), asks, bids);
                }
                return new OrderBook(new Date(), Collections.emptyList(), Collections.emptyList());
              });
    } else {
      return streamingService
          .subscribeBitmexChannel(channelName)
          .map(
              s -> {
                BitmexOrderbook orderbook;
                String action = s.getAction();
                if ("partial".equals(action)) {
                  orderbook = s.toBitmexOrderbook();
                  orderbooks.put(symbol, orderbook);
                } else {
                  orderbook = orderbooks.get(symbol);
                  // ignore updates until first "partial"
                  if (orderbook == null) {
                    return new OrderBook(
                        new Date(), Collections.emptyList(), Collections.emptyList());
                  }
                  BitmexLimitOrder[] levels = s.toBitmexOrderbookLevels();
                  orderbook.updateLevels(levels, action);
                }

                return orderbook.toOrderbook();
              });
    }
  }

  public Observable<RawOrderBook> getRawOrderBook(CurrencyPair currencyPair) {
    String symbol = BitmexStreamingAdapters.toString(currencyPair);
    String channelName = String.format("orderBook10:%s", symbol);
    return streamingService
        .subscribeBitmexChannel(channelName)
        .map(BitmexWebSocketTransaction::toRawOrderBook);
  }

  public Observable<BitmexTicker> getRawTicker(CurrencyPair currencyPair) {
    String symbol = BitmexStreamingAdapters.toString(currencyPair);
    String channelName = String.format("quote:%s", symbol);

    return streamingService
        .subscribeBitmexChannel(channelName)
        .map(BitmexWebSocketTransaction::toBitmexTicker);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String symbol = BitmexStreamingAdapters.toString(currencyPair);
    String channelName = String.format("quote:%s", symbol);

    return streamingService
        .subscribeBitmexChannel(channelName)
        .map(BitmexWebSocketTransaction::toBitmexTicker)
        .map(BitmexStreamingAdapters::toTicker);
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String instrument = BitmexStreamingAdapters.toString(currencyPair);
    String channelName = String.format("trade:%s", instrument);

    return streamingService
        .subscribeBitmexChannel(channelName)
        .flatMapIterable(
            s -> {
              BitmexTrade[] bitmexTrades = s.toBitmexTrades();
              List<Trade> trades = new ArrayList<>(bitmexTrades.length);
              for (BitmexTrade bitmexTrade : bitmexTrades) {
                trades.add(BitmexStreamingAdapters.toTrade(bitmexTrade));
              }
              return trades;
            });
  }

  public Observable<BitmexExecution> getRawExecutions(String symbol) {
    return streamingService
        .subscribeBitmexChannel("execution:" + symbol)
        .flatMapIterable(
            s -> {
              JsonNode executions = s.getData();
              List<BitmexExecution> bitmexExecutions = new ArrayList<>(executions.size());
              for (JsonNode execution : executions) {
                bitmexExecutions.add(objectMapper.treeToValue(execution, BitmexExecution.class));
              }
              return bitmexExecutions;
            });
  }

  public void enableDeadManSwitch() throws IOException {
    enableDeadManSwitch(
        BitmexStreamingService.DMS_RESUBSCRIBE, BitmexStreamingService.DMS_CANCEL_ALL_IN);
  }

  /**
   * @param rate in milliseconds to send updated
   * @param timeout milliseconds from now after which orders will be cancelled
   */
  public void enableDeadManSwitch(long rate, long timeout) throws IOException {
    streamingService.enableDeadMansSwitch(rate, timeout);
  }

  public boolean isDeadManSwitchEnabled() throws IOException {
    return streamingService.isDeadMansSwitchEnabled();
  }

  public void disableDeadMansSwitch() throws IOException {
    streamingService.disableDeadMansSwitch();
  }

  public Observable<BitmexFunding> getRawFunding() {
    String channelName = "funding";
    return streamingService
        .subscribeBitmexChannel(channelName)
        .map(BitmexWebSocketTransaction::toBitmexFunding);
  }
}
