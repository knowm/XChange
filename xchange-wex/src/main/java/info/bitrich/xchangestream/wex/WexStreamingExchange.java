package info.bitrich.xchangestream.wex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import io.reactivex.Completable;
import org.knowm.xchange.btce.v3.BTCEExchange;

/**
 * Created by Lukas Zaoralek on 16.11.17.
 */
public class WexStreamingExchange extends BTCEExchange implements StreamingExchange {
  private static final String API_KEY = "ee987526a24ba107824c";
  private static final String APP_CLUSTER = "eu";
  private final PusherStreamingService streamingService;

  private WexStreamingMarketDataService streamingMarketDataService;

  public WexStreamingExchange() {
    streamingService = new PusherStreamingService(API_KEY, APP_CLUSTER);
  }

  @Override
  protected void initServices() {
    super.initServices();
    streamingMarketDataService = new WexStreamingMarketDataService(streamingService, marketDataService);
  }

  @Override
  public Completable connect() {
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
}
