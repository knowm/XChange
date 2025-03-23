package info.bitrich.xchangestream.bybit;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler.WebSocketMessageHandler;
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

  // spot, linear, inverse or option
  public static final String EXCHANGE_TYPE = "Exchange_Type";

  private BybitStreamingService streamingService;
  private BybitStreamingMarketDataService streamingMarketDataService;
  private BybitStreamingTradeService streamingTradeService;

  private BybitUserDataStreamingService userDataStreamingService;

  @Override
  protected void initServices() {
    super.initServices();
    this.streamingService = new BybitStreamingService(getApiUrl(), exchangeSpecification);
    if (isApiKeyValid()) {
      this.userDataStreamingService =
          new BybitUserDataStreamingService(getApiUrlWithAuth(), exchangeSpecification);
    }
    this.streamingMarketDataService = new BybitStreamingMarketDataService(streamingService);
    this.streamingTradeService = new BybitStreamingTradeService(userDataStreamingService);
  }

  private boolean isApiKeyValid() {
    return exchangeSpecification.getApiKey() != null
        && !exchangeSpecification.getApiKey().isEmpty();
  }

  private String getApiUrl() {
    String apiUrl;
    if (Boolean.TRUE.equals(
        exchangeSpecification.getExchangeSpecificParametersItem(SPECIFIC_PARAM_TESTNET))) {
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
      LOG.info("Connect to BybitStream with auth");
      completableList.add(userDataStreamingService.connect());
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
    if (userDataStreamingService != null) {
      userDataStreamingService.pingPongDisconnectIfConnected();
      completableList.add(userDataStreamingService.disconnect());
      userDataStreamingService = null;
    }
    return Completable.concat(completableList);
  }

  @Override
  public BybitStreamingTradeService getStreamingTradeService() {
    if (userDataStreamingService != null && userDataStreamingService.isAuthorized()) {
      return streamingTradeService;
    } else {
      throw new IllegalArgumentException("Authentication required for private streams");
    }
  }

  @Override
  public boolean isAlive() {
    // In normal situation - streamingService is always runs, userDataStreamingService - depends
    if (streamingService != null && streamingService.isSocketOpen()) {
      if (isApiKeyValid()) {
        return userDataStreamingService != null && userDataStreamingService.isSocketOpen();
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public BybitStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  /**
   * Enables the user to listen on channel inactive events and react appropriately.
   *
   * @param channelInactiveHandler a WebSocketMessageHandler instance.
   */
  public void setChannelInactiveHandler(WebSocketMessageHandler channelInactiveHandler) {
    streamingService.setChannelInactiveHandler(channelInactiveHandler);
  }

  public void setUserDataChannelInactiveHandler(WebSocketMessageHandler channelInactiveHandler) {
    userDataStreamingService.setChannelInactiveHandler(channelInactiveHandler);
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  public Observable<Throwable> reconnectFailurePrivateChannel() {
    return userDataStreamingService.subscribeReconnectFailure();
  }

  @Override
  public Observable<State> connectionStateObservable() {
    return streamingService.subscribeConnectionState();
  }

  public Observable<State> connectionStateObservablePrivateChannel() {
    return userDataStreamingService.subscribeConnectionState();
  }

  @Override
  public void resubscribeChannels() {
    streamingService.resubscribeChannels();
    if (userDataStreamingService != null) {
      userDataStreamingService.resubscribeChannels();
    }
  }

  @Override
  public Observable<Object> connectionIdle() {
    return streamingService.subscribeIdle();
  }

  public Observable<Object> connectionIdlePrivateChannel() {
    return userDataStreamingService.subscribeIdle();
  }
}
