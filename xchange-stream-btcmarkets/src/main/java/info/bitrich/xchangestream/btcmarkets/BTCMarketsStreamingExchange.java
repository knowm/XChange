package info.bitrich.xchangestream.btcmarkets;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;

public class BTCMarketsStreamingExchange extends BTCMarketsExchange implements StreamingExchange {

  private static final String API_URI = "wss://socket.btcmarkets.net/v2";

  private BTCMarketsStreamingService streamingService;
  private BTCMarketsStreamingMarketDataService streamingMarketDataService;

  @Override
  protected void initServices() {
    super.initServices();

    this.streamingService = createStreamingService();
    this.streamingMarketDataService = new BTCMarketsStreamingMarketDataService(streamingService);
  }

  private BTCMarketsStreamingService createStreamingService() {
    return new BTCMarketsStreamingService(API_URI);
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
  public Flowable<State> connectionStateFlowable() {
    return streamingService.subscribeConnectionState();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = super.getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(false);
    return spec;
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
