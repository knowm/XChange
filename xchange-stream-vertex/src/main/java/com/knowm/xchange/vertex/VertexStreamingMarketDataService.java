package com.knowm.xchange.vertex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.knowm.xchange.vertex.dto.PriceAndQuantity;
import com.knowm.xchange.vertex.dto.VertexBestBidOfferMessage;
import com.knowm.xchange.vertex.dto.VertexMarketDataUpdateMessage;
import com.knowm.xchange.vertex.dto.VertexModelUtils;
import com.knowm.xchange.vertex.dto.VertexOrderBookStream;
import com.knowm.xchange.vertex.dto.VertexTradeData;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VertexStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger logger = LoggerFactory.getLogger(VertexStreamingMarketDataService.class);
  public static final int DEFAULT_DEPTH = 20;
  public static final TypeReference<List<PriceAndQuantity>> PRICE_LIST_TYPE_REF = new TypeReference<>() {
  };

  private final VertexStreamingService subscriptionStream;

  private final Map<Instrument, StreamHolder> orderBooksStreams = new ConcurrentHashMap<>();
  private final Map<Instrument, Observable<Ticker>> tickerStreams = new ConcurrentHashMap<>();

  private final Map<Instrument, Observable<Trade>> tradeSubscriptions = new ConcurrentHashMap<>();

  private final ObjectMapper mapper;

  private final VertexProductInfo productInfo;
  private final VertexStreamingExchange exchange;
  private final JavaType PRICE_LIST_TYPE;


  public VertexStreamingMarketDataService(VertexStreamingService subscriptionStream, VertexProductInfo productInfo, VertexStreamingExchange vertexStreamingExchange) {
    this.subscriptionStream = subscriptionStream;
    this.productInfo = productInfo;
    this.exchange = vertexStreamingExchange;
    mapper = StreamingObjectMapperHelper.getObjectMapper();
    mapper.enable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    mapper.registerModule(new JavaTimeModule());
    PRICE_LIST_TYPE = mapper.constructType(PRICE_LIST_TYPE_REF.getType());
  }

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    Observable<Ticker> cachedStream = tickerStreams.computeIfAbsent(
        instrument,
        newInstrument -> {

          logger.info("Subscribing to ticker for " + newInstrument);

          long productId = productInfo.lookupProductId(newInstrument);
          String channelName = "best_bid_offer." + productId;

          return subscriptionStream.subscribeChannel(channelName)
              .map(jsonNode -> {
                VertexBestBidOfferMessage vertexBestBidOfferMessage = mapper.treeToValue(jsonNode, VertexBestBidOfferMessage.class);
                BigDecimal bid = VertexModelUtils.convertToDecimal(vertexBestBidOfferMessage.getBid_price());
                BigDecimal ask = VertexModelUtils.convertToDecimal(vertexBestBidOfferMessage.getAsk_price());
                BigDecimal bidQty = VertexModelUtils.convertToDecimal(vertexBestBidOfferMessage.getBid_qty());
                BigDecimal askQty = VertexModelUtils.convertToDecimal(vertexBestBidOfferMessage.getAsk_qty());

                exchange.setMarketPrice(productId, new TopOfBookPrice(bid, ask));

                return new Ticker.Builder()
                    .instrument(newInstrument)
                    .bid(bid)
                    .ask(ask)
                    .bidSize(bidQty)
                    .askSize(askQty)
                    .timestamp(new Date(vertexBestBidOfferMessage.getTimestamp().toEpochMilli()))
                    .creationTimestamp(new Date(Instant.now().toEpochMilli()))
                    .build();


              }).doOnDispose(() -> {
                logger.info("Unsubscribing from ticker for " + newInstrument);
                tickerStreams.remove(newInstrument);
              });
        });

    return cachedStream.share();
  }

  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    final int maxDepth;
    if (args.length > 0 && args[0] instanceof Integer) {
      maxDepth = (int) args[0];
    } else {
      maxDepth = DEFAULT_DEPTH;
    }

    long productId = productInfo.lookupProductId(instrument);

    StreamHolder cachedInstrumentStream = orderBooksStreams.computeIfAbsent(
        instrument,
        newInstrument -> {
          logger.info("Subscribing to orderBook for " + newInstrument);

          String channelName = "book_depth." + productInfo.lookupProductId(newInstrument);

          AtomicReference<Instant> snapshotTimeHolder = new AtomicReference<>();

          Subject<VertexMarketDataUpdateMessage> snapshots = PublishSubject.<VertexMarketDataUpdateMessage>create().toSerialized();

          Observable<VertexMarketDataUpdateMessage> clearOnDisconnect = subscriptionStream.subscribeDisconnect()
              .map(o -> {
                logger.info("Clearing order books for {} due to disconnect", newInstrument);
                return VertexMarketDataUpdateMessage.EMPTY;
              });


          AtomicReference<Instant> lastIncrementTimestamp = new AtomicReference<>(null);

          Observable<VertexMarketDataUpdateMessage> marketDataUpdates = subscriptionStream.subscribeChannel(channelName)
              .map(json -> {
                VertexMarketDataUpdateMessage marketDataUpdate = mapper.treeToValue(json, VertexMarketDataUpdateMessage.class);
                return Objects.requireNonNullElse(marketDataUpdate, VertexMarketDataUpdateMessage.EMPTY);
              }).filter(update -> {
                if (update.getLastMaxTime() == null) return true; // Pass through snapshots

                //Subscribe to updates but drop until snapshot reply - there is still a chance we could miss a message but not much we can do about that
                Instant snapshotTime = snapshotTimeHolder.get();
                if (snapshotTime == null || update.getMaxTime().isAfter(snapshotTime)) {
                  if (snapshotTime != null) {
                    snapshotTimeHolder.set(null);
                  }
                  return true;
                }
                return false;
              });

          Observable<VertexMarketDataUpdateMessage> updatesWithMissedMsgFilter = marketDataUpdates.filter(update -> {
            Instant lastIncrementTime = lastIncrementTimestamp.get();
            if (lastIncrementTime != null && update.getLastMaxTime() != null) {
              if (!lastIncrementTime.equals(update.getLastMaxTime())) {
                if (update.getMaxTime().equals(lastIncrementTime)) {
                  logger.trace("Skipping update for {} {} == {}. Already processed.", instrument, lastIncrementTime, update.getLastMaxTime());
                  return false;
                }
                logger.error("Unexpected gap in timestamps for {} {} != {}. Re-snapshot...", instrument, lastIncrementTime, update.getLastMaxTime());
                requestSnapshot(productId, snapshotTimeHolder, snapshots, lastIncrementTimestamp);
                return false;
              }
            }
            lastIncrementTimestamp.set(update.getMaxTime());

            return true;
          });

          Consumer<Disposable> triggerSnapshot = (d) -> requestSnapshot(productId, snapshotTimeHolder, snapshots, lastIncrementTimestamp);
          Observable<VertexMarketDataUpdateMessage> stream = Observable.merge(
                  snapshots,
                  updatesWithMissedMsgFilter,
                  clearOnDisconnect
              )
              .doOnDispose(() -> logger.info("Subscription to " + newInstrument + " stopped"))
              .share();
          return new StreamHolder(stream, triggerSnapshot);

        }
    );


    VertexOrderBookStream instrumentAndDepthStream = new VertexOrderBookStream(instrument, maxDepth);

    Disposable instrumentStream = cachedInstrumentStream.getStream()
        .subscribe(instrumentAndDepthStream);

    return instrumentAndDepthStream
        .doOnSubscribe((d) -> {
          //trigger snapshot after delay
          Observable.timer(500, TimeUnit.MILLISECONDS)
              .subscribe(o -> cachedInstrumentStream.getTriggerSnapshot().accept(d));
        })
        .doOnDispose(instrumentStream::dispose);

  }

  private void requestSnapshot(long productId, AtomicReference<Instant> snapshotTimeHolder, Subject<VertexMarketDataUpdateMessage> snapshots, AtomicReference<Instant> lastChangeId) {
    if (Instant.MAX.equals(snapshotTimeHolder.get())) return; // Already requested (or in progress
    snapshotTimeHolder.set(Instant.MAX); // Block all updates while we request a snapshot
    snapshots.onNext(VertexMarketDataUpdateMessage.EMPTY); //Clear book
    // Request snapshot for new subscriber
    logger.info("Requesting snapshot for product " + productId);
    exchange.submitQueries(new Query(snapshotQuery(DEFAULT_DEPTH, productId), (data) -> {
      VertexMarketDataUpdateMessage snapshot = buildSnapshotFromQueryResponse(productId, data);
      logger.info("Snapshot for product " + productId + " " + snapshot);
      snapshots.onNext(snapshot);
      snapshotTimeHolder.set(snapshot.getMaxTime());
      lastChangeId.set(null);
    }, (code, error) -> logger.error("Error requesting snapshot for product " + productId + " " + code + " " + error)));
  }

  private VertexMarketDataUpdateMessage buildSnapshotFromQueryResponse(long productId, JsonNode data) {
    return new VertexMarketDataUpdateMessage(parsePrices(data, "bids"), parsePrices(data, "asks"), NanoSecondsDeserializer.parse(data.get("timestamp").asText()), NanoSecondsDeserializer.parse(data.get("timestamp").asText()), null, productId);
  }

  private static String snapshotQuery(int maxDepth, long productId) {
    return "{\"type\":\"market_liquidity\",\"product_id\": " + productId + ", \"depth\": " + maxDepth + "}";
  }

  private List<PriceAndQuantity> parsePrices(JsonNode data, String field) {
    ArrayNode bidArray = data.withArray(field);

    List<PriceAndQuantity> bids;
    try {
      bids = mapper.treeToValue(bidArray, PRICE_LIST_TYPE);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return bids;
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair instrument, Object... args) {
    //noinspection UnnecessaryLocalVariable
    Instrument inst = instrument;
    return getTrades(inst, args);
  }

  @Override
  public Observable<Trade> getTrades(Instrument instrument, Object... args) {

    return tradeSubscriptions.computeIfAbsent(
        instrument,
        newInstrument -> {

          String channelName = "trade." + productInfo.lookupProductId(instrument);

          logger.info("Subscribing to trade channel: " + channelName);

          return subscriptionStream.subscribeChannel(channelName)
              .map(json -> mapper.treeToValue(json, VertexTradeData.class).toTrade(instrument));


        }).share();
  }


  private static class StreamHolder {
    private final Observable<VertexMarketDataUpdateMessage> stream;
    private final Consumer<Disposable> triggerSnapshot;

    public StreamHolder(Observable<VertexMarketDataUpdateMessage> stream, Consumer<Disposable> triggerSnapshot) {
      this.stream = stream;
      this.triggerSnapshot = triggerSnapshot;
    }

    public Observable<VertexMarketDataUpdateMessage> getStream() {
      return stream;
    }

    public Consumer<Disposable> getTriggerSnapshot() {
      return triggerSnapshot;
    }
  }


  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return this.getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    return this.getOrderBook((Instrument) currencyPair, args);
  }

}
