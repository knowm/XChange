package info.bitrich.xchangestream.hitbtc;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(HitbtcManualExample.class);

  public static void main(String[] args) {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(HitbtcStreamingExchange.class);

    exchange.connect().blockingAwait();
    Disposable orderBookObserver =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_USD)
            .subscribe(
                orderBook -> {
                  LOG.info("First ask: {}", orderBook.getAsks().get(0));
                  LOG.info("First bid: {}", orderBook.getBids().get(0));
                },
                throwable -> LOG.error("ERROR in getting order book: ", throwable));

    Disposable tradesObserver =
        exchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.BTC_USD)
            .subscribe(
                trade -> {
                  LOG.info("TRADE: {}", trade);
                },
                throwable -> LOG.error("ERROR in getting trade: ", throwable));

    Disposable tickerObserver =
        exchange
            .getStreamingMarketDataService()
            .getTicker(CurrencyPair.ETH_BTC)
            .subscribe(
                ticker -> {
                  LOG.info("TICKER: {}", ticker);
                },
                throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    orderBookObserver.dispose();
    tradesObserver.dispose();
    tickerObserver.dispose();
    exchange.disconnect().subscribe(() -> LOG.info("Disconnected"));
  }
}
