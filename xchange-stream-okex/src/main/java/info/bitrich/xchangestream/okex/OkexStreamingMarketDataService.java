package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.okex.dto.OkexSubscribeMessage;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
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

  public OkexStreamingMarketDataService(OkexStreamingService service) {
    this.service = service;
  }

  //    public void unsubscribe(Instrument instrument, String subscriptionType) {
  //        String instId = OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument);
  //        OkexSubscribeMessage.SubscriptionTopic topic =
  //                new OkexSubscribeMessage.SubscriptionTopic(subscriptionType, null, null,
  // instId);
  //        OkexSubscribeMessage osm = new OkexSubscribeMessage();
  //        osm.setOp("unsubscribe");
  //        osm.getArgs().add(topic);
  //        //        instId += "-"+subscriptionType;
  //        service.unsubscribeChannel(instId, osm);
  //        switch (subscriptionType) {
  //            case "books":
  //                orderBookMap.remove(instId);
  //                break;
  //            default:
  //                throw new RuntimeException("Subscription type not supported to unsubscribe from
  // stream");
  //        }
  //    }

  private final Map<String, OrderBook> orderBookMap = new HashMap<>();

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    String channelName = "tickers";
    String instId = OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument);
    String subscriptionName = instId + "-" + channelName;
    OkexSubscribeMessage.SubscriptionTopic topic =
        new OkexSubscribeMessage.SubscriptionTopic(channelName, null, null, instId);
    OkexSubscribeMessage osm = new OkexSubscribeMessage();
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
    String instId = OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument);
    String subscriptionName = instId + "-" + channelName;
    OkexSubscribeMessage.SubscriptionTopic topic =
        new OkexSubscribeMessage.SubscriptionTopic(channelName, null, null, instId);
    OkexSubscribeMessage osm = new OkexSubscribeMessage();
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
    String instId = OkexAdapters.adaptInstrumentToOkexInstrumentId(instrument);
    String subscriptionName = "";
    if (args.length >= 1) {
      subscriptionName = instId + "-" + args[0].toString();
      LOG.debug("channelName unique id {}", subscriptionName);
    } else {
      subscriptionName = instId + "-books";
      LOG.debug("channelName unique id {}", subscriptionName);
    }

    OkexSubscribeMessage.SubscriptionTopic topic =
        new OkexSubscribeMessage.SubscriptionTopic(channelName, null, null, instId);
    OkexSubscribeMessage osm = new OkexSubscribeMessage();
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
                    String.format("Unexpected books action=%s, message=%s", action, jsonNode));
                return Observable.fromIterable(new LinkedList<>());
              }
            });
  }
}
