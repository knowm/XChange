package info.bitrich.xchangestream.coinmate;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinmateManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(CoinmateStreamingExchange.class);

  public static void main(String[] args) {

    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(CoinmateStreamingExchange.class.getName());
    exchange.connect().blockingAwait();

    exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.BTC_EUR)
        .subscribe(
            orderBook -> {
              LOG.info("First ask: {}", orderBook.getAsks().get(0));
              LOG.info("First bid: {}", orderBook.getBids().get(0));
            });

    //        Disposable subscribe =
    // exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD).subscribe(trade -> {
    //            LOG.info("Trade {}", trade);
    //        });

    //        subscribe.dispose();

    //        exchange.disconnect().subscribe(() -> LOG.info("Disconnected from the Exchange"));

    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
