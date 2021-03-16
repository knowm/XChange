package info.bitrich.xchangestream.bankera;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bankera.BankeraExchange;
import org.knowm.xchange.bankera.service.BankeraMarketDataService;

public class BankeraStreamingExchange extends BankeraExchange implements StreamingExchange {

  private static final String WS_URI = "wss://api-exchange.bankera.com/ws";
  private final BankeraStreamingService streamingService;
  private BankeraStreamingMarketDataService streamingMarketDataService;

  public BankeraStreamingExchange() {
    this.streamingService = new BankeraStreamingService(WS_URI);
  }

  @Override
  protected void initServices() {
    super.initServices();
    streamingMarketDataService =
        new BankeraStreamingMarketDataService(
            streamingService, (BankeraMarketDataService) marketDataService);
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
