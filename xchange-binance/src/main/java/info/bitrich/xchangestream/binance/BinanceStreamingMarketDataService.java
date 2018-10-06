package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.BinanceRawTrade;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.dto.DepthBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.TickerBinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.dto.TradeBinanceWebsocketTransaction;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.DEPTH_UPDATE;
import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.TICKER_24_HR;
import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.TRADE;

public class BinanceStreamingMarketDataService implements StreamingMarketDataService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceStreamingMarketDataService.class);

    private final BinanceStreamingService service;
    private final Map<CurrencyPair, OrderbookSubscription> orderbooks = new HashMap<>();

    private final Map<CurrencyPair, Observable<BinanceTicker24h>> tickerSubscriptions = new HashMap<>();
    private final Map<CurrencyPair, Observable<OrderBook>> orderbookSubscriptions = new HashMap<>();
    private final Map<CurrencyPair, Observable<BinanceRawTrade>> tradeSubscriptions = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final BinanceMarketDataService marketDataService;

    public BinanceStreamingMarketDataService(BinanceStreamingService service, BinanceMarketDataService marketDataService) {
        this.service = service;
        this.marketDataService = marketDataService;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        if (!service.getProductSubscription().getOrderBook().contains(currencyPair)) {
            throw new UnsupportedOperationException("Binance exchange only supports up front subscriptions - subscribe at connect time");
        }
        return orderbookSubscriptions.get(currencyPair);
    }

    public Observable<BinanceTicker24h> getRawTicker(CurrencyPair currencyPair, Object... args) {
        if (!service.getProductSubscription().getTicker().contains(currencyPair)) {
            throw new UnsupportedOperationException("Binance exchange only supports up front subscriptions - subscribe at connect time");
        }
        return tickerSubscriptions.get(currencyPair);
    }

    public Observable<BinanceRawTrade> getRawTrades(CurrencyPair currencyPair, Object... args) {
        if (!service.getProductSubscription().getTrades().contains(currencyPair)) {
            throw new UnsupportedOperationException("Binance exchange only supports up front subscriptions - subscribe at connect time");
        }
        return tradeSubscriptions.get(currencyPair);
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        return getRawTicker(currencyPair)
                .map(BinanceTicker24h::toTicker);
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        return getRawTrades(currencyPair)
                .map(rawTrade -> new Trade(
                        BinanceAdapters.convertType(rawTrade.isBuyerMarketMaker()),
                        rawTrade.getQuantity(),
                        currencyPair,
                        rawTrade.getPrice(),
                        new Date(rawTrade.getTimestamp()),
                        String.valueOf(rawTrade.getTradeId())
                ));
    }

    private static String channelFromCurrency(CurrencyPair currencyPair, String subscriptionType) {
        String currency = String.join("", currencyPair.toString().split("/")).toLowerCase();
        return currency + "@" + subscriptionType;
    }

    /**
     * Registers subsriptions with the streaming service for the given products.
     *
     * As we receive messages as soon as the connection is open, we need to register subscribers to handle these before the
     * first messages arrive.
     */
    public void openSubscriptions(ProductSubscription productSubscription) {
        productSubscription.getTicker()
                .forEach(currencyPair ->
                        tickerSubscriptions.put(currencyPair, triggerObservableBody(rawTickerStream(currencyPair).share())));
        productSubscription.getOrderBook()
                .forEach(currencyPair ->
                        orderbookSubscriptions.put(currencyPair, triggerObservableBody(orderBookStream(currencyPair).share())));
        productSubscription.getTrades()
                .forEach(currencyPair ->
                        tradeSubscriptions.put(currencyPair, triggerObservableBody(rawTradeStream(currencyPair).share())));
    }

    private Observable<BinanceTicker24h> rawTickerStream(CurrencyPair currencyPair) {
        return service.subscribeChannel(channelFromCurrency(currencyPair, "ticker"))
                .map((JsonNode s) -> tickerTransaction(s.toString()))
                .filter(transaction ->
                        transaction.getData().getCurrencyPair().equals(currencyPair) &&
                            transaction.getData().getEventType() == TICKER_24_HR)
                .map(transaction -> transaction.getData().getTicker());
    }

    private static final class OrderbookSubscription {
        long snapshotlastUpdateId = 0L;
        AtomicLong lastUpdateId = new AtomicLong(0L);
        OrderBook orderBook;
        ConnectableObservable<BinanceWebsocketTransaction<DepthBinanceWebSocketTransaction>> stream;
        Disposable disposable;
    }

    private OrderbookSubscription initialOrderBook(CurrencyPair currencyPair) {
        OrderbookSubscription subscription = new OrderbookSubscription();

        // 1. Open a stream to wss://stream.binance.com:9443/ws/bnbbtc@depth
        subscription.stream = service.subscribeChannel(channelFromCurrency(currencyPair, "depth"))
            .map((JsonNode s) -> depthTransaction(s.toString()))
            .filter(transaction ->
                    transaction.getData().getCurrencyPair().equals(currencyPair) &&
                            transaction.getData().getEventType() == DEPTH_UPDATE)

        // 2.Buffer the events you receive from the stream
            .replay();
        subscription.disposable = subscription.stream.connect();

        // 3. Get a depth snapshot from https://www.binance.com/api/v1/depth?symbol=BNBBTC&limit=1000
        setSnapshot(currencyPair, subscription);
        return subscription;
    }

    private void setSnapshot(CurrencyPair currencyPair, OrderbookSubscription subscription) {
        try {
            LOG.info("Fetching initial orderbook snapshot for {} ", currencyPair);
            BinanceOrderbook book = marketDataService.getBinanceOrderbook(currencyPair, 1000);
            subscription.snapshotlastUpdateId = book.lastUpdateId;
            subscription.lastUpdateId.set(book.lastUpdateId);
            subscription.orderBook = BinanceMarketDataService.convertOrderBook(book, currencyPair);
        } catch (IOException e) {
            LOG.error("Failed to fetch initial order book for " + currencyPair);
            subscription.orderBook = new OrderBook(null, new ArrayList<>(), new ArrayList<>());
        }
    }

    private Observable<OrderBook> orderBookStream(CurrencyPair currencyPair) {
        OrderbookSubscription subscription = orderbooks.computeIfAbsent(currencyPair, pair -> initialOrderBook(pair));

        return subscription.stream
                .doOnComplete(() -> {
                    subscription.disposable.dispose();
                    orderbooks.remove(currencyPair);
                })
                .map(BinanceWebsocketTransaction::getData)

                // 4. Drop any event where u is <= lastUpdateId in the snapshot
                .filter(depth -> depth.getLastUpdateId() > subscription.snapshotlastUpdateId)

                // 5. The first processed should have U <= lastUpdateId+1 AND u >= lastUpdateId+1
                .filter(depth -> {
                    long lastUpdateId = subscription.lastUpdateId.get();
                    if (lastUpdateId == 0L) {
                        return depth.getFirstUpdateId() <= lastUpdateId + 1 &&
                               depth.getLastUpdateId() >= lastUpdateId + 1;
                    } else {
                        return true;
                    }
                })

                // 6. While listening to the stream, each new event's U should be equal to the previous event's u+1
                .filter(depth -> {
                    long lastUpdateId = subscription.lastUpdateId.get();
                    boolean result;
                    if (lastUpdateId == 0L) {
                        result = true;
                    } else {
                        result = depth.getFirstUpdateId() == lastUpdateId + 1;
                    }
                    if (result) {
                        subscription.lastUpdateId.set(depth.getLastUpdateId());
                    } else {
                        // If not, we re-sync
                        LOG.info("Orderbook snapshot for {} out of date (last={}, U={}, u={})", currencyPair, lastUpdateId, depth.getFirstUpdateId(), depth.getLastUpdateId());
                        setSnapshot(currencyPair, subscription);
                    }
                    return result;
                })

                // 7. The data in each event is the absolute quantity for a price level
                // 8. If the quantity is 0, remove the price level
                // 9. Receiving an event that removes a price level that is not in your local order book can happen and is normal.
                .map(depth -> {
                    BinanceOrderbook ob = depth.getOrderBook();
                    ob.bids.forEach((key, value) -> subscription.orderBook.update(new OrderBookUpdate(
                            OrderType.BID,
                            null,
                            currencyPair,
                            key,
                            depth.getEventTime(),
                            value)));
                    ob.asks.forEach((key, value) -> subscription.orderBook.update(new OrderBookUpdate(
                            OrderType.ASK,
                            null,
                            currencyPair,
                            key,
                            depth.getEventTime(),
                            value)));
                    return subscription.orderBook;
                });
    }

    private Observable<BinanceRawTrade> rawTradeStream(CurrencyPair currencyPair) {
        return service.subscribeChannel(channelFromCurrency(currencyPair, "trade"))
                .map((JsonNode s) -> tradeTransaction(s.toString()))
                .filter(transaction ->
                        transaction.getData().getCurrencyPair().equals(currencyPair) &&
                                transaction.getData().getEventType() == TRADE
                )
                .map(transaction -> transaction.getData().getRawTrade());
    }

    /** Force observable to execute its body, this way we get `BinanceStreamingService` to register the observables emitter
     * ready for our message arrivals. */
    private <T> Observable<T> triggerObservableBody(Observable<T> observable) {
        Consumer<T> NOOP = whatever -> {};
        observable.subscribe(NOOP);
        return observable;
    }

    private BinanceWebsocketTransaction<TickerBinanceWebsocketTransaction> tickerTransaction(String s) {
        try {
            return mapper.readValue(s, new TypeReference<BinanceWebsocketTransaction<TickerBinanceWebsocketTransaction>>() {});
        } catch (IOException e) {
            throw new ExchangeException("Unable to parse ticker transaction", e);
        }
    }

    private BinanceWebsocketTransaction<DepthBinanceWebSocketTransaction> depthTransaction(String s) {
        try {
            return mapper.readValue(s, new TypeReference<BinanceWebsocketTransaction<DepthBinanceWebSocketTransaction>>() {});
        } catch (IOException e) {
          throw new ExchangeException("Unable to parse order book transaction", e);
        }
    }

    private BinanceWebsocketTransaction<TradeBinanceWebsocketTransaction> tradeTransaction(String s) {
        try {
            return mapper.readValue(s, new TypeReference<BinanceWebsocketTransaction<TradeBinanceWebsocketTransaction>>() {});
        } catch (IOException e) {
            throw new ExchangeException("Unable to parse trade transaction", e);
        }
    }

}
