package info.bitrich.xchangestream.gemini;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(GeminiManualExample.class);

  public static void main(String[] args) throws Exception {
    ProductSubscription productSubscription =
        ProductSubscription.create()
            .addOrderbook(CurrencyPair.BTC_USD)
            .addOrderbook(CurrencyPair.ETH_USD)
            .build();

    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(GeminiStreamingExchange.class)
            .getDefaultExchangeSpecification();

    GeminiStreamingExchange exchange =
        (GeminiStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

    exchange.connect(productSubscription).blockingAwait();

    Disposable sub1 =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_USD, 5)
            .subscribe(
                orderBook -> {
                  LOG.info("First ask: {}", orderBook.getAsks().get(0));
                  LOG.info("First bid: {}", orderBook.getBids().get(0));
                },
                throwable -> LOG.error("ERROR in getting order book: ", throwable));

    Disposable sub2 =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.ETH_USD, 5)
            .subscribe(
                orderBook -> {
                  LOG.info("First ask: {}", orderBook.getAsks().get(0));
                  LOG.info("First bid: {}", orderBook.getBids().get(0));
                },
                throwable -> LOG.error("ERROR in getting order book: ", throwable));

    Thread.sleep(2000);
    LOG.info("Disposing subscriptions.");
    sub1.dispose();
    sub2.dispose();
    exchange.disconnect().blockingAwait();
  }
}
