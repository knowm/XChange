package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.*;
import io.reactivex.rxjava3.core.Completable;
import lombok.Getter;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;

@Getter
public class KrakenStreamingExchange extends BaseExchange implements StreamingExchange {

  private KrakenStreamingService krakenStreamingService;
  private KrakenPrivateStreamingService krakenPrivateStreamingService;
  private StreamingMarketDataService streamingMarketDataService;
  private StreamingTradeService streamingTradeService;
  private StreamingAccountService streamingAccountService;

  @Override
  public Completable connect(ProductSubscription... args) {
    krakenStreamingService =
        new KrakenStreamingService(exchangeSpecification.getOverrideWebsocketApiUri());
    krakenPrivateStreamingService =
        new KrakenPrivateStreamingService(
            (String) exchangeSpecification.getParameter("V2_PRIVATE_WS_URL"), this);

    streamingTradeService = new KrakenStreamingTradeService(krakenPrivateStreamingService);
    streamingAccountService = new KrakenStreamingAccountService(krakenPrivateStreamingService);

    applyStreamingSpecification(exchangeSpecification, krakenStreamingService);

    streamingMarketDataService = new KrakenStreamingMarketDataService(krakenStreamingService);

    krakenPrivateStreamingService.connect().blockingAwait();

    return krakenStreamingService.connect();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    var specification = new ExchangeSpecification(getClass());
    specification.setExchangeName("Kraken");
    specification.setSslUri("https://api.kraken.com");
    specification.setOverrideWebsocketApiUri("wss://ws.kraken.com/v2");
    specification.setExchangeSpecificParametersItem(
        "V2_PRIVATE_WS_URL", "wss://ws-auth.kraken.com/v2");
    specification.setShouldLoadRemoteMetaData(false);
    return specification;
  }

  @Override
  public Completable disconnect() {
    KrakenStreamingService service = krakenStreamingService;
    krakenStreamingService = null;
    streamingMarketDataService = null;
    streamingTradeService = null;
    streamingAccountService = null;
    return service.disconnect();
  }

  @Override
  public boolean isAlive() {
    return krakenStreamingService != null && krakenStreamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    krakenStreamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  protected void initServices() {}
}
