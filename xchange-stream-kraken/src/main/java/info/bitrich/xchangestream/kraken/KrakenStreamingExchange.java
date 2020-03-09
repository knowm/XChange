package info.bitrich.xchangestream.kraken;

import com.google.common.base.MoreObjects;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.service.KrakenAccountServiceRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author makarid */
public class KrakenStreamingExchange extends KrakenExchange implements StreamingExchange {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenStreamingExchange.class);
  private static final String USE_BETA = "Use_Beta";
  private static final String API_URI = "wss://ws.kraken.com";
  private static final String API_AUTH_URI = "wss://ws-auth.kraken.com";
  private static final String API_BETA_URI = "wss://beta-ws.kraken.com";

  private KrakenStreamingService streamingService, privateStreamingService;
  private KrakenStreamingMarketDataService streamingMarketDataService;
  private KrakenStreamingTradeService streamingTradeService;

  public KrakenStreamingExchange() {}

  private static String pickUri(boolean isPrivate, boolean useBeta) {
    return useBeta ? API_BETA_URI : isPrivate ? API_AUTH_URI : API_URI;
  }

  @Override
  protected void initServices() {
    super.initServices();
    Boolean useBeta =
        MoreObjects.firstNonNull(
            (Boolean) exchangeSpecification.getExchangeSpecificParametersItem(USE_BETA),
            Boolean.FALSE);

    this.streamingService = new KrakenStreamingService(false, pickUri(false, useBeta));
    this.streamingMarketDataService = new KrakenStreamingMarketDataService(streamingService);

    if (StringUtils.isNotEmpty(exchangeSpecification.getApiKey())) {
      this.privateStreamingService = new KrakenStreamingService(true, pickUri(true, useBeta));
    }

    KrakenAccountServiceRaw rawKrakenAcctService = (KrakenAccountServiceRaw) getAccountService();

    streamingTradeService =
        new KrakenStreamingTradeService(privateStreamingService, rawKrakenAcctService);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    if (privateStreamingService != null)
      return privateStreamingService.connect().mergeWith(streamingService.connect());
    return streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    if (privateStreamingService != null)
      return privateStreamingService.disconnect().mergeWith(streamingService.disconnect());
    return streamingService.disconnect();
  }

  @Override
  public boolean isAlive() {
    return streamingService.isSocketOpen()
        && (privateStreamingService == null || privateStreamingService.isSocketOpen());
  }

  @Override
  public Observable<Object> connectionSuccess() {
    return streamingService.subscribeConnectionSuccess();
  }

  @Override
  public Observable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = super.getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(false);
    return spec;
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public StreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
