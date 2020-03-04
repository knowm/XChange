package org.knowm.xchange.stream.coinmate;

import com.pusher.client.PusherOptions;
import com.pusher.client.util.HttpAuthorizer;
import org.knowm.xchange.stream.coinmate.dto.auth.CoinmateUrlEncodedConnectionFactory;
import org.knowm.xchange.stream.coinmate.dto.auth.PusherAuthParamsObject;
import org.knowm.xchange.stream.service.pusher.PusherStreamingService;
import io.reactivex.Completable;
import org.knowm.xchange.coinmate.CoinmateExchange;
import org.knowm.xchange.stream.core.*;

public class CoinmateStreamingExchange extends CoinmateExchange implements StreamingExchange {
  private static final String API_KEY = "af76597b6b928970fbb0";
  private PusherStreamingService streamingService;

  private CoinmateStreamingMarketDataService streamingMarketDataService;
  private CoinmateStreamingAccountService streamingAccountService;
  private CoinmateStreamingTradeService streamingTradeService;

  public CoinmateStreamingExchange() {}

  private void createExchange() {
    if (exchangeSpecification.getApiKey() != null) {
      PusherAuthParamsObject params =
          new PusherAuthParamsObject(
              exchangeSpecification.getSecretKey(),
              exchangeSpecification.getApiKey(),
              exchangeSpecification.getUserName(),
              getNonceFactory());

      CoinmateUrlEncodedConnectionFactory urlEncodedConnectionFactory =
          new CoinmateUrlEncodedConnectionFactory(params);
      HttpAuthorizer authorizer =
          new HttpAuthorizer("https://www.coinmate.io/api/pusherAuth", urlEncodedConnectionFactory);
      PusherOptions options = new PusherOptions();
      options.setAuthorizer(authorizer);
      options.setCluster("mt1");
      streamingService = new PusherStreamingService(API_KEY, options);
    } else {
      streamingService = new PusherStreamingService(API_KEY);
    }
  }

  @Override
  protected void initServices() {
    super.initServices();
    createExchange();
    streamingMarketDataService = new CoinmateStreamingMarketDataService(streamingService);
    streamingAccountService =
        new CoinmateStreamingAccountService(streamingService, exchangeSpecification.getUserName());
    streamingTradeService =
        new CoinmateStreamingTradeService(streamingService, exchangeSpecification.getUserName());
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
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
