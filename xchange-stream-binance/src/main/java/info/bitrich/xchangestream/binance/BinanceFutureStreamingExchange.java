package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import info.bitrich.xchangestream.util.Events;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.binance.BinanceFutureAuthenticated;
import org.knowm.xchange.binance.BinanceFutureExchange;
import org.knowm.xchange.binance.service.BinanceFutureMarketDataService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple endpoint switch as we cannot inject it when setting up the endpoint.
 */
public class BinanceFutureStreamingExchange extends BinanceFutureExchange implements StreamingExchange {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceFutureStreamingExchange.class);
    //    private static final String WS_API_BASE_URI = "wss://fstream-auth.binance.com/";
    private static final String WS_API_BASE_URI = "wss://fstream.binance.com/";
    private static final String WS_SANDBOX_API_BASE_URI = "wss://stream.binancefuture.com";
    public static final String USE_HIGHER_UPDATE_FREQUENCY =
            "Binance_Orderbook_Use_Higher_Frequency";
    public static final String USE_REALTIME_BOOK_TICKER = "Binance_Ticker_Use_Realtime";
    public static final String FETCH_ORDER_BOOK_LIMIT = "Binance_Fetch_Order_Book_Limit";

    private BinanceStreamingService streamingService;
    private BinanceFutureUserDataChannel userDataChannel;
    private BinanceUserDataStreamingService userDataStreamingService;

    private BinanceFutureStreamingMarketDataService streamingMarketDataService;
    private BinanceStreamingAccountService streamingAccountService;
    private BinanceStreamingTradeService streamingTradeService;

    Runnable onApiCall;
    boolean realtimeOrderBookTicker;
    String orderBookUpdateFrequencyParameter = "";
    int oderBookFetchLimitParameter = 1000;


    @Override
    protected void initServices() {
        super.initServices();
        this.onApiCall = Events.onApiCall(exchangeSpecification);
        boolean userHigherFrequency =
                Boolean.TRUE.equals(
                        exchangeSpecification.getExchangeSpecificParametersItem(USE_HIGHER_UPDATE_FREQUENCY));
        realtimeOrderBookTicker =
                Boolean.TRUE.equals(
                        exchangeSpecification.getExchangeSpecificParametersItem(USE_REALTIME_BOOK_TICKER));
        if (userHigherFrequency) {
            orderBookUpdateFrequencyParameter = "@100ms";
        }
        Object fetchOrderBookLimit =
                exchangeSpecification.getExchangeSpecificParametersItem(FETCH_ORDER_BOOK_LIMIT);
        if (fetchOrderBookLimit instanceof Integer) {
            oderBookFetchLimitParameter = (int) fetchOrderBookLimit;
        }
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Subscriptions must be made at connection time");
        }
        if (streamingService != null) {
            throw new UnsupportedOperationException(
                    "Exchange only handles a single connection - disconnect the current connection.");
        }

        ProductSubscription subscriptions = args[0];
        streamingService = createStreamingService(subscriptions);

        List<Completable> completables = new ArrayList<>();

        if (subscriptions.hasUnauthenticated()) {
            completables.add(streamingService.connect());
        }

        if (subscriptions.hasAuthenticated()) {
            if (exchangeSpecification.getApiKey() == null) {
                throw new IllegalArgumentException("API key required for authenticated streams");
            }

            LOG.info("Connecting to authenticated web socket");
            BinanceFutureAuthenticated binance =
                    ExchangeRestProxyBuilder.forInterface(
                                    BinanceFutureAuthenticated.class, getExchangeSpecification())
                            .build();
            userDataChannel =
                    new BinanceFutureUserDataChannel(binance, exchangeSpecification.getApiKey(), onApiCall);
            try {
                completables.add(createAndConnectUserDataService(userDataChannel.getListenKey()));
            } catch (BinanceFutureUserDataChannel.NoActiveChannelException e) {
                throw new IllegalStateException("Failed to establish user data channel", e);
            }
        }

        streamingMarketDataService =
                new BinanceFutureStreamingMarketDataService(
                        streamingService,
                        (BinanceFutureMarketDataService) marketDataService,
                        onApiCall,
                        orderBookUpdateFrequencyParameter,
                        realtimeOrderBookTicker,
                        oderBookFetchLimitParameter);
        streamingAccountService = new BinanceStreamingAccountService(userDataStreamingService);
        streamingTradeService = new BinanceStreamingTradeService(userDataStreamingService);

        return Completable.concat(completables)
                .doOnComplete(() -> streamingMarketDataService.openSubscriptions(subscriptions))
                .doOnComplete(() -> streamingAccountService.openSubscriptions())
                .doOnComplete(() -> streamingTradeService.openSubscriptions());
    }

    protected BinanceStreamingService createStreamingService(ProductSubscription subscription) {
        String path =
                getStreamingBaseUri() + "stream?streams=" + buildSubscriptionStreams(subscription);
        return new BinanceStreamingService(path, subscription);
    }

    public String buildSubscriptionStreams(ProductSubscription subscription) {
        return Stream.of(
                        buildSubscriptionStrings(
                                subscription.getTicker(),
                                realtimeOrderBookTicker
                                        ? BinanceSubscriptionType.BOOK_TICKER.getType()
                                        : BinanceSubscriptionType.TICKER.getType()),
                        buildSubscriptionStrings(
                                subscription.getOrderBook(), BinanceSubscriptionType.DEPTH.getType()),
                        buildSubscriptionStrings(
                                subscription.getTrades(), BinanceSubscriptionType.TRADE.getType()))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("/"));
    }

    private String buildSubscriptionStrings(
            List<CurrencyPair> currencyPairs, String subscriptionType) {
        if (BinanceSubscriptionType.DEPTH.getType().equals(subscriptionType)) {
            return subscriptionStrings(currencyPairs)
                    .map(s -> s + "@" + subscriptionType + orderBookUpdateFrequencyParameter)
                    .collect(Collectors.joining("/"));
        } else {
            return subscriptionStrings(currencyPairs)
                    .map(s -> s + "@" + subscriptionType)
                    .collect(Collectors.joining("/"));
        }
    }

    private static Stream<String> subscriptionStrings(List<CurrencyPair> currencyPairs) {
        return currencyPairs.stream()
                .map(pair -> String.join("", pair.toString().split("/")).toLowerCase());
    }

    public void enableLiveSubscription() {
        if (this.streamingService == null) {
            throw new UnsupportedOperationException(
                    "You must connect to streams before enabling live subscription.");
        }
        this.streamingService.enableLiveSubscription();
    }

    private Completable createAndConnectUserDataService(String listenKey) {
        userDataStreamingService =
                BinanceUserDataStreamingService.create(getStreamingBaseUri(), listenKey);
        return userDataStreamingService
                .connect()
                .doOnComplete(
                        () -> {
                            LOG.info("Connected to authenticated web socket");
                            userDataChannel.onChangeListenKey(
                                    newListenKey -> {
                                        userDataStreamingService
                                                .disconnect()
                                                .doOnComplete(
                                                        () -> {
                                                            createAndConnectUserDataService(newListenKey)
                                                                    .doOnComplete(
                                                                            () -> {
                                                                                streamingAccountService.setUserDataStreamingService(
                                                                                        userDataStreamingService);
                                                                                streamingTradeService.setUserDataStreamingService(
                                                                                        userDataStreamingService);
                                                                            });
                                                        });
                                    });
                        });
    }

    @Override
    public Completable disconnect() {
        List<Completable> completables = new ArrayList<>();
        completables.add(streamingService.disconnect());
        streamingService = null;
        if (userDataStreamingService != null) {
            completables.add(userDataStreamingService.disconnect());
            userDataStreamingService = null;
        }
        if (userDataChannel != null) {
            userDataChannel.close();
            userDataChannel = null;
        }
        streamingMarketDataService = null;
        return Completable.concat(completables);
    }


    @Override
    public boolean isAlive() {
        return streamingService != null && streamingService.isSocketOpen();
    }

    @Override
    public Observable<Throwable> reconnectFailure() {
        return streamingService.subscribeReconnectFailure();
    }

    @Override
    public Observable<Object> connectionSuccess() {
        return streamingService.subscribeConnectionSuccess();
    }

    @Override
    public Observable<ConnectionStateModel.State> connectionStateObservable() {
        return streamingService.subscribeConnectionState();
    }

    @Override
    public BinanceFutureStreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public BinanceStreamingAccountService getStreamingAccountService() {
        return streamingAccountService;
    }

    @Override
    public BinanceStreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        streamingService.useCompressedMessages(compressedMessages);
    }

    protected String getStreamingBaseUri() {
        return WS_API_BASE_URI;
    }
}
