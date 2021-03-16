package info.bitrich.xchangestream.poloniex2;

import static org.knowm.xchange.poloniex.PoloniexAdapters.adaptPoloniexDepth;
import static org.knowm.xchange.poloniex.PoloniexAdapters.adaptPoloniexTicker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.poloniex2.dto.*;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Flowable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 10.11.17. */
public class PoloniexStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOG =
      LoggerFactory.getLogger(PoloniexStreamingMarketDataService.class);
  private static final String TICKER_CHANNEL_ID = "1002";

  private final PoloniexStreamingService service;
  private final Supplier<Flowable<Ticker>> streamingTickers;

  public PoloniexStreamingMarketDataService(
      PoloniexStreamingService service, Map<Integer, CurrencyPair> currencyIdMap) {
    this.service = service;
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    streamingTickers =
        Suppliers.memoize(
            () ->
                service
                    .subscribeChannel(TICKER_CHANNEL_ID)
                    .map(
                        s -> {
                          PoloniexWebSocketTickerTransaction ticker =
                              mapper.readValue(
                                  s.toString(), PoloniexWebSocketTickerTransaction.class);
                          CurrencyPair currencyPair = currencyIdMap.get(ticker.getPairId());
                          return adaptPoloniexTicker(
                              ticker.toPoloniexTicker(currencyPair), currencyPair);
                        })
                    .share());
  }

  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    Flowable<PoloniexOrderbook> subscribedOrderbook =
        service
            .subscribeCurrencyPairChannel(currencyPair)
            .scan(
                Optional.empty(),
                (Optional<PoloniexOrderbook> orderbook,
                    List<PoloniexWebSocketEvent> poloniexWebSocketEvents) ->
                    poloniexWebSocketEvents.stream()
                        .filter(
                            s ->
                                s instanceof PoloniexWebSocketOrderbookInsertEvent
                                    || s instanceof PoloniexWebSocketOrderbookModifiedEvent)
                        .reduce(
                            orderbook,
                            (poloniexOrderbook, s) -> getPoloniexOrderbook(orderbook, s),
                            (o1, o2) -> {
                              throw new UnsupportedOperationException("No parallel execution");
                            }))
            .filter(Optional::isPresent)
            .map(Optional::get);

    return subscribedOrderbook.map(s -> adaptPoloniexDepth(s.toPoloniexDepth(), currencyPair));
  }

  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return streamingTickers.get().filter(ticker -> ticker.getCurrencyPair().equals(currencyPair));
  }

  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    Flowable<PoloniexWebSocketTradeEvent> subscribedTrades =
        service
            .subscribeCurrencyPairChannel(currencyPair)
            .flatMapIterable(poloniexWebSocketEvents -> poloniexWebSocketEvents)
            .filter(PoloniexWebSocketTradeEvent.class::isInstance)
            .map(PoloniexWebSocketTradeEvent.class::cast)
            .publish(1).refCount();

    return subscribedTrades.map(
        s -> PoloniexWebSocketAdapter.convertPoloniexWebSocketTradeEventToTrade(s, currencyPair));
  }

  private Optional<PoloniexOrderbook> getPoloniexOrderbook(
      final Optional<PoloniexOrderbook> orderbook, final PoloniexWebSocketEvent s) {
    if (s.getEventType().equals("i")) {
      OrderbookInsertEvent insertEvent = ((PoloniexWebSocketOrderbookInsertEvent) s).getInsert();
      SortedMap<BigDecimal, BigDecimal> asks =
          insertEvent.toDepthLevels(OrderbookInsertEvent.ASK_SIDE);
      SortedMap<BigDecimal, BigDecimal> bids =
          insertEvent.toDepthLevels(OrderbookInsertEvent.BID_SIDE);
      return Optional.of(new PoloniexOrderbook(asks, bids));
    } else {
      OrderbookModifiedEvent modifiedEvent =
          ((PoloniexWebSocketOrderbookModifiedEvent) s).getModifiedEvent();
      orderbook
          .orElseThrow(
              () -> new IllegalStateException("Orderbook update received before initial snapshot"))
          .modify(modifiedEvent);
      return orderbook;
    }
  }
}
