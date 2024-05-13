package info.bitrich.xchangestream.bybit;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.Completable;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamingExchange extends BybitExchange implements StreamingExchange {

  private final Logger LOG = LoggerFactory.getLogger(BybitStreamingExchange.class);

  //https://bybit-exchange.github.io/docs/v5/ws/connect
  public static final String URI = "wss://stream.bybit.com/v5/public";
  public static final String TESTNET_URI = "wss://stream-testnet.bybit.com/v5/public";
  public static final String AUTH_URI = "wss://stream.bybit.com/v5/private";
  // stream-testnet.bybit.com - don't accept api key
  public static final String TESTNET_AUTH_URI = "wss://stream-demo.bybit.com/v5/private";

  //spot, linear, inverse or option
  public static final String EXCHANGE_TYPE = "Exchange_Type";

  private BybitStreamingService streamingService;
  private BybitStreamingMarketDataService streamingMarketDataService;
  private BybitStreamingTradeService streamingTradeService;

  @Override
  protected void initServices() {
    super.initServices();
    this.streamingService = new BybitStreamingService(getApiUrl(), exchangeSpecification);
    this.streamingMarketDataService = new BybitStreamingMarketDataService(streamingService);
    this.streamingTradeService = new BybitStreamingTradeService(streamingService);
  }

  private String getApiUrl() {
    String apiUrl;
    if (exchangeSpecification.getApiKey() == null) {
      if (Boolean.TRUE.equals(
          exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
        apiUrl = TESTNET_URI;
      } else {
        apiUrl = URI;
      }
      apiUrl += "/" + ((BybitCategory)exchangeSpecification.getExchangeSpecificParametersItem(EXCHANGE_TYPE)).getValue();
    } else {
      if (Boolean.TRUE.equals(
          exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
        apiUrl = TESTNET_AUTH_URI;
      } else {
        apiUrl = AUTH_URI;
      }
    }
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

  @Override
  public BybitStreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

}
