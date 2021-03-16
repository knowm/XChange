package info.bitrich.xchangestream.bitfinex;

import static java.util.concurrent.TimeUnit.MINUTES;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.service.ConnectableService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.utils.CertHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 7.11.17. */
public class BitfinexManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(BitfinexManualExample.class);

  private static final TimedSemaphore rateLimiter = new TimedSemaphore(1, MINUTES, 15);

  private static void rateLimit() {
    try {
      rateLimiter.acquire();
    } catch (InterruptedException e) {
      LOG.warn("Bitfinex connection throttle control has been interrupted");
    }
  }

  public static void main(String[] args) throws Exception {
    CertHelper.trustAllCerts();

    ExchangeSpecification defaultExchangeSpecification =
        new ExchangeSpecification(BitfinexStreamingExchange.class);
    defaultExchangeSpecification.setExchangeSpecificParametersItem(
        ConnectableService.BEFORE_CONNECTION_HANDLER, (Runnable) BitfinexManualExample::rateLimit);

    defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);
    /*
            defaultExchangeSpecification.setProxyHost("localhost");
            defaultExchangeSpecification.setProxyPort(9999);

            defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_HOST, "localhost");
            defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_PORT, 8889);

            defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);
            defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ACCEPT_ALL_CERITICATES, true);
            defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ENABLE_LOGGING_HANDLER, true);
    */

    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(defaultExchangeSpecification);
    exchange.connect().blockingAwait();

    Flowable<OrderBook> orderBookObserver =
        exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD);
    Disposable orderBookSubscriber =
        orderBookObserver.subscribe(
            orderBook -> {
              LOG.info("First ask: {}", orderBook.getAsks().get(0));
              LOG.info("First bid: {}", orderBook.getBids().get(0));
            },
            throwable -> {
              LOG.error("ERROR in getting order book: ", throwable);
            });

    Disposable tickerSubscriber =
        exchange
            .getStreamingMarketDataService()
            .getTicker(CurrencyPair.BTC_EUR)
            .subscribe(
                ticker -> {
                  LOG.info("TICKER: {}", ticker);
                },
                throwable -> LOG.error("ERROR in getting ticker: ", throwable));

    Disposable tradesSubscriber =
        exchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.BTC_USD)
            .subscribe(
                trade -> {
                  LOG.info("TRADE: {}", trade);
                },
                throwable -> {
                  LOG.error("ERROR in getting trade: ", throwable);
                });

    Thread.sleep(10000);

    tickerSubscriber.dispose();
    tradesSubscriber.dispose();
    orderBookSubscriber.dispose();

    LOG.info("disconnecting...");
    exchange.disconnect().subscribe(() -> LOG.info("disconnected"));

    rateLimiter.shutdown();
  }
}
