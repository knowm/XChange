package info.bitrich.xchangestream.bybit.example;

import static info.bitrich.xchangestream.bybit.example.BaseBybitExchange.connect;

import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.derivative.FuturesContract;
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

  static List<Disposable> booksDisposable = new ArrayList<>();
  static Instrument XRP_PERP = new FuturesContract("XRP/USDT/PERP");
  static StreamingExchange exchange;

  private static void getOrderBookExample() throws InterruptedException {
    exchange = connect(BybitCategory.LINEAR, false);
    subscribeOrderBook();
    Thread.sleep(600000L);
    for (Disposable dis : booksDisposable) {
      dis.dispose();
    }
    exchange.disconnect().blockingAwait();
  }

  private static void subscribeOrderBook() {
    booksDisposable.add(
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(XRP_PERP)
            .doOnError(
                error -> {
                  log.error(error.getMessage());
                  for (Disposable dis : booksDisposable) {
                    dis.dispose();
                  }
                  subscribeOrderBook();
                })
            .subscribe(
                orderBook -> System.out.print("."),
                throwable -> {
                  log.error(throwable.getMessage());
                }));
  }
}
