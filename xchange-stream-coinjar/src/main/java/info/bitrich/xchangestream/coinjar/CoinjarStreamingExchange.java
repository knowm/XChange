package info.bitrich.xchangestream.coinjar;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import org.knowm.xchange.coinjar.CoinjarExchange;

public class CoinjarStreamingExchange extends CoinjarExchange implements StreamingExchange {

  private static final String API_URI = "wss://feed.exchange.coinjar.com/socket/websocket";

  private CoinjarStreamingService streamingService;
  private CoinjarStreamingMarketDataService streamingMarketDataService;
  private CoinjarStreamingTradeService streamingTradeService;

  @Override
  protected void initServices() {
    super.initServices();

    this.streamingService =
        new CoinjarStreamingService(API_URI, this.exchangeSpecification.getApiKey());
    this.streamingMarketDataService = new CoinjarStreamingMarketDataService(streamingService);
    this.streamingTradeService = new CoinjarStreamingTradeService(streamingService);
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
  public Flowable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  @Override
  public Flowable<Object> connectionSuccess() {
    return streamingService.subscribeConnectionSuccess();
  }

  @Override
  public CoinjarStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public StreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
