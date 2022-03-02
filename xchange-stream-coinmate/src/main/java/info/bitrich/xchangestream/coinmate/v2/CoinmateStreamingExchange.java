package info.bitrich.xchangestream.coinmate.v2;

import info.bitrich.xchangestream.coinmate.v2.dto.auth.AuthParams;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Completable;
import org.knowm.xchange.coinmate.CoinmateExchange;

public class CoinmateStreamingExchange extends CoinmateExchange implements StreamingExchange {
  private static final String API_BASE = "wss://coinmate.io/api/websocket";

  private CoinmateStreamingService streamingService;
  private CoinmateStreamingMarketDataService streamingMarketDataService;
  private CoinmateStreamingAccountService streamingAccountService;
  private CoinmateStreamingTradeService streamingTradeService;

  public CoinmateStreamingExchange() {}

  private void createExchange() {
    AuthParams authParams;
    if (exchangeSpecification.getApiKey() != null) {
      authParams =
          new AuthParams(
              exchangeSpecification.getSecretKey(),
              exchangeSpecification.getApiKey(),
              exchangeSpecification.getUserName(),
              getNonceFactory());
    } else {
      authParams = null;
    }

    streamingService = new CoinmateStreamingService(API_BASE, authParams);
  }

  @Override
  protected void initServices() {
    super.initServices();
    createExchange();
    streamingMarketDataService = new CoinmateStreamingMarketDataService(streamingService);
    streamingAccountService = new CoinmateStreamingAccountService(streamingService);
    streamingTradeService = new CoinmateStreamingTradeService(streamingService);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    return streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    return streamingService.disconnect();
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

  @Override
  public boolean isAlive() {
    return streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {}
}
