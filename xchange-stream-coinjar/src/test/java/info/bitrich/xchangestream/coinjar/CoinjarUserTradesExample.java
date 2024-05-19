package info.bitrich.xchangestream.coinjar;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.BaseTestConsumer;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.AuthUtils;

public class CoinjarUserTradesExample {

  @Test
  public void runTest() {
    ExchangeSpecification defaultExchangeSpecification =
        new ExchangeSpecification(CoinjarStreamingExchange.class);

    AuthUtils.setApiAndSecretKey(defaultExchangeSpecification);

    if (defaultExchangeSpecification.getApiKey() != null) {
      StreamingExchange exchange =
          StreamingExchangeFactory.INSTANCE.createExchange(defaultExchangeSpecification);
      exchange.connect().blockingAwait();
      StreamingTradeService streamingTradeService = exchange.getStreamingTradeService();

      Disposable disposable =
          streamingTradeService
              .getUserTrades(null)
              .test()
              .awaitCount(1)
              .assertNoErrors();
      disposable.dispose();
    }
  }
}
