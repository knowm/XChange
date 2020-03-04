package org.knowm.xchange.stream.bitstamp;

import static org.knowm.xchange.stream.service.ConnectableService.BEFORE_CONNECTION_HANDLER;

import org.knowm.xchange.stream.core.ProductSubscription;
import org.knowm.xchange.stream.core.StreamingExchange;
import org.knowm.xchange.stream.core.StreamingMarketDataService;
import org.knowm.xchange.stream.service.pusher.PusherStreamingService;
import io.reactivex.Completable;
import org.knowm.xchange.bitstamp.BitstampExchange;

/**
 * This Bitstamp Streaming WebSocket V1 implementation.<br>
 * From official site:<br>
 *
 * <p>THIS IS AN OLD IMPLEMENTATION OF WEBSOCKET AND WILL BE DISCONTINUED IN Q2 2019.<br>
 * PLEASE USE THE NEW WEBSOCKET IMPLEMENTATION HERE: HTTPS://WWW.BITSTAMP.NET/WEBSOCKET/V2<br>
 * See <a href="https://www.bitstamp.net/websocket/v1-legacy/">Bitstamp WebSocket V1</a>
 *
 * @deprecated Use {@link org.knowm.xchange.stream.bitstamp.v2.BitstampStreamingExchange} instead.
 */
@Deprecated
public class BitstampStreamingExchange extends BitstampExchange implements StreamingExchange {
  private static final String API_KEY = "de504dc5763aeef9ff52";
  private final PusherStreamingService streamingService;

  private BitstampStreamingMarketDataService streamingMarketDataService;

  public BitstampStreamingExchange() {
    streamingService = new PusherStreamingService(API_KEY);
  }

  @Override
  protected void initServices() {
    super.initServices();
    streamingService.setBeforeConnectionHandler(
        (Runnable)
            getExchangeSpecification()
                .getExchangeSpecificParametersItem(BEFORE_CONNECTION_HANDLER));
    streamingMarketDataService = new BitstampStreamingMarketDataService(streamingService);
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
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public boolean isAlive() {
    return this.streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
