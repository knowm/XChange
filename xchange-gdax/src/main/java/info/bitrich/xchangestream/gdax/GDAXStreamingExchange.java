package info.bitrich.xchangestream.gdax;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.reactivex.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gdax.GDAXExchange;

/**
 * GDAX Streaming Exchange. Connects to live WebSocket feed.
 */
public class GDAXStreamingExchange extends GDAXExchange implements StreamingExchange {
  private static final String API_URI = "wss://ws-feed.gdax.com";
  
  private final GDAXStreamingService streamingService;
  private GDAXStreamingMarketDataService streamingMarketDataService;

  public GDAXStreamingExchange() {
    this.streamingService = new GDAXStreamingService(API_URI);
  }
 
  @Override
  protected void initServices() {
    super.initServices();
    streamingMarketDataService = new GDAXStreamingMarketDataService(streamingService);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    if (args == null || args.length == 0) throw new UnsupportedOperationException("The ProductSubscription must be defined!");
    streamingService.subscribeMultipleCurrencyPairs(args);

    return streamingService.connect();
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

  /**
   * Enables the user to listen on channel inactive events and react appropriately.
   * @param channelInactiveHandler a WebSocketMessageHandler instance.
   */
  public void setChannelInactiveHandler(WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler) {
    streamingService.setChannelInactiveHandler(channelInactiveHandler);
  }
}
