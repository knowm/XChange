package info.bitrich.xchangestream.kraken;

import static org.knowm.xchange.currency.CurrencyPair.BTC_USD;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenOrderbookExample {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenOrderbookExample.class);

  private static Disposable subscribe(StreamingExchange krakenExchange, CurrencyPair currencyPair) {
    return krakenExchange
        .getStreamingMarketDataService()
        .getOrderBook(currencyPair, 100)
        .subscribe(
            s -> {
              LOG.info(
                  "Received book with {} bids and {} asks", s.getBids().size(), s.getAsks().size());
              if (!s.getBids().isEmpty()) {
                BigDecimal bestBid = s.getBids().iterator().next().getLimitPrice();
                BigDecimal bestAsk = s.getAsks().iterator().next().getLimitPrice();
                if (bestBid.compareTo(bestAsk) > 0) {
                  LOG.warn(
                      "Crossed {} book, best bid {}, best ask {}", currencyPair, bestBid, bestAsk);
                }
              }
            },
            throwable -> {
              LOG.error("Fail to get ticker {}", throwable.getMessage(), throwable);
            });
  }

  public static void main(String[] args) throws InterruptedException {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(KrakenStreamingExchange.class);

    StreamingExchange krakenExchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    krakenExchange.connect().blockingAwait();

    subscribe(krakenExchange, BTC_USD);

    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);

    krakenExchange.disconnect().subscribe(() -> LOG.info("Disconnected"));
  }
}
