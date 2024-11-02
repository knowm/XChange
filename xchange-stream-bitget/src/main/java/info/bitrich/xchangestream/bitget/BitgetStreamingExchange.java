package info.bitrich.xchangestream.bitget;

import info.bitrich.xchangestream.bitget.config.Config;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Completable;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitget.BitgetExchange;

@Getter
public class BitgetStreamingExchange extends BitgetExchange implements StreamingExchange {

  private BitgetStreamingService publicStreamingService;
  private BitgetPrivateStreamingService privateStreamingService;
  private StreamingMarketDataService streamingMarketDataService;
  private StreamingTradeService streamingTradeService;
  private StreamingAccountService streamingAccountService;

  @Override
  public Completable connect(ProductSubscription... args) {
    publicStreamingService = new BitgetStreamingService(Config.V2_PUBLIC_WS_URL);
    if (StringUtils.isNoneBlank(
        exchangeSpecification.getApiKey(),
        exchangeSpecification.getSecretKey(),
        exchangeSpecification.getPassword())) {
      privateStreamingService =
          new BitgetPrivateStreamingService(
              Config.V2_PRIVATE_WS_URL,
              exchangeSpecification.getApiKey(),
              exchangeSpecification.getSecretKey(),
              exchangeSpecification.getPassword());
      streamingTradeService = new BitgetStreamingTradeService(privateStreamingService);
      privateStreamingService.connect().blockingAwait();
    }
    applyStreamingSpecification(exchangeSpecification, publicStreamingService);
    streamingMarketDataService = new BitgetStreamingMarketDataService(publicStreamingService);

    return publicStreamingService.connect();
  }

  @Override
  public Completable disconnect() {
    BitgetStreamingService service = publicStreamingService;
    publicStreamingService = null;
    streamingMarketDataService = null;
    streamingTradeService = null;
    streamingAccountService = null;
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
