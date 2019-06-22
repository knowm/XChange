package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthOrder;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthPreTrade;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthTrade;
import info.bitrich.xchangestream.core.StreamingTradeService;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import org.knowm.xchange.bitfinex.v1.service.BitfinexTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.trade.TradeService;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public class BitfinexStreamingTradeService implements StreamingTradeService {

    private final BitfinexStreamingService service;
    private final BitfinexTradeService tradeService;
    private final Consumer<Method> onApiCall;

    private final AtomicInteger ordersCheckingIdle = new AtomicInteger();
    private final AtomicReference<Disposable> ordersOnIdle = new AtomicReference<>();
    private final Subject<Order> fetchedOrders = PublishSubject.<Order>create().toSerialized();
    private final ConcurrentMap<String, Date> openOrders = new ConcurrentHashMap<>();

    public BitfinexStreamingTradeService(BitfinexStreamingService service, BitfinexTradeService tradeService, Consumer<Method> onApiCall) {
        this.service = service;
        this.tradeService = tradeService;
        this.onApiCall = onApiCall;
    }

    public Observable<Order> getOrderChanges() {
        if (ordersCheckingIdle.getAndIncrement() == 0) {
            ordersOnIdle.set(service.getIdleAlerts()
                .subscribe(i -> {
                    onApiCall.run();
                    tradeService.getOpenOrders().getAllOpenOrders()
                            .forEach(fetchedOrders::onNext);
                }));
        }
        return getRawAuthenticatedOrders()
                .filter(o -> o.getId() != 0)
                .map(BitfinexStreamingAdapters::adaptOrder)
                .mergeWith(fetchedOrders)
                .filter(o -> latestTimestampsByTrade.compute(o.getId(), remappingFunction)




                lastTimestamp.getAndAccumulate(a.timestamp, (x, y) -> x.before(y) ? y : x)
                        .before(a.timestamp))
                .doOnComplete(() -> {
                    if (ordersCheckingIdle.decrementAndGet() == 0) {
                        ordersOnIdle.getAndSet(null).dispose();
                    }
                });
    }

    @Override
    public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        return getOrderChanges().filter(o -> currencyPair.equals(o.getCurrencyPair()));
    }

    /**
     * Gets a stream of all user trades to which we are subscribed.
     *
     * @return The stream of user trades.
     */
    public Observable<UserTrade> getUserTrades() {
        return getRawAuthenticatedTrades()
                .filter(o -> o.getId() != 0)
                .map(BitfinexStreamingAdapters::adaptUserTrade);
    }

    @Override
    public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        return getUserTrades().filter(t -> currencyPair.equals(t.getCurrencyPair()));
    }

    public Observable<BitfinexWebSocketAuthOrder> getRawAuthenticatedOrders() {
        return withAuthenticatedService(BitfinexStreamingService::getAuthenticatedOrders);
    }

    public Observable<BitfinexWebSocketAuthPreTrade> getRawAuthenticatedPreTrades() {
        return withAuthenticatedService(BitfinexStreamingService::getAuthenticatedPreTrades);
    }

    public Observable<BitfinexWebSocketAuthTrade> getRawAuthenticatedTrades() {
        return withAuthenticatedService(BitfinexStreamingService::getAuthenticatedTrades);
    }

    private <T> Observable<T> withAuthenticatedService(Function<BitfinexStreamingService, Observable<T>> serviceConsumer) {
        if (!service.isAuthenticated()) {
            throw new ExchangeSecurityException("Not authenticated");
        }
        return serviceConsumer.apply(service);
    }
}
