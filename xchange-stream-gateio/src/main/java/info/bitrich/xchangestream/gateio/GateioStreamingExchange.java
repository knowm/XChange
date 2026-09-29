package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.*;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.service.GateioMarketDataService;

import java.util.ArrayList;
import java.util.List;

public class GateioStreamingExchange extends GateioExchange implements StreamingExchange {

  private GateioStreamingService streamingService;
  private StreamingMarketDataService streamingMarketDataService;
  private StreamingTradeService streamingTradeService;
  private StreamingAccountService streamingAccountService;
  private GateioUserTradeStreamingService userTradeStreamingService;

  public GateioStreamingExchange() {
  }

  @Override
  protected void initServices() {
    super.initServices();
    if (isFuturesEnabled())
      exchangeSpecification.setSslUri(Config.V4_FUTURES_URL);
    else
      exchangeSpecification.setSslUri(Config.V4_URL);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    applyWebsocketTimeouts(exchangeSpecification);
    streamingService =
        new GateioStreamingService(
            exchangeSpecification.getSslUri(),
            exchangeSpecification.getApiKey(),
            exchangeSpecification.getSecretKey(), exchangeSpecification, isFuturesEnabled());
    applyStreamingSpecification(exchangeSpecification, streamingService);
    if (isApiKeyValid()) {
      userTradeStreamingService =
          new GateioUserTradeStreamingService(exchangeSpecification.getSslUri(), exchangeSpecification.getApiKey(),
              exchangeSpecification.getSecretKey(), exchangeSpecification);
      applyStreamingSpecification(exchangeSpecification, userTradeStreamingService);
    }
    streamingMarketDataService = new GateioStreamingMarketDataService(streamingService, exchangeMetaData,
        (GateioMarketDataService) marketDataService, this);
    streamingTradeService = new GateioStreamingTradeService(streamingService, exchangeMetaData);
    streamingAccountService = new GateioStreamingAccountService(streamingService);
    List<Completable> completableList = new ArrayList<>();
    completableList.add(streamingService.connect());
    if (isApiKeyValid()) {
      completableList.add(userTradeStreamingService.connect());
    }
    return Completable.concat(completableList);
  }

  private boolean isApiKeyValid() {
    return exchangeSpecification.getApiKey() != null
        && !exchangeSpecification.getApiKey().isEmpty()
        && exchangeSpecification.getSecretKey() != null
        && !exchangeSpecification.getSecretKey().isEmpty();
  }

  @Override
  public Completable disconnect() {
    GateioStreamingService service = streamingService;
    streamingService = null;
    streamingMarketDataService = null;
    streamingTradeService = null;
    streamingAccountService = null;
    return service.disconnect();
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public StreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  @Override
  public StreamingAccountService getStreamingAccountService() {
    return streamingAccountService;
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification = super.getDefaultExchangeSpecification();
    specification.setShouldLoadRemoteMetaData(true);

    return specification;
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  @Override
  public Observable<ConnectionStateModel.State> connectionStateObservable() {
    return streamingService.subscribeConnectionState();
  }

  @Override
  public Observable<Object> connectionIdle() {
    return streamingService.subscribeIdle();
  }
}
