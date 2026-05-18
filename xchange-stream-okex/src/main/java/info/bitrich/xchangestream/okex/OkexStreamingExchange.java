package info.bitrich.xchangestream.okex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okex.OkexExchange;

import java.util.ArrayList;
import java.util.List;

public class OkexStreamingExchange extends OkexExchange implements StreamingExchange {

  // Production URIs
  public static final String WS_PUBLIC_CHANNEL_URI = "wss://ws.okx.com:8443/ws/v5/public";
  public static final String WS_PRIVATE_CHANNEL_URI = "wss://ws.okx.com:8443/ws/v5/private";
  public static final String WS_BUSINESS_CHANNEL_URI = "wss://ws.okx.com:8443/ws/v5/business";

  // Demo(Sandbox) URIs
  public static final String SANDBOX_WS_PUBLIC_CHANNEL_URI =
          "wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999";
  public static final String SANDBOX_WS_PRIVATE_CHANNEL_URI =
          "wss://wspap.okx.com:8443/ws/v5/private?brokerId=9999";
  public static final String SANDBOX_WS_BUSINESS_CHANNEL_URI =
      "wss://wspap.okx.com:8443/ws/v5/business?brokerId=9999";

  private OkexStreamingService streamingService;

  private OkexStreamingMarketDataService streamingMarketDataService;

  private OkexStreamingTradeService streamingTradeService;

  private OkexPrivateStreamingService privateStreamingService;
  private OkexBusinessStreamingService businessStreamingService;

  public OkexStreamingExchange() {
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    applyWebsocketTimeouts(exchangeSpecification);
    streamingService = new OkexStreamingService(getPublicApiUrl(), exchangeSpecification);
    applyStreamingSpecification(exchangeSpecification, streamingService);
    if (isApiKeyValid()) {
      privateStreamingService =
          new OkexPrivateStreamingService(getPrivateApiUrl(), exchangeSpecification, this);
      applyStreamingSpecification(exchangeSpecification, privateStreamingService);
    }
    businessStreamingService = new OkexBusinessStreamingService(getBusinessApiUrl(), exchangeSpecification);
    applyStreamingSpecification(exchangeSpecification, businessStreamingService);
    streamingMarketDataService =
        new OkexStreamingMarketDataService(streamingService, businessStreamingService, exchangeMetaData);
    streamingTradeService =
            new OkexStreamingTradeService(
                    privateStreamingService, exchangeMetaData, getResilienceRegistries());
    List<Completable> completableList = new ArrayList<>();
    completableList.add(streamingService.connect());
    completableList.add(businessStreamingService.connect());
    if (isApiKeyValid()) {
      completableList.add(privateStreamingService.connect());
    }
    return Completable.concat(completableList);
  }

  private boolean isApiKeyValid() {
    return exchangeSpecification.getApiKey() != null
            && !exchangeSpecification.getApiKey().isEmpty()
            && exchangeSpecification.getSecretKey() != null
            && !exchangeSpecification.getSecretKey().isEmpty();
  }

  private String getPublicApiUrl() {
    String apiUrl;
    ExchangeSpecification exchangeSpec = getExchangeSpecification();
    if (exchangeSpec.getOverrideWebsocketApiUri() != null) {
      return exchangeSpec.getOverrideWebsocketApiUri();
    }
    if (useSandbox()) {
      apiUrl = SANDBOX_WS_PUBLIC_CHANNEL_URI;
    } else {
      apiUrl = WS_PUBLIC_CHANNEL_URI;
    }
    return apiUrl;
  }

  private String getPrivateApiUrl() {
    String apiUrl;
    if (useSandbox()) {
      apiUrl = SANDBOX_WS_PRIVATE_CHANNEL_URI;
    } else {
      apiUrl = WS_PRIVATE_CHANNEL_URI;
    }
    return apiUrl;
  }

  private String getBusinessApiUrl() {
    String apiUrl;
    if (useSandbox()) {
      apiUrl = SANDBOX_WS_BUSINESS_CHANNEL_URI;
    } else {
      apiUrl = WS_BUSINESS_CHANNEL_URI;
    }
    return apiUrl;
  }

  @Override
  public Completable disconnect() {
    List<Completable> completableList = new ArrayList<>();
    if (streamingService != null) {
      streamingService.pingPongDisconnectIfConnected();
      completableList.add(streamingService.disconnect());
    }
    if (privateStreamingService != null) {
      privateStreamingService.pingPongDisconnectIfConnected();
      completableList.add(privateStreamingService.disconnect());
    }
    if (businessStreamingService != null) {
      businessStreamingService.pingPongDisconnectIfConnected();
      completableList.add(businessStreamingService.disconnect());
    }
    return Completable.concat(completableList);
  }

  @Override
  public boolean isAlive() {
    if (streamingService != null) {
      if (privateStreamingService != null) {
        return streamingService.isSocketOpen()
                && privateStreamingService.isSocketOpen()
                && privateStreamingService.isLoginDone();
      } else {
        return streamingService.isSocketOpen();
      }
    }
    return false;
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public OkexStreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    throw new NotYetImplementedForExchangeException("useCompressedMessage");
  }

  /**
   * Enables the user to listen on channel inactive events and react appropriately.
   *
   * @param channelInactiveHandler a WebSocketMessageHandler instance.
   */
  public void setChannelInactiveHandler(
          WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler) {
    streamingService.setChannelInactiveHandler(channelInactiveHandler);
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  @Override
  public Observable<ConnectionStateModel.State> connectionStateObservable() {
    return streamingService.subscribeConnectionState();
  }

  public Observable<State> connectionStateObservablePrivateChannel() {
    return privateStreamingService.subscribeConnectionState();
  }

  public Observable<State> connectionStateObservableBusinessChannel() {
    return businessStreamingService.subscribeConnectionState();
  }

  @Override
  public void resubscribeChannels() {
    streamingService.resubscribeChannels();
  }

  @Override
  public Observable<Object> connectionIdle() {
    return streamingService.subscribeIdle();
  }
}
