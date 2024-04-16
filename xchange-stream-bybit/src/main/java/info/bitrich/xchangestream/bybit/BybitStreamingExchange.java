package info.bitrich.xchangestream.bybit;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.Completable;
import org.knowm.xchange.bybit.BybitExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamingExchange extends BybitExchange implements StreamingExchange {

  private final Logger LOG = LoggerFactory.getLogger(BybitStreamingExchange.class);

  //https://bybit-exchange.github.io/docs/v5/ws/connect
  public static final String URI = "wss://stream.bybit.com/v5/public";
  public static final String TESTNET_URI = "wss://stream-testnet.bybit.com/v5/public";
  public static final String AUTH_URI = "wss://stream.bybit.com/v5/private";
  public static final String TESTNET_AUTH_URI = "wss://stream-testnet.bybit.com/v5/private";

  //spot, linear, inverse or option
  public static final String EXCHANGE_TYPE = "EXCHANGE_TYPE";

  private BybitStreamingService streamingService;
  private BybitStreamingMarketDataService streamingMarketDataService;

  @Override
  protected void initServices() {
    super.initServices();
    this.streamingService = new BybitStreamingService(getApiUrl(),
        exchangeSpecification.getExchangeSpecificParametersItem(EXCHANGE_TYPE));
    this.streamingMarketDataService = new BybitStreamingMarketDataService(streamingService);
  }

  private String getApiUrl() {
    String apiUrl = null;
    if (exchangeSpecification.getApiKey() == null) {
      if (Boolean.TRUE.equals(
          exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
        apiUrl = TESTNET_URI;
      } else {
        apiUrl = URI;
      }
      apiUrl += "/" + exchangeSpecification.getExchangeSpecificParametersItem(EXCHANGE_TYPE);
    }
// TODO auth
    return apiUrl;
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    LOG.info("Connect to BybitStream");
    return streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    streamingService.pingPongDisconnectIfConnected();
    return streamingService.disconnect();
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public BybitStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

}
