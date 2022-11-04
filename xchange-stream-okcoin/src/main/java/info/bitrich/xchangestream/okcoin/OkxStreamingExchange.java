package info.bitrich.xchangestream.okcoin;

import static org.knowm.xchange.okex.v5.service.OkexMarketDataService.SPOT;
import static org.knowm.xchange.okex.v5.service.OkexMarketDataService.SWAP;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.reactivex.Completable;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okex.v5.OkexAdapters;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.v5.dto.meta.OkexExchangeInfo;
import org.knowm.xchange.okex.v5.service.OkexAccountService;
import org.knowm.xchange.okex.v5.service.OkexMarketDataServiceRaw;

public class OkxStreamingExchange extends OkexExchange implements StreamingExchange {
  // Production URIs
  public static final String WS_PUBLIC_CHANNEL_URI = "wss://ws.okx.com:8443/ws/v5/public";
  public static final String WS_PRIVATE_CHANNEL_URI = "wss://ws.okx.com:8443/ws/v5/private";

  public static final String AWS_WS_PUBLIC_CHANNEL_URI = "wss://wsaws.okx.com:8443/ws/v5/public";
  public static final String AWS_WS_PRIVATE_CHANNEL_URI = "wss://wsaws.okx.com:8443/ws/v5/private";

  // Demo(Sandbox) URIs
  public static final String SANDBOX_WS_PUBLIC_CHANNEL_URI =
      "wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999";
  public static final String SANDBOX_WS_PRIVATE_CHANNEL_URI =
      "wss://wspap.okx.com:8443/ws/v5/private?brokerId=9999";

  private OkxStreamingService streamingService;
  protected OkexExchangeInfo exchangeInfo;
  private OkxStreamingMarketDataService streamingMarketDataService;

  public OkxStreamingExchange() {}

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
  public void remoteInit() throws IOException {
    List<OkexInstrument> instruments =
        ((OkexMarketDataServiceRaw) marketDataService)
            .getOkexInstruments(SWAP, null, null)
            .getData();
    instruments.addAll(
        ((OkexMarketDataServiceRaw) marketDataService)
            .getOkexInstruments(SPOT, null, null)
            .getData());

    // Currency data and trade fee is only retrievable through a private endpoint
    List<OkexCurrency> currencies = null;
    List<OkexTradeFee> tradeFee = null;
    if (exchangeSpecification.getApiKey() != null
        && exchangeSpecification.getSecretKey() != null
        && exchangeSpecification.getExchangeSpecificParametersItem("passphrase") != null) {
      currencies = ((OkexMarketDataServiceRaw) marketDataService).getOkexCurrencies().getData();
      String accountLevel =
          ((OkexAccountService) accountService)
              .getOkexAccountConfiguration()
              .getData()
              .get(0)
              .getAccountLevel();
      tradeFee =
          ((OkexAccountService) accountService)
              .getTradeFee(SPOT, null, null, accountLevel)
              .getData();
    }

    exchangeMetaData =
        OkexAdapters.adaptToExchangeMetaData(exchangeMetaData, instruments, currencies, tradeFee);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    ExchangeSpecification exchangeSpec = getExchangeSpecification();

    String publicApiUrl = getPublicApiUrl();
    this.streamingService = new OkxStreamingService(publicApiUrl, exchangeSpec);

    applyStreamingSpecification(exchangeSpec, this.streamingService);
    this.streamingMarketDataService = new OkxStreamingMarketDataService(this.streamingService);

    return streamingService.connect();
  }

  private String getPublicApiUrl() {
    String apiUrl;
    ExchangeSpecification exchangeSpec = getExchangeSpecification();
    if (exchangeSpec.getOverrideWebsocketApiUri() != null) {
      return exchangeSpec.getOverrideWebsocketApiUri();
    }
    final boolean useSandbox =
        exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX) != null
            && exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX).equals("true");
    final boolean userAws =
        exchangeSpecification
            .getExchangeSpecificParametersItem(Parameters.PARAM_USE_AWS)
            .equals("true");

    if (useSandbox) {
      apiUrl = SANDBOX_WS_PUBLIC_CHANNEL_URI;
    } else {
      apiUrl = userAws ? AWS_WS_PUBLIC_CHANNEL_URI : WS_PUBLIC_CHANNEL_URI;
    }
    return apiUrl;
  }

  @Override
  public Completable disconnect() {
    OkxStreamingService service = this.streamingService;
    this.streamingService = null;
    this.streamingMarketDataService = null;
    service.pingPongSubscriptionDispose();
    return service.disconnect();
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public OkxStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    throw new NotYetImplementedForExchangeException("useCompressedMessage");
  }

  public OkxStreamingService getStreamingService() {
    return streamingService;
  }
}
