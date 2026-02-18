package info.bitrich.xchangestream.bybit;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamingExchange extends BybitExchange implements StreamingExchange {

  private final Logger LOG = LoggerFactory.getLogger(BybitStreamingExchange.class);

  // https://bybit-exchange.github.io/docs/v5/ws/connect
  public static final String URI = "wss://stream.bybit.com/v5/public";
  public static final String TESTNET_URI = "wss://stream-testnet.bybit.com/v5/public";
  // DEMO_URI without auth is the same as URI

  public static final String AUTH_URI = "wss://stream.bybit.com/v5/private";
  public static final String TESTNET_AUTH_URI = "wss://stream-testnet.bybit.com/v5/private";
  public static final String DEMO_AUTH_URI = "wss://stream-demo.bybit.com/v5/private";

  // websocket trade
  public static final String TRADE_URI = "wss://stream.bybit.com/v5/trade";
  public static final String TESTNET_TRADE_URI = "wss://stream-testnet.bybit.com/v5/trade";

  // spot, linear, inverse or option
  public static final String EXCHANGE_TYPE = "Exchange_Type";

  private BybitStreamingService streamingService;
  private BybitStreamingMarketDataService streamingMarketDataService;
  private BybitStreamingTradeService streamingTradeService;
  private BybitUserTradeStreamingService streamingUserTradeService;
  private BybitUserDataStreamingService streamingUserDataService;

  @Override
  protected void initServices() {
    super.initServices();
    applyWebsocketTimeouts(exchangeSpecification);
    this.streamingService = new BybitStreamingService(getApiUrl(), exchangeSpecification);
    applyStreamingSpecification(exchangeSpecification, streamingService);
    if (isApiKeyValid()) {
      this.streamingUserDataService =
          new BybitUserDataStreamingService(getApiUrlWithAuth(), exchangeSpecification);
      applyStreamingSpecification(exchangeSpecification, streamingUserDataService);
      this.streamingUserTradeService =
          new BybitUserTradeStreamingService(getTradeApiUrlWithAuth(), exchangeSpecification);
      applyStreamingSpecification(exchangeSpecification, streamingUserTradeService);
    }
    this.streamingMarketDataService = new BybitStreamingMarketDataService(streamingService);
    this.streamingTradeService =
        new BybitStreamingTradeService(
            streamingUserDataService, streamingUserTradeService, getResilienceRegistries(), this);
  }

  private String getTradeApiUrlWithAuth() {
    if (Boolean.TRUE.equals(
        exchangeSpecification.getExchangeSpecificParametersItem(SPECIFIC_PARAM_TESTNET))) {
      return TESTNET_TRADE_URI;
    } else {
      return TRADE_URI;
    }
  }

  private boolean isApiKeyValid() {
    return exchangeSpecification.getApiKey() != null
        && !exchangeSpecification.getApiKey().isEmpty()
        && exchangeSpecification.getSecretKey() != null
        && !exchangeSpecification.getSecretKey().isEmpty();
  }

  private String getApiUrl() {
    String apiUrl;
    if (Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
      apiUrl = TESTNET_URI;
    } else {
      apiUrl = URI;
    }
    apiUrl +=
        "/"
            + ((BybitCategory)
                    exchangeSpecification.getExchangeSpecificParametersItem(EXCHANGE_TYPE))
                .getValue();
    return apiUrl;
  }

  private String getApiUrlWithAuth() {
    String apiUrl;
    if (Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
      apiUrl = DEMO_AUTH_URI;
    } else {
      if (Boolean.TRUE.equals(
          exchangeSpecification.getExchangeSpecificParametersItem(SPECIFIC_PARAM_TESTNET))) {
        apiUrl = TESTNET_AUTH_URI;
      } else {
        apiUrl = AUTH_URI;
      }
    }
    return apiUrl;
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    LOG.info("Connect to BybitStream");
    List<Completable> completableList = new ArrayList<>();
    completableList.add(streamingService.connect());
    if (isApiKeyValid()) {
      completableList.add(streamingUserDataService.connect());
      completableList.add(streamingUserTradeService.connect());
    }
    return Completable.concat(completableList);
  }

  @Override
  public Completable disconnect() {
    List<Completable> completableList = new ArrayList<>();
    if (streamingService != null) {
      streamingService.pingPongDisconnectIfConnected();
      completableList.add(streamingService.disconnect());
      streamingService = null;
    }
    if (streamingUserDataService != null) {
      streamingUserDataService.pingPongDisconnectIfConnected();
      completableList.add(streamingUserDataService.disconnect());
      streamingUserDataService = null;
    }
    if (streamingUserTradeService != null) {
      completableList.add(streamingUserTradeService.disconnect());
      streamingUserTradeService = null;
    }
    return Completable.concat(completableList);
  }

  @Override
  public BybitStreamingTradeService getStreamingTradeService() {
    if (streamingUserDataService != null && streamingUserDataService.isAuthorized()) {
      return streamingTradeService;
    } else {
      throw new IllegalArgumentException("Authentication required for private streams");
    }
  }

  @Override
  public boolean isAlive() {
    // In a normal situation - streamingService is always runs, userDataStreamingService - depends
    if (streamingService != null) {
      if (isApiKeyValid()) {
        return streamingService.isSocketOpen()
            && streamingUserDataService.isSocketOpen()
            && streamingUserDataService.isAuthorized()
            && streamingUserTradeService.isSocketOpen()
            && streamingUserTradeService.isAuthorized();
      } else {
        return streamingService.isSocketOpen();
      }
    }
    return false;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public BybitStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  public Observable<Throwable> reconnectFailurePrivateChannel() {
    return streamingUserDataService.subscribeReconnectFailure();
  }

  @Override
  public Observable<State> connectionStateObservable() {
    return streamingService.subscribeConnectionState();
  }

  public Observable<State> connectionStateObservablePrivateChannel() {
    return streamingUserDataService.subscribeConnectionState();
  }

  public Observable<State> connectionStateObservableTradeChannel() {
    return streamingUserTradeService.subscribeConnectionState();
  }

  @Override
  public void resubscribeChannels() {
    streamingService.resubscribeChannels();
    if (streamingUserDataService != null) {
      streamingUserDataService.resubscribeChannels();
    }
  }
}
