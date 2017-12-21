package info.bitrich.xchangestream.binance;

import org.knowm.xchange.binance.BinanceExchange;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;

public class BinanceStreamingExchange extends BinanceExchange implements StreamingExchange {
    private static final String API_BASE_URI = "wss://stream.binance.com:9443/ws/";

    private final BinanceStreamingService streamingService;
    private BinanceStreamingMarketDataService streamingMarketDataService;

    public BinanceStreamingExchange() {
      this.streamingService = new BinanceStreamingService(API_BASE_URI);
    }

    @Override
    protected void initServices() {
      super.initServices();
      streamingMarketDataService = new BinanceStreamingMarketDataService(streamingService);
    }

    @Override
    public Completable connect(ProductSubscription... args) {
      return Completable.create(CompletableEmitter::onComplete);
    }

    @Override
    public Completable disconnect() {
      return Completable.create(CompletableEmitter::onComplete);
    }

    @Override
    public boolean isAlive() {
        throw new IllegalStateException("Not implemented.");
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
      return streamingMarketDataService;
    }
  }

