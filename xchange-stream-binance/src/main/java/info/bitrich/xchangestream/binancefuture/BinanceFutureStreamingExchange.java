package info.bitrich.xchangestream.binancefuture;

import static java.util.Collections.emptyMap;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.binance.BinanceStreamingMarketDataService;
import info.bitrich.xchangestream.binance.exceptions.NoActiveChannelException;
import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;
import info.bitrich.xchangestream.binance.KlineSubscription;
import info.bitrich.xchangestream.core.ProductSubscription;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceFuturesAuthenticated;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple endpoint switch as we cannot inject it when setting up the endpoint.
 */
public class BinanceFutureStreamingExchange extends BinanceStreamingExchange {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceFutureStreamingExchange.class);

  private static final String WS_API_BASE_URI = "wss://fstream.binance.com/";
  private BinanceFuturesUserDataChannel userDataChannel;
  private BinanceFuturesStreamingAccountService streamingAccountService;
  private BinanceFuturesStreamingTradeService streamingTradeService;

  protected String getStreamingBaseUri() {
    return WS_API_BASE_URI;
  }

  /**
   * BinanceFutures streaming API expects connections to multiple channels to be defined at
   * connection time. To define the channels for this connection pass a `ProductSubscription` in at
   * connection time.
   *
   * @param args A single `ProductSubscription` to define the subscriptions required to be available
   *             during this connection.
   * @return A completable which fulfils once connection is complete.
   */
  @Override
  public Completable connect(ProductSubscription... args) {
    if (args == null || args.length == 0) {
      throw new IllegalArgumentException("Subscriptions must be made at connection time");
    }
    return internalConnect(new KlineSubscription(emptyMap()), args);
  }

  @Override
  public BinanceFuturesStreamingAccountService getStreamingAccountService() {
    return streamingAccountService;
  }

  @Override
  public BinanceFuturesStreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  private Completable internalConnect(
      KlineSubscription klineSubscription, ProductSubscription... args) {
    if (streamingService != null) {
      throw new UnsupportedOperationException(
          "Exchange only handles a single connection - disconnect the current connection.");
    }

    ProductSubscription subscriptions = args[0];
    streamingService = createStreamingService(subscriptions, klineSubscription);

    List<Completable> completables = new ArrayList<>();

    if (subscriptions.hasUnauthenticated() || klineSubscription.hasUnauthenticated()) {
      completables.add(streamingService.connect());
    }

    if (subscriptions.hasAuthenticated()) {
      if (exchangeSpecification.getApiKey() == null) {
        throw new IllegalArgumentException("API key required for authenticated streams");
      }

      LOG.info("Connecting to authenticated web socket");

      ExchangeSpecification spec = getExchangeSpecification();
      spec.setSslUri(FUTURES_URL);

      BinanceFuturesAuthenticated binanceFutures = ExchangeRestProxyBuilder
          .forInterface(BinanceFuturesAuthenticated.class, spec)
          .build();

      userDataChannel = new BinanceFuturesUserDataChannel(
          binanceFutures,
          exchangeSpecification.getApiKey(),
          onApiCall
      );

      try {
        completables.add(createAndConnectUserDataService(userDataChannel.getListenKey()));
      } catch (NoActiveChannelException e) {
        throw new IllegalStateException("Failed to establish user data channel", e);
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
    streamingAccountService = new BinanceFuturesStreamingAccountService(userDataStreamingService);
    streamingTradeService = new BinanceFuturesStreamingTradeService(userDataStreamingService);

    return Completable.concat(completables)
        .doOnComplete(
            () -> streamingMarketDataService.openSubscriptions(subscriptions, klineSubscription))
        .doOnComplete(() -> streamingAccountService.openSubscriptions())
        .doOnComplete(() -> streamingTradeService.openSubscriptions());
  }


  private Completable createAndConnectUserDataService(String listenKey) {
    userDataStreamingService =
        BinanceUserDataStreamingService.create(getStreamingBaseUri(), listenKey);
    applyStreamingSpecification(getExchangeSpecification(), userDataStreamingService);
    return userDataStreamingService
        .connect()
        .doOnComplete(
            () -> {
              LOG.info("Connected to authenticated web socket");
              userDataChannel.onChangeListenKey(
                  newListenKey ->
                      userDataStreamingService
                          .disconnect()
                          .doOnComplete(
                              () ->
                                  createAndConnectUserDataService(newListenKey)
                                      .doOnComplete(
                                          () -> {
                                            streamingAccountService.setUserDataStreamingService(
                                                userDataStreamingService);
                                            streamingTradeService.setUserDataStreamingService(
                                                userDataStreamingService);
                                          })));
            });
  }
}
