package info.bitrich.xchangestream.huobi;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import org.knowm.xchange.huobi.HuobiExchange;

public class HuobiStreamingExchange extends HuobiExchange implements StreamingExchange {

  private static final String API_BASE_URI = "wss://api.huobi.pro/ws";
  private static final String API_URI_AWS = "wss://api-aws.huobi.pro/ws";

  private HuobiStreamingService streamingService;
  private HuobiStreamingMarketDataService streamingMarketDataService;

  @Override
  protected void initServices() {
    super.initServices();
    Boolean aws =
        (Boolean)
            getExchangeSpecification()
                .getExchangeSpecificParameters()
                .getOrDefault("AWS", Boolean.FALSE);
    this.streamingService = new HuobiStreamingService(aws ? API_URI_AWS : API_BASE_URI);
    this.streamingService.useCompressedMessages(true);
    streamingMarketDataService = new HuobiStreamingMarketDataService(streamingService);
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
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
