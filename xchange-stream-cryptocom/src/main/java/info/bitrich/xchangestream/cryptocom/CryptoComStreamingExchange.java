package info.bitrich.xchangestream.cryptocom;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Completable;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoComStreamingExchange extends CryptoComExchange implements StreamingExchange {

  private static final Logger LOG = LoggerFactory.getLogger(CryptoComStreamingExchange.class);

  public static final String PUBLIC_WS_URL = "wss://stream.crypto.com/exchange/v1/market";
  public static final String PRIVATE_WS_URL = "wss://stream.crypto.com/exchange/v1/user";

  // Crypto.com does not publish a dedicated sandbox streaming host in its docs; inferred from the
  // "api." -> "uat-api.3ona.co" REST sandbox naming used by CryptoComExchange. Unverified - see
  // the warning logged in connect() when USE_SANDBOX is set.
  private static final String SANDBOX_PUBLIC_WS_URL = "wss://uat-stream.3ona.co/exchange/v1/market";
  private static final String SANDBOX_PRIVATE_WS_URL = "wss://uat-stream.3ona.co/exchange/v1/user";

  private CryptoComStreamingService publicStreamingService;
  private CryptoComPrivateStreamingService privateStreamingService;

  private CryptoComStreamingMarketDataService streamingMarketDataService;
  private CryptoComStreamingTradeService streamingTradeService;
  private CryptoComStreamingAccountService streamingAccountService;

  @Override
  public Completable connect(ProductSubscription... args) {
    if (usingSandbox()) {
      LOG.warn(
          "Crypto.com does not publish a sandbox WebSocket host; connecting to {} which is "
              + "inferred from the REST sandbox naming and unverified.",
          publicWsUrl());
    }

    publicStreamingService = new CryptoComStreamingService(publicWsUrl());
    applyStreamingSpecification(exchangeSpecification, publicStreamingService);
    streamingMarketDataService = new CryptoComStreamingMarketDataService(publicStreamingService);
    Completable publicConnect = publicStreamingService.connect();

    String apiKey = exchangeSpecification.getApiKey();
    String secretKey = exchangeSpecification.getSecretKey();
    if (apiKey == null || secretKey == null) {
      return publicConnect;
    }

    privateStreamingService =
        new CryptoComPrivateStreamingService(privateWsUrl(), apiKey, secretKey);
    applyStreamingSpecification(exchangeSpecification, privateStreamingService);
    streamingTradeService = new CryptoComStreamingTradeService(privateStreamingService);
    streamingAccountService = new CryptoComStreamingAccountService(privateStreamingService);

    // Independent connections to different hosts - no reason to serialize them.
    return Completable.mergeArray(publicConnect, privateStreamingService.connect());
  }

  private String publicWsUrl() {
    return usingSandbox() ? SANDBOX_PUBLIC_WS_URL : PUBLIC_WS_URL;
  }

  private String privateWsUrl() {
    return usingSandbox() ? SANDBOX_PRIVATE_WS_URL : PRIVATE_WS_URL;
  }

  @Override
  public Completable disconnect() {
    Completable publicDisconnect =
        publicStreamingService == null
            ? Completable.complete()
            : publicStreamingService.disconnect();
    Completable privateDisconnect =
        privateStreamingService == null
            ? Completable.complete()
            : privateStreamingService.disconnect();

    publicStreamingService = null;
    privateStreamingService = null;
    streamingMarketDataService = null;
    streamingTradeService = null;
    streamingAccountService = null;

    return Completable.mergeArray(publicDisconnect, privateDisconnect);
  }

  @Override
  public boolean isAlive() {
    return publicStreamingService != null && publicStreamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    if (publicStreamingService != null) {
      publicStreamingService.useCompressedMessages(compressedMessages);
    }
    if (privateStreamingService != null) {
      privateStreamingService.useCompressedMessages(compressedMessages);
    }
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
  public StreamingAccountService getStreamingAccountService() {
    return streamingAccountService;
  }
}
