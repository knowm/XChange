package info.bitrich.xchangestream.coinjar;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinjar.CoinjarExchange;

public class CoinjarStreamingExchange extends CoinjarExchange implements StreamingExchange {

  private static final String API_URI = "wss://feed.exchange.coinjar.com/socket/websocket";

  private CoinjarStreamingService streamingService;
  private CoinjarStreamingMarketDataService streamingMarketDataService;

  @Override
  protected void initServices() {
    super.initServices();

    this.streamingService = createStreamingService();
    this.streamingMarketDataService = new CoinjarStreamingMarketDataService(streamingService);
  }

  private CoinjarStreamingService createStreamingService() {
    return new CoinjarStreamingService(API_URI, this.exchangeSpecification.getApiKey());
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    return streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    return streamingService.disconnect();
  }

  @Override
  public boolean isAlive() {
    return streamingService.isSocketOpen();
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
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = super.getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(false);
    return spec;
  }

  @Override
  public CoinjarStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
