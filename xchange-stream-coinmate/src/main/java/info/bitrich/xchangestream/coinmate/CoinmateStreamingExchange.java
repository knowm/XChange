package info.bitrich.xchangestream.coinmate;

import info.bitrich.xchangestream.coinmate.dto.auth.AuthParams;
import info.bitrich.xchangestream.core.*;
import io.reactivex.Completable;
import org.knowm.xchange.coinmate.CoinmateExchange;

public class CoinmateStreamingExchange extends CoinmateExchange implements StreamingExchange {
  private static final String API_BASE = "wss://coinmate.io/api/websocket";

  private CoinmateStreamingServiceFactory streamingServiceFactory;
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
    streamingServiceFactory = new CoinmateStreamingServiceFactory(API_BASE, authParams);
  }

  @Override
  protected void initServices() {
    super.initServices();
    createExchange();
    streamingMarketDataService = new CoinmateStreamingMarketDataService(streamingServiceFactory);
    streamingAccountService = new CoinmateStreamingAccountService(streamingServiceFactory);
    streamingTradeService = new CoinmateStreamingTradeService(streamingServiceFactory);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    return Completable.complete();
  }

  @Override
  public Completable disconnect() {
    return Completable.complete();
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
    return true;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {}
}
