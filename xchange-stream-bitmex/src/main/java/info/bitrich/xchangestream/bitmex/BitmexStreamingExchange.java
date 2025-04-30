package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.currency.CurrencyPair;

@Getter
public class BitmexStreamingExchange extends BitmexExchange implements StreamingExchange {
  private static final String API_URI = "wss://www.bitmex.com/realtime";
  private static final String TESTNET_API_URI = "wss://testnet.bitmex.com/realtime";

  private BitmexStreamingService streamingService;
  private BitmexStreamingMarketDataService streamingMarketDataService;
  private BitmexStreamingTradeService streamingTradeService;

  @Override
  protected void initServices() {
    super.initServices();
    streamingService = createStreamingService();
    streamingMarketDataService = new BitmexStreamingMarketDataService(streamingService);
    streamingTradeService = new BitmexStreamingTradeService(streamingService);
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
  public boolean isAlive() {
    return streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public Observable<Long> messageDelay() {
    return Observable.create(
        delayEmitter -> {
          streamingService.addDelayEmitter(delayEmitter);
        });
  }

  @Override
  public void remoteInit() {
    super.remoteInit();

    // adapt spot mappings by removing '_' symbol
    BitmexAdapters.SYMBOL_TO_INSTRUMENT.entrySet().stream()
        .filter(entry -> entry.getValue() instanceof CurrencyPair)
        .forEach(
            entry ->
                BitmexStreamingAdapters.putSymbolMapping(
                    StringUtils.remove(entry.getKey(), "_"), (CurrencyPair) entry.getValue()));
  }

  @Override
  public void resubscribeChannels() {
    streamingService.resubscribeChannels();
  }
}
