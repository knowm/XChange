package info.bitrich.xchangestream.bitso;

import info.bitrich.xchangestream.bitso.dto.BitsoWebsocketAuthData;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitso.BitsoExchange;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.util.Date;

/** CoinbasePro Streaming Exchange. Connects to live WebSocket feed. */
public class BitsoStreamingExchange extends BitsoExchange implements StreamingExchange {
  private static final String API_URI = "wss://ws.bitso.com";
  private static final String SANDBOX_API_URI = "wss://ws.bitso.com";
  private static final String PRIME_API_URI = "wss://ws.bitso.com";
  private static final String PRIME_SANDBOX_API_URI =
      "wss://ws.bitso.com";

  private BitsoStreamingService streamingService;
  private BitsoStreamingMarketDataService streamingMarketDataService;
  private BitsoStreamingTradeService streamingTradeService;

  public BitsoStreamingExchange() {}

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

    boolean subscribeToL3Orderbook =
        Boolean.TRUE.equals(
            exchangeSpecification.getExchangeSpecificParametersItem(
                StreamingExchange.L3_ORDERBOOK));
    BitsoWebsocketAuthData bitsoWebsocketAuthData=new BitsoWebsocketAuthData("","","","",new Date().getTime());
    this.streamingService =
        new BitsoStreamingService(apiUri,subscribeToL3Orderbook);
    applyStreamingSpecification(exchangeSpecification, this.streamingService);

    this.streamingMarketDataService = new BitsoStreamingMarketDataService(streamingService);
    this.streamingTradeService = new BitsoStreamingTradeService(streamingService);
    streamingService.subscribeMultipleCurrencyPairs(args);
    return streamingService.connect();
  }

//  private BitsoWebsocketAuthData authData(ExchangeSpecification exchangeSpec) {
//    BitsoWebsocketAuthData authData = null;
//    if (exchangeSpec.getApiKey() != null) {
//      try {
//        BitsoAccountServiceRaw rawAccountService =
//            (BitsoAccountServiceRaw) getAccountService();
//        authData = rawAccountService.getWebsocketAuthData();
//      } catch (Exception e) {
//        logger.warn(
//            "Failed attempting to acquire Websocket AuthData needed for private data on"
//                + " websocket.  Will only receive public information via API",
//            e);
//      }
//    }
//    return authData;
//  }

  @Override
  public Completable disconnect() {
    BitsoStreamingService service = streamingService;
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
  public Observable<State> connectionStateObservable() {
    return streamingService.subscribeConnectionState();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = super.getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(false);

    return spec;
  }

  @Override
  public BitsoStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public StreamingAccountService getStreamingAccountService() {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public BitsoStreamingTradeService getStreamingTradeService() {
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
