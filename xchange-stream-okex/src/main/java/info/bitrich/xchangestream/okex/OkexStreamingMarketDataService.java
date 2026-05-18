package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.dto.marketdata.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static info.bitrich.xchangestream.okex.OkexStreamingService.*;

public class OkexStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger LOG = LoggerFactory.getLogger(OkexStreamingMarketDataService.class);

  private final OkexStreamingService service;
  private final OkexBusinessStreamingService businessStreamingService;
  private final ExchangeMetaData exchangeMetaData;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final Map<Instrument, PublishSubject<List<OrderBookUpdate>>>
      orderBookUpdatesSubscriptions;

  public OkexStreamingMarketDataService(
      OkexStreamingService service,
      OkexBusinessStreamingService businessStreamingService,
      ExchangeMetaData exchangeMetaData) {
    this.service = service;
    this.businessStreamingService = businessStreamingService;
    this.exchangeMetaData = exchangeMetaData;
    this.orderBookUpdatesSubscriptions = new ConcurrentHashMap<>();
  }

  private final Map<String, OrderBook> orderBookMap = new HashMap<>();

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    String channelUniqueId = TICKERS + OkexAdapters.adaptInstrument(instrument);

    return service
        .subscribeChannel(channelUniqueId)
        .filter(message -> message.has("data"))
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
    String channelUniqueId = TRADES + OkexAdapters.adaptInstrument(instrument);

    return service
        .subscribeChannel(channelUniqueId)
        .filter(message -> message.has("data"))
        .flatMap(
            jsonNode -> {
              List<OkexTrade> okexTradeList =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper.getTypeFactory().constructCollectionType(List.class, OkexTrade.class));
              return Observable.fromIterable(
                  OkexAdapters.adaptTrades(okexTradeList, instrument, exchangeMetaData)
                      .getTrades());
            });
  }

  @Override
  public Observable<FundingRate> getFundingRate(Instrument instrument, Object... args) {
    String channelUniqueId = FUNDING_RATE + OkexAdapters.adaptInstrument(instrument);

    return service
        .subscribeChannel(channelUniqueId)
        .filter(message -> message.has("data"))
        .map(
            jsonNode -> {
              List<OkexFundingRate> okexFundingRates =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper
                          .getTypeFactory()
                          .constructCollectionType(List.class, OkexFundingRate.class));
              return OkexAdapters.adaptFundingRate(okexFundingRates);
            });
  }

  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    String instId = OkexAdapters.adaptInstrument(instrument);
    String channelName = args.length >= 1 ? args[0].toString() : "books";
    String channelUniqueId = channelName + instId;

    return service
        .subscribeChannel(channelUniqueId)
        .flatMap(
            jsonNode -> {
              List<OkexOrderbook> okexOrderbooks =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper
                          .getTypeFactory()
                          .constructCollectionType(List.class, OkexOrderbook.class));
              if (jsonNode.get("action") != null) {
                // "books5" channel pushes 5 depth levels every time.
                String action =
                    channelName.equals(ORDERBOOK5) ? "snapshot" : jsonNode.get("action").asText();
                if ("snapshot".equalsIgnoreCase(action)) {
                  OrderBook orderBook =
                      OkexAdapters.adaptOrderBook(okexOrderbooks, instrument, exchangeMetaData);
                  orderBookMap.put(instId, orderBook);
                  return Observable.just(orderBook);
                } else if ("update".equalsIgnoreCase(action)) {
                  OrderBook orderBook = orderBookMap.getOrDefault(instId, null);
                  if (orderBook == null) {
                    LOG.error("Failed to get orderBook, instId={}.", instId);
                    return Observable.fromIterable(new LinkedList<>());
                  }
                  Date timestamp = new Timestamp(Long.parseLong(okexOrderbooks.get(0).getTs()));
                  BigDecimal contractValue =
                      exchangeMetaData.getInstruments().get(instrument).getContractValue();
                  List<OrderBookUpdate> orderBookUpdates =
                      OkexAdapters.adaptOrderBookUpdates(
                          instrument,
                          okexOrderbooks.get(0).getAsks(),
                          okexOrderbooks.get(0).getBids(),
                          contractValue,
                          timestamp);
                  orderBookUpdates.forEach(orderBook::update);
                  if (orderBookUpdatesSubscriptions.get(instrument) != null) {
                    orderBookUpdatesSubscriptions(instrument, orderBookUpdates);
                  }
                  return Observable.just(orderBook);
                } else {
                  LOG.error("Unexpected books action={}, message={}", action, jsonNode);
                  return Observable.fromIterable(new LinkedList<>());
                }
              } else {
                if (channelName.contains(ORDERBOOK_BBO_TBT)) {
                  // one level orderbook snapshot
                  OrderBook orderBook =
                      OkexAdapters.adaptOrderBook(okexOrderbooks, instrument, exchangeMetaData);
                  return Observable.just(orderBook);
                }
                return Observable.fromIterable(new LinkedList<>());
              }
            });
  }

  @Override
  public Observable<List<OrderBookUpdate>> getOrderBookUpdates(
      Instrument instrument, Object... args) {
    return orderBookUpdatesSubscriptions.computeIfAbsent(instrument, v -> PublishSubject.create());
  }

  private void orderBookUpdatesSubscriptions(
      Instrument instrument, List<OrderBookUpdate> orderBookUpdates) {
    orderBookUpdatesSubscriptions.get(instrument).onNext(orderBookUpdates);
  }

  @Override
  public Observable<CandleStickData> getCandleStick(Instrument instrument, CandleStickInterval interval) {
    String channelUniqueId = OkexAdapters.adaptCandleStickInterval(interval).name() + "-" + OkexAdapters.adaptInstrument(instrument);

    return businessStreamingService
        .subscribeChannel(channelUniqueId)
        .filter(message -> message.has("data"))
        .flatMap(
            jsonNode -> {
              List<OkexCandleStick> okexCandles =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper.getTypeFactory()
                          .constructCollectionType(List.class, OkexCandleStick.class));
              return Observable.just(OkexAdapters.adaptCandleStickData(okexCandles, instrument));
            });
  }
}
