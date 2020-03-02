package info.bitrich.xchangestream.bankera;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankeraManualExample {
  private static final Logger LOGGER = LoggerFactory.getLogger(BankeraManualExample.class);

  public static void main(String[] args) {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(BankeraStreamingExchange.class.getName());

    exchange.connect().blockingAwait();
    exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.ETH_BTC)
        .subscribe(
            orderBook -> LOGGER.debug("ORDERBOOK: {}", orderBook.toString()),
            throwable -> LOGGER.error("ERROR in getting order book: ", throwable));

    exchange
        .getStreamingMarketDataService()
        .getTrades(CurrencyPair.ETH_BTC)
        .subscribe(
            trade -> LOGGER.debug("TRADES: {}", trade.toString()),
            throwable -> LOGGER.error("ERROR in getting trade ", throwable));

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    exchange.disconnect().subscribe(() -> LOGGER.info("Disconnected"));
  }
}
