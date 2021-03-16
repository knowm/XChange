package info.bitrich.xchangestream.bitfinex;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.BitfinexExchange;

/** Created by Lukas Zaoralek on 7.11.17. */
public class BitfinexStreamingExchange extends BitfinexExchange implements StreamingExchange {

  static final String API_URI = "wss://api.bitfinex.com/ws/2";

  private BitfinexStreamingService streamingService;
  private BitfinexStreamingMarketDataService streamingMarketDataService;
  private BitfinexStreamingTradeService streamingTradeService;
  private BitfinexStreamingAccountService streamingAccountService;

  @Override
  protected void initServices() {
    super.initServices();
    this.streamingService = createStreamingService();
    this.streamingMarketDataService = new BitfinexStreamingMarketDataService(streamingService);
    this.streamingTradeService = new BitfinexStreamingTradeService(streamingService);
    this.streamingAccountService = new BitfinexStreamingAccountService(streamingService);
  }

  private BitfinexStreamingService createStreamingService() {
    BitfinexStreamingService streamingService =
        new BitfinexStreamingService(API_URI, getNonceFactory());
    applyStreamingSpecification(getExchangeSpecification(), streamingService);
    if (StringUtils.isNotEmpty(exchangeSpecification.getApiKey())) {
      streamingService.setApiKey(exchangeSpecification.getApiKey());
      streamingService.setApiSecret(exchangeSpecification.getSecretKey());
    }
    return streamingService;
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
  public boolean isAlive() {
    return streamingService.isSocketOpen();
  }

  @Override
  public Flowable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  @Override
  public Flowable<Object> connectionSuccess() {
    return streamingService.subscribeConnectionSuccess();
  }

  @Override
  public Flowable<State> connectionStateFlowable() {
    return streamingService.subscribeConnectionState();
  }

  @Override
  public Flowable<Object> connectionIdle() {
    return streamingService.subscribeIdle();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = super.getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(false);

    return spec;
  }

  @Override
  public BitfinexStreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public BitfinexStreamingAccountService getStreamingAccountService() {
    return streamingAccountService;
  }

  @Override
  public BitfinexStreamingTradeService getStreamingTradeService() {
    return streamingTradeService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  public boolean isAuthenticatedAlive() {
    return streamingService != null && streamingService.isAuthenticated();
  }
}
