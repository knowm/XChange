package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.gateio.config.Config;
import io.reactivex.rxjava3.core.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gateio.GateioExchange;

public class GateioStreamingExchange extends GateioExchange implements StreamingExchange {

  private GateioStreamingService streamingService;
  private StreamingMarketDataService streamingMarketDataService;
  private StreamingTradeService streamingTradeService;
  private StreamingAccountService streamingAccountService;

  public GateioStreamingExchange() {}

  @Override
  public Completable connect(ProductSubscription... args) {
    streamingService =
        new GateioStreamingService(
            getApiUri(),
            exchangeSpecification.getApiKey(),
            exchangeSpecification.getSecretKey());
    applyStreamingSpecification(exchangeSpecification, streamingService);
    streamingMarketDataService = new GateioStreamingMarketDataService(streamingService);
    streamingTradeService = new GateioStreamingTradeService(streamingService);
    streamingAccountService = new GateioStreamingAccountService(streamingService);

    return streamingService.connect();
  }

  protected String getApiUri() {
    String overrideWebsocketApiUri = exchangeSpecification.getOverrideWebsocketApiUri();
    if (overrideWebsocketApiUri != null) return overrideWebsocketApiUri;
    if (isSandbox(exchangeSpecification)) {
      return Config.SANDBOX_V4_URL;
    }
    return Config.V4_URL;
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
    specification.setShouldLoadRemoteMetaData(false);
    specification.setOverrideWebsocketApiUri(Config.V4_URL);
    return specification;
  }
}
