package info.bitrich.xchangestream.kucoin;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import info.bitrich.xchangestream.util.Events;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.kucoin.dto.response.WebsocketResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KucoinStreamingExchange extends KucoinExchange implements StreamingExchange {

  private KucoinStreamingService publicStreamingService;
  private KucoinStreamingService privateStreamingService;
  private KucoinStreamingMarketDataService streamingMarketDataService;
  private KucoinStreamingTradeService streamingTradeService;

  private final List<NettyStreamingService<?>> services = new ArrayList<>();
  private Runnable onApiCall;

  @Override
  protected void initServices() {
    super.initServices();
    this.onApiCall = Events.onApiCall(exchangeSpecification);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    ProductSubscription subscriptions = args[0];

    Completable complete = Completable.complete();
    services.clear();

    if (subscriptions.hasUnauthenticated()) {
      complete = complete.doOnComplete(() -> {
        WebsocketResponse connectionDetails = getPublicWebsocketConnectionDetails();
        WebsocketResponse.InstanceServer instanceServer = connectionDetails.getInstanceServers().get(0);
        String url = instanceServer.getEndpoint() + "?token=" + connectionDetails.getToken();

        publicStreamingService = new KucoinStreamingService(url, instanceServer.getPingInterval(), false);
        applyStreamingSpecification(getExchangeSpecification(), publicStreamingService);
        publicStreamingService.connect().doOnError(ex -> logger.warn("encountered error while subscribing to public websocket", ex)).blockingAwait();

        services.add(publicStreamingService);
        streamingMarketDataService = new KucoinStreamingMarketDataService(publicStreamingService, getMarketDataService(), onApiCall);
      });
    }

    if (subscriptions.hasAuthenticated()) {
      if (exchangeSpecification.getApiKey() == null) {
        throw new IllegalArgumentException("API key required for authenticated streams");
      }

      complete = complete.doOnComplete(() -> {
        WebsocketResponse connectionDetails = getPrivateWebsocketConnectionDetails();
        WebsocketResponse.InstanceServer instanceServer = connectionDetails.getInstanceServers().get(0);
        String url = instanceServer.getEndpoint() + "?token=" + connectionDetails.getToken();

        privateStreamingService = new KucoinStreamingService(url, instanceServer.getPingInterval(), true);
        applyStreamingSpecification(getExchangeSpecification(), privateStreamingService);
        privateStreamingService.connect().doOnError(ex -> logger.warn("encountered error while subscribing to private websocket", ex)).blockingAwait();

        services.add(privateStreamingService);
        streamingTradeService = new KucoinStreamingTradeService(privateStreamingService);
      });
    }

    return complete;
  }

  @Override
  public Completable disconnect() {

    if (publicStreamingService != null) {
      publicStreamingService = null;
      streamingMarketDataService = null;
    }

    if (privateStreamingService != null) {
      privateStreamingService = null;
    }

    List<Completable> completables = services.stream().map(NettyStreamingService::disconnect).collect(Collectors.toList());
    services.clear();
    return Completable.concat(completables);
  }

  @Override
  public boolean isAlive() {
    return services.stream().anyMatch(NettyStreamingService::isSocketOpen);
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return Observable.concat(services.stream().map(NettyStreamingService::subscribeReconnectFailure).collect(Collectors.toList()));
  }

  @Override
  public Observable<Object> connectionSuccess() {
    return Observable.concat(services.stream().map(NettyStreamingService::subscribeConnectionSuccess).collect(Collectors.toList()));
  }

  @Override
  public KucoinStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public StreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    services.forEach(s -> s.useCompressedMessages(compressedMessages));
  }
}
