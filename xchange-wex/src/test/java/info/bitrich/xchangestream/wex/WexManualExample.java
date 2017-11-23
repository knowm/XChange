package info.bitrich.xchangestream.wex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lukas Zaoralek on 16.11.17.
 */
public class WexManualExample {
  private static final Logger LOG = LoggerFactory.getLogger(WexManualExample.class);

  public static void main(String[] args) {

    StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(WexStreamingExchange.class.getName());
    exchange.connect().blockingAwait();

    exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD).subscribe(orderBook -> {
      LOG.info("First ask: {}", orderBook.getAsks().get(0));
      LOG.info("First bid: {}", orderBook.getBids().get(0));
    });

    exchange.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD).subscribe(trade -> {
      LOG.info("Trade {}", trade);
    });

    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
