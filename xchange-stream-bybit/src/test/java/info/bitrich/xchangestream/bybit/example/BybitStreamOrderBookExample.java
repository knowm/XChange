package info.bitrich.xchangestream.bybit.example;

import static info.bitrich.xchangestream.bybit.example.BaseBybitExchange.connect;

import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitStreamOrderBookExample {
  private static final Logger log = LoggerFactory.getLogger(BybitStreamOrderBookExample.class);
  public static void main(String[] args) {
    // Stream orderBook and OrderBookUpdates
    try {
      getOrderBookExample();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static void getOrderBookExample() throws InterruptedException {
    Instrument BTC_SPOT = new CurrencyPair("BTC/USDT");
    StreamingExchange exchange = connect(BybitCategory.SPOT, false);
    List<Disposable> booksDisposable = new ArrayList<>();
    List<Disposable> booksUpdatesDisposable = new ArrayList<>();

    booksDisposable.add(
        exchange.getStreamingMarketDataService().getOrderBook(BTC_SPOT)
            .doOnError(
                error -> {
                  log.error(error.getMessage());
                })
            .subscribe(
            orderBook -> log.info("orderBook: {}", orderBook.getTimeStamp())));

    booksUpdatesDisposable.add(
        exchange
            .getStreamingMarketDataService()
            .getOrderBookUpdates(BTC_SPOT)
            .doOnError(
                error -> {
                  log.error(error.getMessage());
                })
            .subscribe(
                orderBookUpdates -> log.info("orderBookUpdates: {}", orderBookUpdates)));
    Thread.sleep(4000L);
    for (Disposable dis:booksDisposable)
      dis.dispose();
    for (Disposable dis:booksUpdatesDisposable)
      dis.dispose();
    exchange.disconnect().blockingAwait();
  }
}
