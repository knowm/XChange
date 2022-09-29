package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.binance.BinanceUserDataChannel.NoActiveChannelException;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import info.bitrich.xchangestream.util.Events;
import io.reactivex.Completable;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.trade.margin.MarginAccountType;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceStreamingExchange extends BinanceExchange implements StreamingExchange {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceStreamingExchange.class);
  private static final String WS_API_BASE_URI = "wss://stream.binance.com:9443/";
  private static final String WS_SANDBOX_API_BASE_URI = "wss://testnet.binance.vision/";
  protected static final String USE_HIGHER_UPDATE_FREQUENCY =
      "Binance_Orderbook_Use_Higher_Frequency";
  protected static final String USE_REALTIME_BOOK_TICKER = "Binance_Ticker_Use_Realtime";
  protected static final String FETCH_ORDER_BOOK_LIMIT = "Binance_Fetch_Order_Book_Limit";
  protected static final String SUBSCRIBE_TO_MARGIN_TRADING = "Binance_Subscribe_To_Margin_Trading";
  protected static final String SUBSCRIBE_TO_ISOLATED_MARGIN_TRADING = "Binance_Subscribe_To_Isolated_Margin_Trading";

  private BinanceStreamingService streamingService;
  private BinanceUserDataStreamingService spotUserDataStreamingService;
  private BinanceUserDataStreamingService marginUserDataStreamingService;
  private BinanceUserDataStreamingService isolatedMarginUserDataStreamingService;

  private BinanceStreamingMarketDataService streamingMarketDataService;
  private StreamingAccountService streamingAccountService;
  private BinanceStreamingAccountService streamingSpotAccountService;
  private BinanceStreamingAccountService streamingMarginAccountService;
  private BinanceStreamingAccountService streamingIsolatedMarginAccountService;
  private StreamingTradeService streamingTradeService;
  private BinanceStreamingTradeService streamingSpotTradeService;
  private BinanceStreamingTradeService streamingMarginTradeService;
  private BinanceStreamingTradeService streamingIsolatedMarginTradeService;

  private BinanceUserDataChannel spotUserDataChannel;
  private BinanceUserDataChannel marginUserDataChannel;
  private BinanceUserDataChannel isolatedMarginUserDataChannel;
  private Runnable onApiCall;
  private String orderBookUpdateFrequencyParameter = "";
  private int oderBookFetchLimitParameter = 1000;
  private boolean realtimeOrderBookTicker;

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

  /**
   * Binance streaming API expects connections to multiple channels to be defined at connection
   * time. To define the channels for this connection pass a `ProductSubscription` in at connection
   * time.
   *
   * @param args A single `ProductSubscription` to define the subscriptions required to be available
   *     during this connection.
   * @return A completable which fulfils once connection is complete.
   */
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
      BinanceAuthenticated binance =
          ExchangeRestProxyBuilder.forInterface(
                  BinanceAuthenticated.class, getExchangeSpecification())
              .build();
      spotUserDataChannel =
          new BinanceUserDataChannel(binance, exchangeSpecification.getApiKey(), onApiCall);
      try {
        completables.add(createAndConnectSpotUserDataService(spotUserDataChannel.getListenKey()));
      } catch (NoActiveChannelException e) {
        throw new IllegalStateException("Failed to establish user data channel", e);
      }

      if (Boolean.TRUE.equals(
              exchangeSpecification.getExchangeSpecificParametersItem(SUBSCRIBE_TO_MARGIN_TRADING))) {
        LOG.info("Connecting to authenticated margin web socket");
        marginUserDataChannel =
                new BinanceMarginUserDataChannel(binance, exchangeSpecification.getApiKey(), onApiCall);
        try {
          Completable marginUserDataServiceCompletable = createAndConnectMarginUserDataService(marginUserDataChannel.getListenKey());
          streamingMarginAccountService = new BinanceStreamingAccountService(marginUserDataStreamingService);
          streamingMarginTradeService = new BinanceStreamingTradeService(this, marginUserDataStreamingService, MarginAccountType.CROSS);
          completables.add(marginUserDataServiceCompletable
                  .doOnComplete(() -> streamingMarginAccountService.openSubscriptions())
                  .doOnComplete(() -> streamingMarginTradeService.openSubscriptions())
          );
        } catch (NoActiveChannelException e) {
          throw new IllegalStateException("Failed to establish margin user data channel", e);
        }
      }

      if (Boolean.TRUE.equals(
              exchangeSpecification.getExchangeSpecificParametersItem(SUBSCRIBE_TO_ISOLATED_MARGIN_TRADING))) {
        LOG.info("Connecting to authenticated isolated margin web socket");
        isolatedMarginUserDataChannel =
                new BinanceIsolatedMarginUserDataChannel(binance, exchangeSpecification.getApiKey(), onApiCall);
        try {
          Completable isolatedMarginUserDataServiceCompletable = createAndConnectIsolatedMarginUserDataService(isolatedMarginUserDataChannel.getListenKey());
          streamingIsolatedMarginAccountService = new BinanceStreamingAccountService(isolatedMarginUserDataStreamingService);
          streamingIsolatedMarginTradeService = new BinanceStreamingTradeService(this, isolatedMarginUserDataStreamingService, MarginAccountType.ISOLATED);
          completables.add(isolatedMarginUserDataServiceCompletable
                  .doOnComplete(() -> streamingIsolatedMarginAccountService.openSubscriptions())
                  .doOnComplete(() -> streamingIsolatedMarginTradeService.openSubscriptions())
          );
        } catch (NoActiveChannelException e) {
          throw new IllegalStateException("Failed to establish isolated margin user data channel", e);
        }
      }
    }

    streamingMarketDataService =
        new BinanceStreamingMarketDataService(
            streamingService,
            (BinanceMarketDataService) marketDataService,
            onApiCall,
            orderBookUpdateFrequencyParameter,
            realtimeOrderBookTicker,
            oderBookFetchLimitParameter);
    streamingSpotAccountService = new BinanceStreamingAccountService(spotUserDataStreamingService);
    streamingSpotTradeService = new BinanceStreamingTradeService(this, spotUserDataStreamingService, null);

    streamingAccountService = streamingMarginAccountService != null || streamingIsolatedMarginAccountService != null
            ? new CompositeStreamingAccountService(streamingSpotAccountService, streamingMarginAccountService, streamingIsolatedMarginAccountService)
            : streamingSpotAccountService;

    streamingTradeService = streamingMarginTradeService != null || streamingIsolatedMarginTradeService != null
            ? new CompositeStreamingTradeService(streamingSpotTradeService, streamingMarginTradeService, streamingIsolatedMarginTradeService)
            : streamingSpotTradeService;

    return Completable.concat(completables)
            .doOnComplete(() -> streamingMarketDataService.openSubscriptions(subscriptions))
            .doOnComplete(() -> streamingSpotAccountService.openSubscriptions())
            .doOnComplete(() -> streamingSpotTradeService.openSubscriptions());
  }

  private Completable createAndConnectSpotUserDataService(String listenKey) {
    spotUserDataStreamingService =
        BinanceUserDataStreamingService.create(getStreamingBaseUri(), listenKey);
    applyStreamingSpecification(getExchangeSpecification(), spotUserDataStreamingService);
    return spotUserDataStreamingService
        .connect()
        .doOnComplete(
            () -> {
              LOG.info("Connected to authenticated web socket");
              spotUserDataChannel.onChangeListenKey(
                  newListenKey -> {
                    spotUserDataStreamingService
                        .disconnect()
                        .doOnComplete(
                            () -> {
                              createAndConnectSpotUserDataService(newListenKey)
                                  .doOnComplete(
                                      () -> {
                                        streamingSpotAccountService.setUserDataStreamingService(
                                                spotUserDataStreamingService);
                                        streamingSpotTradeService.setUserDataStreamingService(
                                                spotUserDataStreamingService);
                                      });
                            });
                  });
            });
  }

  private Completable createAndConnectMarginUserDataService(String listenKey) {
    marginUserDataStreamingService =
        BinanceUserDataStreamingService.create(getStreamingBaseUri(), listenKey);
    applyStreamingSpecification(getExchangeSpecification(), marginUserDataStreamingService);
    return marginUserDataStreamingService
        .connect()
        .doOnComplete(
            () -> {
              LOG.info("Connected to margin authenticated web socket");
              marginUserDataChannel.onChangeListenKey(
                  newListenKey -> {
                    marginUserDataStreamingService
                        .disconnect()
                        .doOnComplete(
                            () -> {
                              createAndConnectMarginUserDataService(newListenKey)
                                  .doOnComplete(
                                      () -> {
                                        streamingMarginAccountService.setUserDataStreamingService(
                                                marginUserDataStreamingService);
                                        streamingMarginTradeService.setUserDataStreamingService(
                                                marginUserDataStreamingService);
                                      });
                            });
                  });
            });
  }

  private Completable createAndConnectIsolatedMarginUserDataService(String listenKey) {
    isolatedMarginUserDataStreamingService =
            BinanceUserDataStreamingService.create(getStreamingBaseUri(), listenKey);
    applyStreamingSpecification(getExchangeSpecification(), isolatedMarginUserDataStreamingService);
    return isolatedMarginUserDataStreamingService
            .connect()
            .doOnComplete(
                    () -> {
                      LOG.info("Connected to margin authenticated web socket");
                      isolatedMarginUserDataChannel.onChangeListenKey(
                              newListenKey -> {
                                isolatedMarginUserDataStreamingService
                                        .disconnect()
                                        .doOnComplete(
                                                () -> {
                                                  createAndConnectIsolatedMarginUserDataService(newListenKey)
                                                          .doOnComplete(
                                                                  () -> {
                                                                    streamingIsolatedMarginAccountService.setUserDataStreamingService(
                                                                            isolatedMarginUserDataStreamingService);
                                                                    streamingIsolatedMarginTradeService.setUserDataStreamingService(
                                                                            isolatedMarginUserDataStreamingService);
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
    if (spotUserDataStreamingService != null) {
      completables.add(spotUserDataStreamingService.disconnect());
      spotUserDataStreamingService = null;
    }
    if (marginUserDataStreamingService != null) {
      completables.add(marginUserDataStreamingService.disconnect());
      marginUserDataStreamingService = null;
    }
    if (isolatedMarginUserDataStreamingService != null) {
      completables.add(isolatedMarginUserDataStreamingService.disconnect());
      isolatedMarginUserDataStreamingService = null;
    }
    if (spotUserDataChannel != null) {
      spotUserDataChannel.close();
      spotUserDataChannel = null;
    }
    if (marginUserDataChannel != null) {
      marginUserDataChannel.close();
      marginUserDataChannel = null;
    }
    if (isolatedMarginUserDataChannel != null) {
      isolatedMarginUserDataChannel.close();
      isolatedMarginUserDataChannel = null;
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
  public Observable<State> connectionStateObservable() {
    return streamingService.subscribeConnectionState();
  }

  @Override
  public BinanceStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public StreamingAccountService getStreamingAccountService() {
    return streamingAccountService;
  }

  public BinanceStreamingAccountService getStreamingSpotAccountService() {
    return streamingSpotAccountService;
  }

  public BinanceStreamingAccountService getStreamingMarginAccountService() {
    return streamingMarginAccountService;
  }

  public BinanceStreamingAccountService getStreamingIsolatedMarginAccountService() {
    return streamingIsolatedMarginAccountService;
  }

  @Override
  public StreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  public BinanceStreamingTradeService getStreamingSpotTradeService() {
    return streamingSpotTradeService;
  }

  public BinanceStreamingTradeService getStreamingMarginTradeService() {
    return streamingMarginTradeService;
  }

  public BinanceStreamingTradeService getStreamingIsolatedMarginTradeService() {
    return streamingIsolatedMarginTradeService;
  }

  protected BinanceStreamingService createStreamingService(ProductSubscription subscription) {
    String path =
        getStreamingBaseUri() + "stream?streams=" + buildSubscriptionStreams(subscription);
    BinanceStreamingService streamingService = new BinanceStreamingService(path, subscription);
    applyStreamingSpecification(getExchangeSpecification(), streamingService);
    return streamingService;
  }

  protected String getStreamingBaseUri() {
    return Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))
        ? WS_SANDBOX_API_BASE_URI
        : WS_API_BASE_URI;
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

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  public void enableLiveSubscription() {
    if (this.streamingService == null) {
      throw new UnsupportedOperationException(
          "You must connect to streams before enabling live subscription.");
    }
    this.streamingService.enableLiveSubscription();
  }

  public void disableLiveSubscription() {
    if (this.streamingService != null) this.streamingService.disableLiveSubscription();
  }
}
