package info.bitrich.xchangestream.lgo;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.service.LgoKeyService;
import org.knowm.xchange.lgo.service.LgoSignatureService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class LgoStreamingExchange extends LgoExchange implements StreamingExchange {

  private LgoStreamingService streamingService;
  private LgoStreamingMarketDataService marketDataService;
  private LgoStreamingAccountService accountService;
  private LgoStreamingTradeService tradeService;
  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

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
            nonceFactory);
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
  public void useCompressedMessages(boolean compressedMessages) {}
}
