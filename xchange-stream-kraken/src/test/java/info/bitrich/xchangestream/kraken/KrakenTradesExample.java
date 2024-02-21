package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenTradesExample {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenTradesExample.class);

  public static void main(String[] args) throws InterruptedException {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(KrakenStreamingExchange.class);

    StreamingExchange krakenExchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    krakenExchange.connect().blockingAwait();

    Disposable tradesDisp =
        krakenExchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.BTC_USD)
            .subscribe(
                s -> {
                  LOG.info("Received {}", s);
                },
                throwable -> {
                  LOG.error("Fail to get ticker {}", throwable.getMessage(), throwable);
                });

    TimeUnit.SECONDS.sleep(45);

    tradesDisp.dispose();

    krakenExchange.disconnect().subscribe(() -> LOG.info("Disconnected"));
  }
}
