package info.bitrich.xchangestream.deribit;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.deribit.config.Config;
import io.reactivex.rxjava3.core.Completable;
import lombok.Getter;
import org.knowm.xchange.deribit.v2.DeribitExchange;

@Getter
public class DeribitStreamingExchange extends DeribitExchange implements StreamingExchange {

  private DeribitStreamingService publicStreamingService;
  private DeribitPrivateStreamingService privateStreamingService;

  private StreamingMarketDataService streamingMarketDataService;
  private StreamingTradeService streamingTradeService;

  @Override
  public Completable connect(ProductSubscription... args) {
    publicStreamingService = new DeribitStreamingService(Config.V2_WS_URL);

    privateStreamingService =
        new DeribitPrivateStreamingService(
            Config.V2_WS_URL,
            exchangeSpecification.getApiKey(),
            exchangeSpecification.getSecretKey());
    privateStreamingService.connect().blockingAwait();

    applyStreamingSpecification(exchangeSpecification, publicStreamingService);

    streamingMarketDataService = new DeribitStreamingMarketDataService(publicStreamingService);
    streamingTradeService = new DeribitStreamingTradeService(privateStreamingService);

    return publicStreamingService.connect();
  }

  @Override
  public Completable disconnect() {
    DeribitStreamingService service = publicStreamingService;
    publicStreamingService = null;
    streamingMarketDataService = null;
    streamingTradeService = null;
    return service.disconnect();
  }

  @Override
  public boolean isAlive() {
    return publicStreamingService != null && publicStreamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    publicStreamingService.useCompressedMessages(compressedMessages);
  }
}
