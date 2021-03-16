package info.bitrich.xchangestream.serum;

import com.knowm.xchange.serum.SerumConfigs.Solana;
import com.knowm.xchange.serum.SerumExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import org.knowm.xchange.ExchangeSpecification;

public class SerumStreamingExchange extends SerumExchange implements StreamingExchange {

  private SerumStreamingService streamingService;

  @Override
  public Completable connect(ProductSubscription... args) {

    final String url =
        Solana.valueOf(
                String.valueOf(getExchangeSpecification().getExchangeSpecificParametersItem("Env")))
            .wsUrl();
    this.streamingService = new SerumStreamingService(url);
    return this.streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    final SerumStreamingService service = streamingService;
    streamingService = null;
    return service.disconnect();
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
  public Flowable<ConnectionStateModel.State> connectionStateFlowable() {
    return streamingService.subscribeConnectionState();
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return null;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification exchangeSpec = new ExchangeSpecification(this.getClass());
    exchangeSpec.setSslUri(Solana.MAINNET.restUrl());
    exchangeSpec.setHost("projectserum.com");
    exchangeSpec.setPort(80);
    exchangeSpec.setExchangeName("Serum");
    exchangeSpec.setExchangeDescription(
        "Serum is a decentralized cryptocurrency exchange built on Solana.");
    exchangeSpec.setExchangeSpecificParametersItem("Env", "MAINNET");

    return exchangeSpec;
  }
}
