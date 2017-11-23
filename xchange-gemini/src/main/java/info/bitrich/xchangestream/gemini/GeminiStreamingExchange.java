package info.bitrich.xchangestream.gemini;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import org.knowm.xchange.gemini.v1.GeminiExchange;

/**
 * Created by Lukas Zaoralek on 15.11.17.
 */
public class GeminiStreamingExchange extends GeminiExchange implements StreamingExchange {
  private static final String API_BASE_URI = "wss://api.gemini.com/v1/marketdata/";

  private final GeminiStreamingService streamingService;
  private GeminiStreamingMarketDataService streamingMarketDataService;

  public GeminiStreamingExchange() {
    this.streamingService = new GeminiStreamingService(API_BASE_URI);
  }

  @Override
  protected void initServices() {
    super.initServices();
    streamingMarketDataService = new GeminiStreamingMarketDataService(streamingService);
  }

  @Override
  public Completable connect() {
    return Completable.create(CompletableEmitter::onComplete);
  }

  @Override
  public Completable disconnect() {
    return Completable.create(CompletableEmitter::onComplete);
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }
}
