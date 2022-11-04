package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.okcoin.dto.okx.OkxSubscribeMessage;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.*;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.OkexAdapters;
import org.knowm.xchange.okex.v5.dto.marketdata.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkxStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger LOG = LoggerFactory.getLogger(OkxStreamingMarketDataService.class);

  private final OkxStreamingService service;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public OkxStreamingMarketDataService(OkxStreamingService service) {
    this.service = service;
  }

  private final Map<String, OrderBook> orderBookMap = new HashMap<>();

  public void unsubscribe(Instrument instrument, String subscriptionType) {
    String instId = OkexAdapters.adaptInstrumentId(instrument);
    OkxSubscribeMessage.SubscriptionTopic topic =
        new OkxSubscribeMessage.SubscriptionTopic(subscriptionType, null, null, instId);
    OkxSubscribeMessage osm = new OkxSubscribeMessage();
    osm.setOp("unsubscribe");
    osm.getArgs().add(topic);
    //        instId += "-"+subscriptionType;
    service.unsubscribeChannel(instId, osm);
    switch (subscriptionType) {
      case "books":
        orderBookMap.remove(instId);
        break;
      default:
        throw new RuntimeException("Subscription type not supported to unsubscribe from stream");
    }
  }

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    String channelName = "tickers";
    String instId = OkexAdapters.adaptInstrumentId(instrument);
    String subscriptionName = instId + "-" + args[0].toString();
    OkxSubscribeMessage.SubscriptionTopic topic =
        new OkxSubscribeMessage.SubscriptionTopic(channelName, null, null, instId);
    OkxSubscribeMessage osm = new OkxSubscribeMessage();
    osm.setOp("subscribe");
    osm.getArgs().add(topic);

    return service
        .subscribeChannel(subscriptionName, osm)
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
    String instId = OkexAdapters.adaptInstrumentId(instrument);
    String subscriptionName = instId + "-" + channelName;
    OkxSubscribeMessage.SubscriptionTopic topic =
        new OkxSubscribeMessage.SubscriptionTopic(channelName, null, null, instId);
    OkxSubscribeMessage osm = new OkxSubscribeMessage();
    osm.setOp("subscribe");
    osm.getArgs().add(topic);

    return service
        .subscribeChannel(subscriptionName, osm)
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
  // SubscriptionName - BTC-USD-books | BTC-USD-SWAP-books
  // channelName - books | books5 | bbo-tbt
  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    String channelName = args.length >= 1 ? args[0].toString() : "books";
    String instId = OkexAdapters.adaptInstrumentId(instrument);
    String subscriptionName = "";
    if (args.length >= 1) {
      subscriptionName = instId + "-" + args[0].toString();
      LOG.debug("channelName unique id {}", subscriptionName);
    } else {
      subscriptionName = instId + "-books";
      LOG.debug("channelName unique id {}", subscriptionName);
    }

    OkxSubscribeMessage.SubscriptionTopic topic =
        new OkxSubscribeMessage.SubscriptionTopic(channelName, null, null, instId);
    OkxSubscribeMessage osm = new OkxSubscribeMessage();
    osm.setOp("subscribe");
    osm.getArgs().add(topic);

    return service
        .subscribeChannel(subscriptionName, osm)
        .flatMap(
            jsonNode -> {
              // "books5" channel pushes 5 depth levels every time.
              String action =
                  channelName.equals("books5") ? "snapshot" : jsonNode.get("action").asText();
              if ("snapshot".equalsIgnoreCase(action)) {
                List<OkexOrderbook> okexOrderbooks =
                    mapper.treeToValue(
                        jsonNode.get("data"),
                        mapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, OkexOrderbook.class));
                OrderBook orderBook = OkexAdapters.adaptOrderBook(okexOrderbooks, instrument);
                orderBookMap.put(instId, orderBook);
                return Observable.just(orderBook);
              } else if ("update".equalsIgnoreCase(action)) {
                OrderBook orderBook = orderBookMap.getOrDefault(instId, null);
                if (orderBook == null) {
                  LOG.error(String.format("Failed to get orderBook, instId=%s.", instId));
                  return Observable.fromIterable(new LinkedList<>());
                }
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

                orderBook.updateDate(
                    Date.from(
                        java.time.Instant.ofEpochMilli(
                            Long.parseLong(jsonNode.get("data").get(0).get("ts").asText()))));
                return Observable.just(orderBook);

              } else {
                LOG.error(
                    String.format(
                        "Unexpected books action=%s, message=%s", action, jsonNode.toString()));
                return Observable.fromIterable(new LinkedList<>());
              }
            });
  }
  //    public Observable<OrderBook> getOrderBook(Instrument instrument, BigDecimal multiplier,
  // String args) {
  //        String channelName = args.length() >= 1 ? args : "books";
  //        String instId = OkexAdapters.adaptInstrumentId(instrument);
  //        String subscriptionName = "";
  //        if (args.length() >= 1) {
  //            subscriptionName = instId  + "-" + args;
  //            LOG.debug("channelName unique id {}", subscriptionName);
  //        } else {
  //            subscriptionName = instId + "-books";
  //            LOG.debug("channelName unique id {}", subscriptionName);
  //        }
  //
  //        OkxSubscribeMessage.SubscriptionTopic topic = new
  // OkxSubscribeMessage.SubscriptionTopic(channelName, null, null, instId);
  //        OkxSubscribeMessage osm = new OkxSubscribeMessage();
  //        osm.setOp("subscribe");
  //        osm.getArgs().add(topic);
  //
  //        return service
  //                .subscribeChannel(subscriptionName, osm)
  //                .flatMap(jsonNode -> {
  //                    // "books5" channel pushes 5 depth levels every time.
  //                    String action = channelName.equals("books5") ? "snapshot" :
  // jsonNode.get("action").asText();
  //                    if ("snapshot".equalsIgnoreCase(action)) {
  //                        List<OkexOrderbook> okexOrderbooks =
  // mapper.treeToValue(jsonNode.get("data"),
  // mapper.getTypeFactory().constructCollectionType(List.class, OkexOrderbook.class));
  //                        OrderBook orderBook = OkexAdapters.adaptOrderBook(okexOrderbooks,
  // instrument, multiplier);
  //                        orderBookMap.put(instId, orderBook);
  //                        return Observable.just(orderBook);
  //                    } else if ("update".equalsIgnoreCase(action)) {
  //                        OrderBook orderBook = orderBookMap.getOrDefault(instId, null);
  //                        if (orderBook == null) {
  //                            LOG.error(String.format("Failed to get orderBook, instId=%s.",
  // instId));
  //                            return Observable.fromIterable(new LinkedList<>());
  //                        }
  //
  //                        List<OkexPublicOrder> asks =
  // mapper.treeToValue(jsonNode.get("data").get(0).get("asks"),
  // mapper.getTypeFactory().constructCollectionType(List.class, OkexPublicOrder.class));
  //                        asks.stream().forEach( okexPublicOrder ->
  // {orderBook.update(OkexAdapters.adaptLimitOrder(okexPublicOrder, instrument,
  // Order.OrderType.ASK, multiplier));});
  //
  //                        List<OkexPublicOrder> bids =
  // mapper.treeToValue(jsonNode.get("data").get(0).get("bids"),
  // mapper.getTypeFactory().constructCollectionType(List.class, OkexPublicOrder.class));
  //                        bids.stream().forEach( okexPublicOrder ->
  // {orderBook.update(OkexAdapters.adaptLimitOrder(okexPublicOrder, instrument,
  // Order.OrderType.BID, multiplier));});
  //
  // orderBook.updateDate(Date.from(java.time.Instant.ofEpochMilli(Long.parseLong(jsonNode.get("data").get(0).get("ts").asText()))));
  //                        return Observable.just(orderBook);
  //
  //                    } else {
  //                        LOG.error(String.format("Unexpected books action=%s, message=%s",
  // action, jsonNode.toString()));
  //                        return Observable.fromIterable(new LinkedList<>());
  //                    }
  //                });
  //    }
  //    public Observable<OrderBook> getOrderBookFutures(Instrument instrument, BigDecimal
  // multiplier, String args) {
  //        String channelName = args.length() >= 1 ? args : "books";
  //        String instId = OkexAdapters.adaptInstrumentId(instrument);
  //        String subscriptionName = "";
  //        if (args.length() >= 1) {
  //            subscriptionName = instId  + "-" + args;
  //            LOG.debug("channelName unique id {}", subscriptionName);
  //        } else {
  //            subscriptionName = instId + "-books";
  //            LOG.debug("channelName unique id {}", subscriptionName);
  //        }
  //
  //        OkxSubscribeMessage.SubscriptionTopic topic = new
  // OkxSubscribeMessage.SubscriptionTopic(channelName, null, null, instId);
  //        OkxSubscribeMessage osm = new OkxSubscribeMessage();
  //        osm.setOp("subscribe");
  //        osm.getArgs().add(topic);
  //
  //        return service
  //                .subscribeChannel(subscriptionName, osm)
  //                .flatMap(jsonNode -> {
  //                    // "books5" channel pushes 5 depth levels every time.
  //                    String action = channelName.equals("books5") ? "snapshot" :
  // jsonNode.get("action").asText();
  //                    if ("snapshot".equalsIgnoreCase(action)) {
  //                        List<OkexOrderbook> okexOrderbooks =
  // mapper.treeToValue(jsonNode.get("data"),
  // mapper.getTypeFactory().constructCollectionType(List.class, OkexOrderbook.class));
  //                        OrderBook orderBook = OkexAdapters.adaptOrderBook(okexOrderbooks,
  // instrument, multiplier);
  //                        orderBookMap.put(instId, orderBook);
  //                        return Observable.just(orderBook);
  //                    } else if ("update".equalsIgnoreCase(action)) {
  //                        OrderBook orderBook = orderBookMap.getOrDefault(instId, null);
  //                        if (orderBook == null) {
  //                            LOG.error(String.format("Failed to get orderBook, instId=%s.",
  // instId));
  //                            return Observable.fromIterable(new LinkedList<>());
  //                        }
  //                        List<OkexPublicOrder> asks =
  // mapper.treeToValue(jsonNode.get("data").get(0).get("asks"),
  // mapper.getTypeFactory().constructCollectionType(List.class, OkexPublicOrder.class));
  //                        asks.stream().forEach( okexPublicOrder ->
  // {orderBook.update(OkexAdapters.adaptLimitOrder(okexPublicOrder, instrument,
  // Order.OrderType.ASK, multiplier));});
  //
  //                        List<OkexPublicOrder> bids =
  // mapper.treeToValue(jsonNode.get("data").get(0).get("bids"),
  // mapper.getTypeFactory().constructCollectionType(List.class, OkexPublicOrder.class));
  //                        bids.stream().forEach( okexPublicOrder ->
  // {orderBook.update(OkexAdapters.adaptLimitOrder(okexPublicOrder, instrument,
  // Order.OrderType.BID, multiplier));});
  //
  //
  // orderBook.updateDate(Date.from(java.time.Instant.ofEpochMilli(Long.parseLong(jsonNode.get("data").get(0).get("ts").asText()))));
  //
  //                        return Observable.just(orderBook);
  //
  //                    } else {
  //                        LOG.error(String.format("Unexpected books action=%s, message=%s",
  // action, jsonNode.toString()));
  //                        return Observable.fromIterable(new LinkedList<>());
  //                    }
  //                });
  //    }

}
