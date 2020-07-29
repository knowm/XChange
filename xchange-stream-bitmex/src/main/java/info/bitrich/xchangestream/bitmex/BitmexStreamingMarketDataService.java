package info.bitrich.xchangestream.bitmex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitmex.dto.*;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.io.IOException;
import java.util.*;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 13.11.17. */
public class BitmexStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOG = LoggerFactory.getLogger(BitmexStreamingMarketDataService.class);

  private final ObjectMapper objectMapper = StreamingObjectMapperHelper.getObjectMapper();

  private final BitmexStreamingService streamingService;

  private final BitmexExchange bitmexExchange;

  private final SortedMap<String, BitmexOrderbook> orderbooks = new TreeMap<>();

  public BitmexStreamingMarketDataService(
      BitmexStreamingService streamingService, BitmexExchange bitmexExchange) {
    this.streamingService = streamingService;
    this.streamingService
        .subscribeConnectionSuccess()
        .subscribe(
            o -> {
              LOG.info("Bitmex connection succeeded. Clearing orderbooks.");
              orderbooks.clear();
            });
    this.bitmexExchange = bitmexExchange;
  }

  private String getBitmexSymbol(CurrencyPair currencyPair) {
    return currencyPair.base.toString() + currencyPair.counter.toString();
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String instrument = getBitmexSymbol(currencyPair);
    String channelName =
        args != null && args.length > 0
            ? String.format("orderBookL2_25:%s", instrument)
            : String.format("orderBookL2:%s", instrument);

    return streamingService
        .subscribeBitmexChannel(channelName)
        .map(
            s -> {
              BitmexOrderbook orderbook;
              String action = s.getAction();
              if (action.equals("partial")) {
                orderbook = s.toBitmexOrderbook();
                orderbooks.put(instrument, orderbook);
              } else {
                orderbook = orderbooks.get(instrument);
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

  public Observable<BitmexTicker> getRawTicker(CurrencyPair currencyPair) {
    String instrument = getBitmexSymbol(currencyPair);
    String channelName = String.format("quote:%s", instrument);

    return streamingService.subscribeBitmexChannel(channelName).map(s -> s.toBitmexTicker());
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String instrument = getBitmexSymbol(currencyPair);
    String channelName = String.format("quote:%s", instrument);

    return streamingService
        .subscribeBitmexChannel(channelName)
        .map(
            s -> {
              BitmexTicker bitmexTicker = s.toBitmexTicker();
              return bitmexTicker.toTicker();
            });
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String instrument = getBitmexSymbol(currencyPair);
    String channelName = String.format("trade:%s", instrument);

    return streamingService
        .subscribeBitmexChannel(channelName)
        .flatMapIterable(
            s -> {
              BitmexTrade[] bitmexTrades = s.toBitmexTrades();
              List<Trade> trades = new ArrayList<>(bitmexTrades.length);
              for (BitmexTrade bitmexTrade : bitmexTrades) {
                trades.add(bitmexTrade.toTrade());
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
