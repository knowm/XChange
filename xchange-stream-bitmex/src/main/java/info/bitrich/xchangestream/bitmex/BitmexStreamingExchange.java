package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.BitmexExchange;

/** Created by Lukas Zaoralek on 12.11.17. */
public class BitmexStreamingExchange extends BitmexExchange implements StreamingExchange {
  private static final String API_URI = "wss://www.bitmex.com/realtime";
  private static final String TESTNET_API_URI = "wss://testnet.bitmex.com/realtime";

  private BitmexStreamingService streamingService;
  private BitmexStreamingMarketDataService streamingMarketDataService;

  public BitmexStreamingExchange() {}

  @Override
  protected void initServices() {
    super.initServices();
    streamingService = createStreamingService();
    streamingMarketDataService = new BitmexStreamingMarketDataService(streamingService, this);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    return streamingService.connect();
  }

  private BitmexStreamingService createStreamingService() {
    ExchangeSpecification exchangeSpec = getExchangeSpecification();
    Boolean useSandbox = (Boolean) exchangeSpec.getExchangeSpecificParametersItem(USE_SANDBOX);
    String uri = useSandbox == null || !useSandbox ? API_URI : TESTNET_API_URI;
    BitmexStreamingService streamingService =
        new BitmexStreamingService(uri, exchangeSpec.getApiKey(), exchangeSpec.getSecretKey());
    applyStreamingSpecification(exchangeSpec, streamingService);
    return streamingService;
  }

  @Override
  public Completable disconnect() {
    return streamingService.disconnect();
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
  public boolean isAlive() {
    return streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public Flowable<Long> messageDelay() {
    return Flowable.create(
        delayEmitter -> {
          streamingService.addDelayEmitter(delayEmitter);
        }, BackpressureStrategy.LATEST);
  }

  @Override
  public void resubscribeChannels() {
    streamingService.resubscribeChannels();
  }

  public BitmexStreamingService getStreamingService() {
    return streamingService;
  }
}
