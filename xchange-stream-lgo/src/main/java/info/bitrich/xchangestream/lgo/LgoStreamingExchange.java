package info.bitrich.xchangestream.lgo;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.service.LgoKeyService;
import org.knowm.xchange.lgo.service.LgoSignatureService;

public class LgoStreamingExchange extends LgoExchange implements StreamingExchange {

  private LgoStreamingService streamingService;
  private LgoStreamingMarketDataService marketDataService;
  private LgoStreamingAccountService accountService;
  private LgoStreamingTradeService tradeService;

  @Override
  protected void initServices() {
    super.initServices();
    streamingService = createStreamingService();
    marketDataService = new LgoStreamingMarketDataService(streamingService);
    accountService = new LgoStreamingAccountService(streamingService);
    tradeService =
        new LgoStreamingTradeService(
            streamingService,
            new LgoKeyService(getExchangeSpecification()),
            LgoSignatureService.createInstance(getExchangeSpecification()),
            getNonceFactory());
  }

  private LgoStreamingService createStreamingService() {
    String apiUrl =
        getExchangeSpecification().getExchangeSpecificParameters().get(LgoEnv.WS_URL).toString();
    return new LgoStreamingService(this.getSignatureService(), apiUrl);
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);
    initServices();
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
  public StreamingMarketDataService getStreamingMarketDataService() {
    return marketDataService;
  }

  @Override
  public LgoStreamingAccountService getStreamingAccountService() {
    return accountService;
  }

  @Override
  public LgoStreamingTradeService getStreamingTradeService() {
    return tradeService;
  }

  @Override
  public Flowable<State> connectionStateFlowable() {
    return streamingService.subscribeConnectionState();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {}
}
