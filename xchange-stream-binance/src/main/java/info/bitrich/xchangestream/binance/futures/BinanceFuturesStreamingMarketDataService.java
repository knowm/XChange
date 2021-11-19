package info.bitrich.xchangestream.binance.futures;

import com.google.common.util.concurrent.RateLimiter;
import info.bitrich.xchangestream.binance.BinanceStreamingMarketDataService;
import info.bitrich.xchangestream.binance.BinanceStreamingService;
import info.bitrich.xchangestream.binance.dto.DepthBinanceWebSocketTransaction;
import io.reactivex.rxjava3.core.Flowable;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class BinanceFuturesStreamingMarketDataService extends BinanceStreamingMarketDataService {
    private static final Logger LOG =
            LoggerFactory.getLogger(BinanceFuturesStreamingMarketDataService.class);

    public BinanceFuturesStreamingMarketDataService(BinanceStreamingService service, BinanceMarketDataService marketDataService, Runnable onApiCall, String orderBookUpdateFrequencyParameter) {
        super(service, marketDataService, onApiCall, orderBookUpdateFrequencyParameter);
    }

    @Override
    public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        throw new NotAvailableFromExchangeException("getOrderBook");
    }

    @Override
    public Flowable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
        if (instrument instanceof FuturesContract) {
            FuturesContract futuresContract = (FuturesContract) instrument;
            return super.getOrderBook(futuresContract.getCurrencyPair(), args);
        }
        throw new NotAvailableFromExchangeException("getOrderBook");
    }

    @Override
    public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        throw new NotAvailableFromExchangeException("getTicker");
    }

    @Override
    public Flowable<Ticker> getTicker(Instrument instrument, Object... args) {
        if (instrument instanceof FuturesContract) {
            FuturesContract futuresContract = (FuturesContract) instrument;
            return super.getTicker(futuresContract.getCurrencyPair(), args)
                    .map(t -> BinanceFuturesAdapter.replaceInstrument(t, futuresContract));
        }
        throw new NotAvailableFromExchangeException("getTicker");
    }

    @Override
    public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        throw new NotAvailableFromExchangeException("getTrades");
    }
    
    @Override
    public Flowable<Trade> getTrades(Instrument instrument, Object... args) {
        if (instrument instanceof FuturesContract) {
            FuturesContract futuresContract = (FuturesContract) instrument;
            return super.getTrades(futuresContract.getCurrencyPair(), args)
                    .map(t -> Trade.Builder.from(t).instrument(futuresContract).build());
        }
        throw new NotAvailableFromExchangeException("getTrades");
    }

    protected Flowable<OrderBook> createOrderBookFlowable(CurrencyPair currencyPair) {
        // 1. Open a stream to wss://stream.binance.com:9443/ws/bnbbtc@depth
        // 2. Buffer the events you receive from the stream.
        OrderbookSubscription subscription =
                new OrderbookSubscription(orderBookRawUpdatesSubscriptions.get(currencyPair));

        return subscription
                .stream

                // 3. Get a depth snapshot from
                // https://www.binance.com/api/v1/depth?symbol=BNBBTC&limit=1000
                // (we do this if we don't already have one or we've invalidated a previous one)
                .doOnNext(transaction -> subscription.initSnapshotIfInvalid(currencyPair))

                // If we failed, don't return anything. Just keep trying until it works
                .filter(transaction -> subscription.snapshotLastUpdateId.get() > 0L)

                // 4. Drop any event where u is < lastUpdateId in the snapshot
                .filter(depth -> depth.getLastUpdateId() >= subscription.snapshotLastUpdateId.get())

                // 5. The first processed should have U <= lastUpdateId AND u >= lastUpdateId
                .filter(
                        depth -> {
                            long lastUpdateId = subscription.lastUpdateId.get();
                            boolean result;
                            if (subscription.initiated) {
                                result = depth.getLastStreamUpdateId() == lastUpdateId;
                            } else if (lastUpdateId == 0L) {
                                result = true;
                            } else {
                                result =
                                        depth.getFirstUpdateId() <= lastUpdateId
                                                && depth.getLastUpdateId() >= lastUpdateId
                                ;
                            }
                            if (result) {
                                subscription.initiated = true;
                                subscription.lastUpdateId.set(depth.getLastUpdateId());
                            } else {
                                // If not, we re-sync.  This will commonly occur a few times when starting up, since
                                // given update ids 1,2,3,4,5,6,7,8,9, Binance may sometimes return a snapshot
                                // as of 5, but update events covering 1-3, 4-6 and 7-9.  We can't apply the 4-6
                                // update event without double-counting 5, and we can't apply the 7-9 update without
                                // missing 6.  The only thing we can do is to keep requesting a fresh snapshot until
                                // we get to a situation where the snapshot and an update event precisely line up.
                                LOG.info(
                                        "Orderbook snapshot for {} out of date (last={}, U={}, u={}). This is normal. Re-syncing.",
                                        currencyPair,
                                        lastUpdateId,
                                        depth.getFirstUpdateId(),
                                        depth.getLastUpdateId());
                                subscription.invalidateSnapshot();
                            }
                            return result;
                        })

                // 7. The data in each event is the absolute quantity for a price level
                // 8. If the quantity is 0, remove the price level
                // 9. Receiving an event that removes a price level that is not in your local order book can
                // happen and is normal.
                .map(
                        depth -> {
                            extractOrderBookUpdates(new FuturesContract(currencyPair, null), depth)
                                    .forEach(it -> subscription.orderBook.update(it));
                            return subscription.orderBook;
                        })
                .publish(1).refCount();
    }

    protected final class OrderbookSubscription {
        public final Flowable<DepthBinanceWebSocketTransaction> stream;
        public final AtomicLong lastUpdateId = new AtomicLong();
        public final AtomicLong snapshotLastUpdateId = new AtomicLong();
        public OrderBook orderBook;
        public boolean initiated;

        public OrderbookSubscription(Flowable<DepthBinanceWebSocketTransaction> stream) {
            this.stream = stream;
            this.initiated = false;
        }

        public void invalidateSnapshot() {
            snapshotLastUpdateId.set(0);
            initiated = false;
        }

        public void initSnapshotIfInvalid(CurrencyPair currencyPair) {
            if (snapshotLastUpdateId.get() != 0) return;
            try {
                LOG.info("Fetching initial orderbook snapshot for {} ", currencyPair);
                onApiCall.run();
                fallbackOnApiCall.get().run();
                BinanceOrderbook book = fetchBinanceOrderBook(currencyPair);
                snapshotLastUpdateId.set(book.lastUpdateId);
                lastUpdateId.set(book.lastUpdateId);
                orderBook = BinanceMarketDataService.convertOrderBook(book, new FuturesContract(currencyPair, null));
            } catch (Exception e) {
                LOG.error("Failed to fetch initial order book for " + currencyPair, e);
                snapshotLastUpdateId.set(0);
                lastUpdateId.set(0);
                orderBook = null;
            }
        }

        private BinanceOrderbook fetchBinanceOrderBook(CurrencyPair currencyPair)
                throws IOException, InterruptedException {
            try {
                return marketDataService.getBinanceOrderbook(currencyPair, 1000);
            } catch (BinanceException e) {
                if (BinanceErrorAdapter.adapt(e) instanceof RateLimitExceededException) {
                    if (fallenBack.compareAndSet(false, true)) {
                        LOG.error(
                                "API Rate limit was hit when fetching Binance order book snapshot. Provide a \n"
                                        + "rate limiter. Apache Commons and Google Guava provide the TimedSemaphore\n"
                                        + "and RateLimiter classes which are effective for this purpose. Example:\n"
                                        + "\n"
                                        + "  exchangeSpecification.setExchangeSpecificParametersItem(\n"
                                        + "      info.bitrich.xchangestream.util.Events.BEFORE_API_CALL_HANDLER,\n"
                                        + "      () -> rateLimiter.acquire())\n"
                                        + "\n"
                                        + "Pausing for 15sec and falling back to one call per three seconds, but you\n"
                                        + "will get more optimal performance by handling your own rate limiting.");
                        RateLimiter rateLimiter = RateLimiter.create(0.333);
                        fallbackOnApiCall.set(rateLimiter::acquire);
                        Thread.sleep(15000);
                    }
                }
                throw e;
            }
        }
    }
}
