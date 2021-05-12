package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenOrderbookExample {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenOrderbookExample.class);

  public static void main(String[] args) throws InterruptedException {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(KrakenStreamingExchange.class);

    StreamingExchange krakenExchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    krakenExchange.connect().blockingAwait();

    Disposable tickerDis =
        krakenExchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_USD)
            .subscribe(
                s -> {
                  LOG.info("Received book with {} bids and {} asks", s.getBids().size(), s.getAsks().size());
                },
                throwable -> {
                  LOG.error("Fail to get ticker {}", throwable.getMessage(), throwable);
                });

    TimeUnit.SECONDS.sleep(60);

    tickerDis.dispose();

    krakenExchange.disconnect().subscribe(() -> LOG.info("Disconnected"));
  }
}
