package info.bitrich.xchangestream.bybit;

import static org.knowm.xchange.bybit.BybitAdapters.convertToBybitSymbol;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.marketdata.BybitPublicOrder;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import dto.marketdata.BybitOrderbook;
import dto.trade.BybitTrade;
import java.util.concurrent.atomic.AtomicLong;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamingMarketDataService implements StreamingMarketDataService {

  private final Logger LOG = LoggerFactory.getLogger(BybitStreamingMarketDataService.class);
  private final BybitStreamingService streamingService;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  public static final String TRADE = "publicTrade.";
  public static final String ORDERBOOK = "orderbook.";
  public static final String TICKER = "tickers.";

  private final Map<String, OrderBook> orderBookMap = new HashMap<>();
  private final Map<Instrument, PublishSubject<List<OrderBookUpdate>>>
      orderBookUpdatesSubscriptions;

  public BybitStreamingMarketDataService(BybitStreamingService streamingService) {
    this.streamingService = streamingService;
    this.orderBookUpdatesSubscriptions = new ConcurrentHashMap<>();
  }

  /**
   * Linear & inverse: Level 1 data, push frequency: 10ms Level 50 data, push frequency: 20ms Level
   * 200 data, push frequency: 100ms Level 500 data, push frequency: 100ms Spot: Level 1 data, push
   * frequency: 10ms Level 50 data, push frequency: 20ms Level 200 data, push frequency: 200ms
   *
   * @param args - orderbook depth
   **/
  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    String depth = "50";
    AtomicLong orderBookUpdateIdPrev = new AtomicLong();
    if (args.length > 0 && args[0] != null) {
      depth = args[0].toString();
    }
    String channelUniqueId =
        ORDERBOOK + depth + "." + convertToBybitSymbol(instrument);
    return streamingService
        .subscribeChannel(channelUniqueId)
        .flatMap(
            jsonNode -> {
              BybitOrderbook bybitOrderbooks =
                  mapper.treeToValue(jsonNode, BybitOrderbook.class);
              String type = bybitOrderbooks.getDataType();
              if (type.equalsIgnoreCase("snapshot")) {
                OrderBook orderBook = BybitStreamAdapters.adaptOrderBook(bybitOrderbooks, instrument);
                orderBookUpdateIdPrev.set(bybitOrderbooks.getData().getU());
                orderBookMap.put(channelUniqueId, orderBook);
                return Observable.just(orderBook);
              } else if (type.equalsIgnoreCase("delta")) {
                return applyDeltaSnapshot(channelUniqueId, instrument, bybitOrderbooks, orderBookUpdateIdPrev);
              }
              return Observable.fromIterable(new LinkedList<>());
            });
  }

  private Observable<OrderBook> applyDeltaSnapshot(String channelUniqueId, Instrument instrument,
      BybitOrderbook bybitOrderBookUpdate,AtomicLong orderBookUpdateIdPrev) {
    OrderBook orderBook = orderBookMap.getOrDefault(channelUniqueId, null);
    if (orderBook == null) {
      LOG.error("Failed to get orderBook, channelUniqueId= {}", channelUniqueId);
      return Observable.fromIterable(new LinkedList<>());
    }
    if (orderBookUpdateIdPrev.incrementAndGet() == bybitOrderBookUpdate.getData().getU()) {
      LOG.debug("orderBookUpdate id {}, seq {} ", bybitOrderBookUpdate.getData().getU(),
          bybitOrderBookUpdate.getData().getSeq());
      List<BybitPublicOrder> asks = bybitOrderBookUpdate.getData().getAsk();
      List<BybitPublicOrder> bids = bybitOrderBookUpdate.getData().getBid();
      Date timestamp = new Date(Long.parseLong(bybitOrderBookUpdate.getTs()));
      asks.forEach(
          bybitPublicOrder ->
              orderBook.update(
                  BybitStreamAdapters.adaptOrderBookOrder(
                      bybitPublicOrder, instrument, Order.OrderType.ASK, timestamp)));
      bids.forEach(
          bybitPublicOrder ->
              orderBook.update(
                  BybitStreamAdapters.adaptOrderBookOrder(
                      bybitPublicOrder, instrument, Order.OrderType.BID, timestamp)));
      if (orderBookUpdatesSubscriptions.get(instrument) != null) {
        orderBookUpdatesSubscriptions(instrument, asks, bids, timestamp);
      }
      return Observable.just(orderBook);
    } else {
      LOG.error("orderBookUpdate id sequence failed, expected {}, in fact {}",
          orderBookUpdateIdPrev,
          bybitOrderBookUpdate.getData().getU());
      // resubscribe or what here?
      return Observable.fromIterable(new LinkedList<>());
    }
  }

  @Override
  public Observable<List<OrderBookUpdate>> getOrderBookUpdates(Instrument instrument,Object... args) {
    return orderBookUpdatesSubscriptions.computeIfAbsent(instrument, v -> PublishSubject.create());
  }

  private void orderBookUpdatesSubscriptions(
      Instrument instrument, List<BybitPublicOrder> asks, List<BybitPublicOrder> bids, Date date) {
    List<OrderBookUpdate> orderBookUpdates = new ArrayList<>();
    for (BybitPublicOrder ask : asks) {
      OrderBookUpdate o =
          new OrderBookUpdate(
              Order.OrderType.ASK,
              new BigDecimal(ask.getSize()),
              instrument,
              new BigDecimal(ask.getPrice()),
              date,
              new BigDecimal(ask.getSize()));
      orderBookUpdates.add(o);
    }
    for (BybitPublicOrder bid : bids) {
      OrderBookUpdate o =
          new OrderBookUpdate(
              Order.OrderType.BID,
              new BigDecimal(bid.getSize()),
              instrument,
              new BigDecimal(bid.getPrice()),
              date,
              new BigDecimal(bid.getSize()));
      orderBookUpdates.add(o);
    }
    orderBookUpdatesSubscriptions.get(instrument).onNext(orderBookUpdates);
  }

  @Override
  public Observable<Trade> getTrades(Instrument instrument, Object... args) {
    String channelUniqueId =
        TRADE + convertToBybitSymbol(instrument);

    return streamingService
        .subscribeChannel(channelUniqueId)
        .filter(message -> message.has("data"))
        .flatMap(
            jsonNode -> {
              List<BybitTrade> bybitTradeList =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper.getTypeFactory()
                          .constructCollectionType(List.class, BybitTrade.class));
              return Observable.fromIterable(
                  BybitStreamAdapters.adaptTrades(bybitTradeList, instrument).getTrades());
            });
  }
}
