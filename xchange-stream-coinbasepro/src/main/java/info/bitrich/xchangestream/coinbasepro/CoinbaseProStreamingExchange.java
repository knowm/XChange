package info.bitrich.xchangestream.coinbasepro;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWebsocketAuthData;
import org.knowm.xchange.coinbasepro.service.CoinbaseProAccountServiceRaw;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

/** CoinbasePro Streaming Exchange. Connects to live WebSocket feed. */
public class CoinbaseProStreamingExchange extends CoinbaseProExchange implements StreamingExchange {
  private static final String API_URI = "wss://ws-feed.pro.coinbase.com";
  private static final String SANDBOX_API_URI = "wss://ws-feed-public.sandbox.pro.coinbase.com";
  private static final String PRIME_API_URI = "wss://ws-feed.prime.coinbase.com";
  private static final String PRIME_SANDBOX_API_URI =
      "wss://ws-feed-public.sandbox.prime.coinbase.com";

  private CoinbaseProStreamingService streamingService;
  private CoinbaseProStreamingMarketDataService streamingMarketDataService;
  private CoinbaseProStreamingTradeService streamingTradeService;

  public CoinbaseProStreamingExchange() {}

  @Override
  protected void initServices() {
    super.initServices();
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    if (args == null || args.length == 0)
      throw new UnsupportedOperationException("The ProductSubscription must be defined!");
    ExchangeSpecification exchangeSpec = getExchangeSpecification();
    boolean useSandbox =
        Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem("Use_Sandbox"));
    boolean usePrime =
        Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem("Use_Prime"));

    String apiUri;
    if (useSandbox) {
      apiUri = usePrime ? PRIME_SANDBOX_API_URI : SANDBOX_API_URI;
    } else {
      apiUri = usePrime ? PRIME_API_URI : API_URI;
    }

    this.streamingService = new CoinbaseProStreamingService(apiUri, () -> authData(exchangeSpec));
    applyStreamingSpecification(exchangeSpecification, this.streamingService);

    this.streamingMarketDataService = new CoinbaseProStreamingMarketDataService(streamingService);
    this.streamingTradeService = new CoinbaseProStreamingTradeService(streamingService);
    streamingService.subscribeMultipleCurrencyPairs(args);
    return streamingService.connect();
  }

  private CoinbaseProWebsocketAuthData authData(ExchangeSpecification exchangeSpec) {
    CoinbaseProWebsocketAuthData authData = null;
    if (exchangeSpec.getApiKey() != null) {
      try {
        CoinbaseProAccountServiceRaw rawAccountService =
            (CoinbaseProAccountServiceRaw) getAccountService();
        authData = rawAccountService.getWebsocketAuthData();
      } catch (Exception e) {
        logger.warn(
            "Failed attempting to acquire Websocket AuthData needed for private data on"
                + " websocket.  Will only receive public information via API",
            e);
      }
    }
    return authData;
  }

  @Override
  public Completable disconnect() {
    CoinbaseProStreamingService service = streamingService;
    streamingService = null;
    streamingMarketDataService = null;
    return service.disconnect();
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  @Override
  public Observable<Object> connectionSuccess() {
    return streamingService.subscribeConnectionSuccess();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = super.getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(false);

    return spec;
  }

  @Override
  public CoinbaseProStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public StreamingAccountService getStreamingAccountService() {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public CoinbaseProStreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  /**
   * Enables the user to listen on channel inactive events and react appropriately.
   *
   * @param channelInactiveHandler a WebSocketMessageHandler instance.
   */
  public void setChannelInactiveHandler(
      WebSocketClientHandler.WebSocketMessageHandler channelInactiveHandler) {
    streamingService.setChannelInactiveHandler(channelInactiveHandler);
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
