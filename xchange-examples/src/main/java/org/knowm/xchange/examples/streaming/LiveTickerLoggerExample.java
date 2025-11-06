package org.knowm.xchange.examples.streaming;

import io.reactivex.disposables.Disposable;
import org.knowm.xchange.bitstamp.BitstampStreamingExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.streaming.ExchangeStreamingAdapter;
import org.knowm.xchange.streaming.StreamingExchange;
import org.knowm.xchange.streaming.StreamingExchangeFactory;

/**
 * Example: Subscribe to live ticker updates from Bitstamp via WebSocket API.
 *
 * <p>Usage:
 *   mvn compile exec:java -Dexec.mainClass="org.knowm.xchange.examples.streaming.LiveTickerLoggerExample"
 */
public class LiveTickerLoggerExample {

  public static void main(String[] args) throws Exception {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(BitstampStreamingExchange.class);

    System.out.println("Connecting to Bitstamp WebSocket API...");
    exchange.connect().blockingAwait();

    Disposable subscription =
        exchange
            .getStreamingMarketDataService()
            .getTicker(CurrencyPair.BTC_USD)
            .subscribe(
                LiveTickerLoggerExample::handleTicker,
                throwable -> System.err.println("Error in stream: " + throwable.getMessage()));

    System.out.println("Subscribed to BTC/USD ticker updates. Listening for 30 seconds...");
    Thread.sleep(30000);

    subscription.dispose();
    exchange.disconnect().blockingAwait();
    System.out.println("Disconnected from exchange.");
  }

  private static void handleTicker(Ticker ticker) {
    System.out.printf("Tick: %s | Bid: %s | Ask: %s | Last: %s%n",
        ticker.getInstrument(),
        ticker.getBid(),
        ticker.getAsk(),
        ticker.getLast());
  }
}
