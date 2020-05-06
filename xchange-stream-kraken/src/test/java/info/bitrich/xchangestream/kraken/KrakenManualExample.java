package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenManualExample {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenManualExample.class);

  public static void main(String[] args) throws InterruptedException {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(KrakenStreamingExchange.class);

    StreamingExchange krakenExchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    krakenExchange.connect().blockingAwait();

    CurrencyPair bchUsdt = new CurrencyPair(Currency.BCH, Currency.getInstance("USD"));
    Disposable btcEurOrderBookDis =
        krakenExchange
            .getStreamingMarketDataService()
            .getOrderBook(bchUsdt, 100)
            .subscribe(
                s -> {
                  LOG.info(
                      "Received order book {}({},{}) ask[0] = {} bid[0] = {}",
                      bchUsdt,
                      s.getAsks().size(),
                      s.getBids().size(),
                      s.getAsks().get(0),
                      s.getBids().get(0));
                },
                throwable -> {
                  LOG.error("Order book FAILED {}", throwable.getMessage(), throwable);
                });
    Disposable btcUsdOrderBookDis =
        krakenExchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BCH_EUR, 10)
            .subscribe(
                s -> {
                  LOG.info(
                      "Received order book {}({},{}) ask[0] = {} bid[0] = {}",
                      CurrencyPair.BCH_EUR,
                      s.getAsks().size(),
                      s.getBids().size(),
                      s.getAsks().get(0),
                      s.getBids().get(0));
                },
                throwable -> {
                  LOG.error("Order book FAILED {}", throwable.getMessage(), throwable);
                });
    Disposable tickerDis =
        krakenExchange
            .getStreamingMarketDataService()
            .getTicker(CurrencyPair.LTC_USD)
            .subscribe(
                s -> {
                  LOG.info("Received {}", s);
                },
                throwable -> {
                  LOG.error("Fail to get ticker {}", throwable.getMessage(), throwable);
                });

    Disposable tradeDis =
        krakenExchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.BTC_USD)
            .subscribe(
                s -> {
                  LOG.info("Received {}", s);
                },
                throwable -> {
                  LOG.error("Fail to get trade {}", throwable.getMessage(), throwable);
                });
    TimeUnit.SECONDS.sleep(5);

    btcEurOrderBookDis.dispose();
    btcUsdOrderBookDis.dispose();
    tickerDis.dispose();
    tradeDis.dispose();

    krakenExchange.disconnect().subscribe(() -> LOG.info("Disconnected"));
  }
}
