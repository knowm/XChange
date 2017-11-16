package info.bitrich.xchangestream.bitflyer;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.pubnub.PubnubStreamingService;
import io.reactivex.Completable;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Created by Lukas Zaoralek on 14.11.17.
 */
public class BitflyerStreamingExchange extends BaseExchange implements StreamingExchange {
  private static final String API_KEY = "sub-c-52a9ab50-291b-11e5-baaa-0619f8945a4f";

  private final PubnubStreamingService streamingService;
  private BitflyerStreamingMarketDataService streamingMarketDataService;

  public BitflyerStreamingExchange() {
    this.streamingService = new PubnubStreamingService(API_KEY);
  }

  @Override
  protected void initServices() {
    streamingMarketDataService = new BitflyerStreamingMarketDataService(streamingService);
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
    ExchangeSpecification spec = new ExchangeSpecification("Bitflyer");
    spec.setShouldLoadRemoteMetaData(false);
    return spec;
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }
}

