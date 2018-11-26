package info.bitrich.xchangestream.bankera;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankeraManualExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankeraManualExample.class);

    public static void main(String[] args) {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE
            .createExchange(BankeraStreamingExchange.class.getName());

        exchange.connect().blockingAwait();
      Disposable orderBookObserver = exchange.getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_USD)
            .subscribe(orderBook -> {
              LOGGER.info("First ask: {}", orderBook.getAsks().get(0));
              LOGGER.info("First bid: {}", orderBook.getBids().get(0));
        }, throwable -> LOGGER.error("ERROR in getting order book: ", throwable));

      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      orderBookObserver.dispose();
      exchange.disconnect().subscribe(() -> LOGGER.info("Disconnected"));

    }
}
