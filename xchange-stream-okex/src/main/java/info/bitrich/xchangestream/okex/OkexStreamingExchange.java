package info.bitrich.xchangestream.okex;

import static org.knowm.xchange.okex.service.OkexMarketDataService.SPOT;
import static org.knowm.xchange.okex.service.OkexMarketDataService.SWAP;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.reactivex.Completable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.dto.meta.OkexExchangeInfo;
import org.knowm.xchange.okex.service.OkexAccountService;
import org.knowm.xchange.okex.service.OkexMarketDataServiceRaw;

public class OkexStreamingExchange extends OkexExchange implements StreamingExchange {
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

  private OkexStreamingService streamingService;
  protected OkexExchangeInfo exchangeInfo;
  private OkexStreamingMarketDataService streamingMarketDataService;

  private OkexStreamingTradeService streamingTradeService;

  public OkexStreamingExchange() {}

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

    this.streamingService = new OkexStreamingService(getApiUrl(), this.exchangeSpecification);
    this.streamingMarketDataService = new OkexStreamingMarketDataService(streamingService);
    this.streamingTradeService = new OkexStreamingTradeService(streamingService);
    applyStreamingSpecification(this.exchangeSpecification, this.streamingService);
    return streamingService.connect();
  }

  private String getApiUrl() {
    String apiUrl;
    ExchangeSpecification exchangeSpec = getExchangeSpecification();
    if (exchangeSpec.getOverrideWebsocketApiUri() != null) {
      return exchangeSpec.getOverrideWebsocketApiUri();
    }

    boolean useSandbox =
        Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX));
    final boolean userAws =
        exchangeSpecification
            .getExchangeSpecificParametersItem(Parameters.PARAM_USE_AWS)
            .equals("true");
    if (useSandbox) {
      apiUrl =
          (this.exchangeSpecification.getApiKey() == null)
              ? SANDBOX_WS_PUBLIC_CHANNEL_URI
              : SANDBOX_WS_PRIVATE_CHANNEL_URI;
    } else {
      apiUrl =
          (this.exchangeSpecification.getApiKey() == null)
              ? userAws ? AWS_WS_PUBLIC_CHANNEL_URI : WS_PUBLIC_CHANNEL_URI
              : userAws ? AWS_WS_PRIVATE_CHANNEL_URI : WS_PRIVATE_CHANNEL_URI;
    }
    return apiUrl;
  }

  @Override
  public Completable disconnect() {
    logger.info("disconnect");
    List<Completable> completables = new ArrayList<>();
    streamingService.pingPongDisconnectIfConnected();
    completables.add(streamingService.disconnect());
    streamingService = null;
    return Completable.concat(completables);
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public OkexStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  public OkexStreamingService getStreamingService() {
    return streamingService;
  }

  @Override
  public OkexStreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    throw new NotYetImplementedForExchangeException("useCompressedMessage");
  }
}
