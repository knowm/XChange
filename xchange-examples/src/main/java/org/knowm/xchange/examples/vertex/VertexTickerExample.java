package org.knowm.xchange.examples.vertex;

import com.knowm.xchange.vertex.VertexStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertexTickerExample {

  private static final Logger logger = LoggerFactory.getLogger(VertexTickerExample.class);
  public static final String BTC_USDC = "wBTC-USDC";

  public static void main(String[] args) throws InterruptedException {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(VertexStreamingExchange.class);

    exchangeSpecification.setApiKey("YOUR_WALLET_ADDRESS");
    exchangeSpecification.setSecretKey("YOUR_WALLET_SECRET_KEY");
    exchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);

    StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    exchange.connect().blockingAwait();

    Disposable ticker = exchange.getStreamingMarketDataService().getTicker(new CurrencyPair(BTC_USDC))
        .forEach(tick -> logger.info(BTC_USDC + " TOB: " + tick));

    Thread.sleep(30000);

    ticker.dispose();

    exchange.disconnect().blockingAwait();


  }

}
