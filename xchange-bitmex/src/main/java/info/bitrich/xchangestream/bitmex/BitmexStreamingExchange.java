package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Created by Lukas Zaoralek on 12.11.17.
 */
public class BitmexStreamingExchange extends BaseExchange implements StreamingExchange {
  private static final String API_URI = "wss://www.bitmex.com/realtime";

  private final BitmexStreamingService streamingService;
  private BitmexStreamingMarketDataService streamingMarketDataService;

  public BitmexStreamingExchange() {
    this.streamingService = new BitmexStreamingService(API_URI);
  }

  @Override
  protected void initServices() {
    streamingMarketDataService = new BitmexStreamingMarketDataService(streamingService);
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
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return null;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification("Bitmex");
    spec.setShouldLoadRemoteMetaData(false);
    return spec;
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }
}
