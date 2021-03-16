package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenTickerExample {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenTickerExample.class);

  public static void main(String[] args) throws InterruptedException {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(KrakenStreamingExchange.class);

    StreamingExchange krakenExchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    krakenExchange.connect().blockingAwait();

    krakenExchange
        .getStreamingMarketDataService()
        .getTicker(CurrencyPair.BTC_USD)
        .subscribe(
            s -> {
              LOG.info("Received {}", s);
            },
            throwable -> {
              LOG.error("Fail to get ticker {}", throwable.getMessage(), throwable);
            });

    TimeUnit.SECONDS.sleep(45);

    krakenExchange.disconnect().subscribe(() -> LOG.info("Disconnected"));
  }
}
