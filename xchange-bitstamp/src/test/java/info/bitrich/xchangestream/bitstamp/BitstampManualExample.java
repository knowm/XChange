package info.bitrich.xchangestream.bitstamp;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.service.ConnectableService;
import io.reactivex.disposables.Disposable;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MINUTES;

public class BitstampManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(BitstampManualExample.class);

    private static final TimedSemaphore rateLimiter = new TimedSemaphore(1, MINUTES, 20);
    private static void rateLimit() {
        try {
            rateLimiter.acquire();
        } catch (InterruptedException e) {
            LOG.warn("Bitfinex connection throttle control has been interrupted");
        }
    }

    public static void main(String[] args) {

        ExchangeSpecification defaultExchangeSpecification = new ExchangeSpecification(BitstampStreamingExchange.class);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(ConnectableService.BEFORE_CONNECTION_HANDLER, (Runnable) BitstampManualExample::rateLimit);

        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(defaultExchangeSpecification);
        exchange.connect().blockingAwait();

        exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD).subscribe(orderBook -> {
            LOG.info("First ask: {}", orderBook.getAsks().get(0));
            LOG.info("First bid: {}", orderBook.getBids().get(0));
        });

        Disposable subscribe = exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD).subscribe(trade -> {
            LOG.info("Trade {}", trade);
        });

        subscribe.dispose();

        exchange.disconnect().subscribe(() -> LOG.info("Disconnected from the Exchange"));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rateLimiter.shutdown();
    }
}
