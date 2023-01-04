package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.CRC32;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.dto.marketdata.OkexOrderbook;
import org.knowm.xchange.okex.dto.marketdata.OkexPublicOrder;
import org.knowm.xchange.okex.dto.marketdata.OkexTicker;
import org.knowm.xchange.okex.dto.marketdata.OkexTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkexStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger LOG = LoggerFactory.getLogger(OkexStreamingMarketDataService.class);

  private final OkexStreamingService service;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final Map<Instrument, PublishSubject<OrderBookUpdate>> orderBookUpdatesSubscriptions;

  public OkexStreamingMarketDataService(OkexStreamingService service) {
    this.service = service;
    this.orderBookUpdatesSubscriptions = new ConcurrentHashMap<>();
  }

  public void unsubscribe(Instrument instrument, String subscriptionType) {
    switch (subscriptionType) {
      case "books":
      case "bbo-tbt":
      case "books5":
      case "books50-l2-tbt":
      case "books-l2-tbt":
        orderBookMap.remove(instrument);
        break;
      default:
        throw new RuntimeException("Subscription type not supported to unsubscribe");
    }
  }

  private final Map<String, OrderBook> orderBookMap = new HashMap<>();

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    String channelName = "tickers";
    String instId = OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument);
    String subscriptionName = instId + "-" + channelName;

    return service
        .subscribeChannel(instId, channelName)
        .flatMap(
            jsonNode -> {
              List<OkexTicker> okexTickers =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper
                          .getTypeFactory()
                          .constructCollectionType(List.class, OkexTicker.class));
              return Observable.fromIterable(okexTickers).map(OkexAdapters::adaptTicker);
            });
  }

  @Override
  public Observable<Trade> getTrades(Instrument instrument, Object... args) {
    String channelName = "trades";
    String instId = OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument);
    String subscriptionName = instId + "-" + channelName;

    return service
        .subscribeChannel(instId, channelName)
        .flatMap(
            jsonNode -> {
              List<OkexTrade> okexTradeList =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper.getTypeFactory().constructCollectionType(List.class, OkexTrade.class));
              return Observable.fromIterable(
                  OkexAdapters.adaptTrades(okexTradeList, instrument).getTrades());
            });
  }

  // instrument - BTC/USDT | BTC/USD-SWAP
  // args - books | books5 | bbo-tbt
  // instId - BTC-USDT | BTC-USD-SWAP
  // channelName - books | books5 | bbo-tbt
  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    String channelName = args.length >= 1 ? args[0].toString() : "books";
    String instId = OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument);
    String subscriptionName = OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument, args);

    return service
        .subscribeChannel(instId, channelName)
        .flatMap(
            jsonNode -> {
              // "books5" channel pushes 5 depth levels every time.
              String action = jsonNode.get("action").asText();
              if ("snapshot".equalsIgnoreCase(action)) {
                List<OkexOrderbook> okexOrderbooks =
                    mapper.treeToValue(
                        jsonNode.get("data"),
                        mapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, OkexOrderbook.class));
                OrderBook orderBook = OkexAdapters.adaptOrderBook(okexOrderbooks, instrument);
                orderBookMap.put(instId, orderBook);
                for (OkexOrderbook ob : okexOrderbooks)
                  if (calcCrc(ob) != ob.getChecksum())
                    LOG.error(
                        "CRC32 check sum mismatch ob full JSON {} expected CRC {} calc CRC {}",
                        jsonNode,
                        ob.getChecksum(),
                        calcCrc(ob));

                return Observable.just(orderBook);
              } else if ("update".equalsIgnoreCase(action)) {
                OrderBook orderBook = orderBookMap.getOrDefault(instId, null);
                if (orderBook == null) {
                  LOG.error(String.format("Failed to get orderBook, instId=%s.", instId));
                  return Observable.fromIterable(new LinkedList<>());
                }
                String ts = jsonNode.get("data").get(0).get("ts").asText();

                List<OkexPublicOrder> asks =
                    mapper.treeToValue(
                        jsonNode.get("data").get(0).get("asks"),
                        mapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, OkexPublicOrder.class));

                asks.stream()
                    .forEach(
                        okexPublicOrder -> {
                          orderBook.update(
                              OkexAdapters.adaptLimitOrder(
                                  okexPublicOrder, instrument, Order.OrderType.ASK));
                        });

                List<OkexPublicOrder> bids =
                    mapper.treeToValue(
                        jsonNode.get("data").get(0).get("bids"),
                        mapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, OkexPublicOrder.class));
                bids.stream()
                    .forEach(
                        okexPublicOrder -> {
                          orderBook.update(
                              OkexAdapters.adaptLimitOrder(
                                  okexPublicOrder, instrument, Order.OrderType.BID));
                        });
                if (orderBookUpdatesSubscriptions.get(instrument) != null) {
                  orderBookUpdatesSubscriptions(
                      instrument, asks, bids, new Timestamp(Long.parseLong(ts)));
                }

                // check only 1% of updates, to lower CPU usage. Because wee check Integrity of full
                // Book,sooner or later we get error if we get wrong data in updates
                if (ThreadLocalRandom.current().nextInt(100) == 1) {
                  long checkSum =
                      Long.parseLong(jsonNode.get("data").get(0).get("checksum").asText());

                  if (calcCrc(orderBook) != checkSum) {
                    LOG.error(
                        "asks sorted {} bids sorted {} ",
                        isOrderBookAsksSorted(orderBook.getAsks()),
                        isOrderBookBidsSorted(orderBook.getBids()));
                    LOG.error(
                        "CRC32 check sum mismatch ob update ob bids {} asks {} full ob {} expected CRC {} calc CRC {}",
                        asks,
                        bids,
                        orderBook,
                        checkSum,
                        calcCrc(orderBook));
                  }
                }
                orderBook.updateDate(
                    new Timestamp(Long.parseLong(jsonNode.get("data").get(0).get("ts").asText())));
                return Observable.just(orderBook);

              } else {
                LOG.error(
                    String.format("Unexpected books action=%s, message=%s", action, jsonNode));
                return Observable.fromIterable(new LinkedList<>());
              }
            });
  }

  private boolean isOrderBookAsksSorted(List<LimitOrder> asks) {
    if (asks.size() < 2) return true;
    Iterator<LimitOrder> iter = asks.iterator();
    BigDecimal current, previous = iter.next().getLimitPrice();
    while (iter.hasNext()) {
      current = iter.next().getLimitPrice();
      if (previous.compareTo(current) > 0) {
        return false;
      }
      previous = current;
    }
    return true;
  }

  private boolean isOrderBookBidsSorted(List<LimitOrder> bids) {
    if (bids.size() < 2) return true;
    Iterator<LimitOrder> iter = bids.iterator();
    BigDecimal current, previous = iter.next().getLimitPrice();
    while (iter.hasNext()) {
      current = iter.next().getLimitPrice();
      if (previous.compareTo(current) < 0) {
        return false;
      }
      previous = current;
    }
    return true;
  }

  public Observable<OrderBookUpdate> getOrderBookUpdates(Instrument instrument) {
    return orderBookUpdatesSubscriptions.computeIfAbsent(instrument, v -> PublishSubject.create());
  }

  private void orderBookUpdatesSubscriptions(
      Instrument instrument, List<OkexPublicOrder> asks, List<OkexPublicOrder> bids, Date date) {
    List<OrderBookUpdate> orderBookUpdate = new ArrayList<>();
    for (OkexPublicOrder ask : asks) {
      OrderBookUpdate o =
          new OrderBookUpdate(
              Order.OrderType.ASK,
              ask.getVolume(),
              instrument,
              ask.getPrice(),
              date,
              ask.getVolume());
      orderBookUpdate.add(o);
    }
    for (OkexPublicOrder bid : bids) {
      OrderBookUpdate o =
          new OrderBookUpdate(
              Order.OrderType.BID,
              bid.getVolume(),
              instrument,
              bid.getPrice(),
              date,
              bid.getVolume());
      orderBookUpdate.add(o);
    }
    for (OrderBookUpdate o : orderBookUpdate)
      orderBookUpdatesSubscriptions.get(instrument).onNext(o);
    LOG.debug("instrument {} orderBookUpdate size {}", instrument, orderBookUpdate.size());
  }

  private long calcCrc(OkexOrderbook ob) {
    StringBuilder data = new StringBuilder();
    OkexPublicOrder o;
    int bidSize = ob.getBids().size();
    int askSize = ob.getAsks().size();
    for (int i = 0; i < 25; i++) {
      if (i < bidSize) {
        o = ob.getBids().get(i);
        data.append(o.getPrice().toPlainString())
            .append(":")
            .append(o.getVolume().toPlainString())
            .append(":");
      }
      if (i < askSize) {
        o = ob.getAsks().get(i);
        data.append(o.getPrice().toPlainString())
            .append(":")
            .append(o.getVolume().toPlainString())
            .append(":");
      }
    }
    return getCrc(data);
  }

  private long calcCrc(OrderBook ob) {
    StringBuilder data = new StringBuilder();
    LimitOrder o;
    int bidSize = ob.getBids().size();
    int askSize = ob.getAsks().size();
    for (int i = 0; i < 25; i++) {
      if (i < bidSize) {
        o = ob.getBids().get(i);
        data.append(o.getLimitPrice().toPlainString())
            .append(":")
            .append(o.getOriginalAmount().toPlainString())
            .append(":");
      }
      if (i < askSize) {
        o = ob.getAsks().get(i);
        data.append(o.getLimitPrice().toPlainString())
            .append(":")
            .append(o.getOriginalAmount().toPlainString())
            .append(":");
      }
    }
    return getCrc(data);
  }

  private long getCrc(StringBuilder data) {
    CRC32 crc32 = new CRC32();
    byte[] toBytes = data.toString().getBytes(StandardCharsets.UTF_8);
    crc32.update(toBytes, 0, toBytes.length - 1); // strip last :
    if (crc32.getValue() > Integer.MAX_VALUE) return crc32.getValue() - 4294967296L;
    else return crc32.getValue();
  }
}
