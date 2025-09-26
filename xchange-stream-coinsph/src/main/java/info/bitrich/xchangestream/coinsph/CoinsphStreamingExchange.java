package info.bitrich.xchangestream.coinsph;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.core.StreamingExchangeConfiguration;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinsphStreamingExchange extends CoinsphExchange implements StreamingExchange {
  private static final Logger LOG = LoggerFactory.getLogger(CoinsphStreamingExchange.class);

  public static final String PUBLIC_API_URI = "wss://wsapi.pro.coins.ph/openapi/quote/stream";
  private CoinsphStreamingService publicStreamingService;
  private CoinsphStreamingService privateStreamingService; // For user data streams

  private CoinsphStreamingMarketDataService streamingMarketDataService;
  private CoinsphStreamingTradeService streamingTradeService;
  private CoinsphStreamingAccountService streamingAccountService;

  private StreamingExchangeConfiguration configuration;

  private String userDataApiBaseUri = "wss://wsapi.pro.coins.ph/openapi/ws/";

  public CoinsphStreamingExchange() {
    this.configuration = getDefaultConfiguration();
  }

  /** Returns the default configuration for this exchange. */
  public static StreamingExchangeConfiguration getDefaultConfiguration() {
    return new StreamingExchangeConfiguration();
  }

  @Override
  protected void initServices() {
    super.initServices(); // Initializes REST services
  }

  private void initStreamingServices() {
    if (Boolean.TRUE.equals(
        getExchangeSpecification().getExchangeSpecificParametersItem(USE_SANDBOX))) {
      this.userDataApiBaseUri = "wss://ws.9001.pl-qa.coinsxyz.me/openapi/ws/";
    }
    this.publicStreamingService =
        new CoinsphStreamingService(
            PUBLIC_API_URI, null, false); // No account service needed for public

    try {
      // Initialize private streaming service if we have API credentials
      if (exchangeSpecification.getApiKey() != null
          && exchangeSpecification.getSecretKey() != null) {
        LOG.info("Initializing private streaming service with API credentials");
        this.privateStreamingService =
            new CoinsphStreamingService(this, configuration); // Mark as private service
      } else {
        LOG.info("No API credentials provided, private streaming service won't be initialized");
        this.privateStreamingService = null;
      }
    } catch (Exception e) {
      LOG.error("Failed to initialize private streaming service", e);
      this.privateStreamingService = null;
    }

    this.streamingMarketDataService = new CoinsphStreamingMarketDataService(publicStreamingService);

    if (privateStreamingService != null) {
      this.streamingTradeService =
          new CoinsphStreamingTradeService(privateStreamingService, getTradeService());
      this.streamingAccountService =
          new CoinsphStreamingAccountService(privateStreamingService, getAccountService());
    }
  }

  /** Get the base URI for user data streams. */
  public String getUserStreamingBaseUri() {
    return userDataApiBaseUri;
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    if (publicStreamingService == null || privateStreamingService == null) {
      initStreamingServices();
    }

    // Connect services that are available
    Completable publicConnect = Completable.complete();
    Completable privateConnect = Completable.complete();

    if (publicStreamingService != null) {
      publicConnect = publicStreamingService.connect();
    }

    if (privateStreamingService != null) {
      privateConnect = privateStreamingService.connect();
    }

    return Completable.concatArray(publicConnect, privateConnect);
  }

  @Override
  public Completable disconnect() {
    Completable publicDisconnect = Completable.complete();
    Completable privateDisconnect = Completable.complete();

    if (publicStreamingService != null) {
      publicDisconnect = publicStreamingService.disconnect();
    }

    if (privateStreamingService != null) {
      privateDisconnect = privateStreamingService.disconnect();
    }

    return Completable.concatArray(publicDisconnect, privateDisconnect);
  }

  @Override
  public boolean isAlive() {
    // Consider alive if both services are configured and at least one is open,
    // or define more specific logic (e.g. public must be alive for market data)
    boolean publicAlive = publicStreamingService != null && publicStreamingService.isSocketOpen();
    boolean privateAlive =
        privateStreamingService != null && privateStreamingService.isSocketOpen();
    return publicAlive || privateAlive; // Or publicAlive && privateAlive if both are essential
  }

  // These might need to be more nuanced if we want to distinguish between public/private service
  // events
  @Override
  public Observable<Throwable> reconnectFailure() {
    // Merge or choose one? For now, let's take public as primary for general health.
    return publicStreamingService != null
        ? publicStreamingService.subscribeReconnectFailure()
        : Observable.empty();
    // Or: return Observable.merge(publicStreamingService.subscribeReconnectFailure(),
    // privateStreamingService.subscribeReconnectFailure());
  }

  @Override
  public Observable<Object> connectionSuccess() {
    return publicStreamingService != null
        ? publicStreamingService.subscribeConnectionSuccess()
        : Observable.empty();
    // Or: return Observable.merge(publicStreamingService.subscribeConnectionSuccess(),
    // privateStreamingService.subscribeConnectionSuccess());
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    if (streamingMarketDataService == null) initStreamingServices();
    return streamingMarketDataService;
  }

  @Override
  public StreamingTradeService getStreamingTradeService() {
    if (streamingTradeService == null) initStreamingServices();
    return streamingTradeService;
  }

  @Override
  public info.bitrich.xchangestream.core.StreamingAccountService getStreamingAccountService() {
    if (streamingAccountService == null) initStreamingServices();
    return streamingAccountService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    if (compressedMessages) {
      LOG.warn(
          "Compressed messages requested, but Coins.ph WebSocket compression support is unconfirmed. Ignoring.");
    }
  }

  @Override
  public void resubscribeChannels() {
    if (publicStreamingService != null) {
      publicStreamingService.resubscribeChannels();
    }
    if (privateStreamingService != null) {
      privateStreamingService.resubscribeChannels();
    }
  }
}
