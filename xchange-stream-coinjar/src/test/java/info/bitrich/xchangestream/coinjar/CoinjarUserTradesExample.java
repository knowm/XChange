package info.bitrich.xchangestream.coinjar;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.core.StreamingTradeService;
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

      streamingTradeService
          .getUserTrades(null)
          .test()
          .awaitCount(1)
          .assertNoErrors();
    }
  }
}
