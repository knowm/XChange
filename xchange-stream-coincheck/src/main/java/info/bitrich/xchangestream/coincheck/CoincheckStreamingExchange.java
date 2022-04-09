package info.bitrich.xchangestream.coincheck;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import info.bitrich.xchangestream.util.Events;
import io.reactivex.Completable;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.coincheck.CoincheckExchange;

@Slf4j
public class CoincheckStreamingExchange extends CoincheckExchange implements StreamingExchange {

  private static final String WS_API_URI = "wss://ws-api.coincheck.com/";
  private CoincheckStreamingService streamingService;
  private CoincheckStreamingMarketDataService streamingMarketDataService;
  private Runnable onApiCall;

  @Override
  protected void initServices() {
    super.initServices();
    this.streamingService = new CoincheckStreamingService(WS_API_URI);
    this.streamingMarketDataService =
        new CoincheckStreamingMarketDataService(this.streamingService, this.onApiCall);
    this.onApiCall = Events.onApiCall(exchangeSpecification);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    return this.streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    Completable completable = streamingService.disconnect();
    streamingService = null;
    streamingMarketDataService = null;
    return completable;
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
  public CoincheckStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
