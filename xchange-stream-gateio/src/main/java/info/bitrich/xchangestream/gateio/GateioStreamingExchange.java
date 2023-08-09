package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.gateio.GateioExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GateioStreamingExchange extends GateioExchange implements StreamingExchange {
  private static final Logger LOG = LoggerFactory.getLogger(GateioStreamingExchange.class);

  private final String V4_URL = "wss://api.gateio.ws/ws/v4/";

  private GateioStreamingService streamingService;
  private StreamingMarketDataService streamingMarketDataService;

  public GateioStreamingExchange() {}

  @Override
  public Completable connect(ProductSubscription... args) {
    streamingService = new GateioStreamingService(V4_URL, exchangeSpecification);
    applyStreamingSpecification(getExchangeSpecification(), streamingService);
    streamingMarketDataService = new GateioStreamingMarketDataService(streamingService);

    return streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    GateioStreamingService service = streamingService;
    streamingService = null;
    streamingMarketDataService = null;
    return service.disconnect();
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
