package info.bitrich.xchangestream.coincheck;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.CompositeDisposable;
import java.math.BigDecimal;
import java.time.Duration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;

@Slf4j
public class CoincheckManualExample {
  @SneakyThrows
  public static void main(String[] args) {
    CompositeDisposable disposables = new CompositeDisposable();

    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(CoincheckStreamingExchange.class)
            .getDefaultExchangeSpecification();
    CoincheckStreamingExchange exchange =
        (CoincheckStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);

    exchange.connect().blockingAwait();

    disposables.add(
        exchange
            .getStreamingMarketDataService()
            .getTicker(CurrencyPair.BTC_JPY)
            .subscribe(
                ticker -> {
                  log.info("Ticker: {}", ticker);
                },
                throwable -> log.error("ERROR in getting ticker: ", throwable)));

    disposables.add(
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(CurrencyPair.BTC_JPY)
            .subscribe(
                book -> {
                  BigDecimal askPrice = book.getAsks().get(0).getLimitPrice();
                  BigDecimal bidPrice = book.getBids().get(0).getLimitPrice();
                  log.info("Order book: {}/{}", askPrice, bidPrice);
                },
                e -> log.error("Error", e)));

    disposables.add(
        exchange
            .getStreamingMarketDataService()
            .getTrades(CurrencyPair.BTC_JPY)
            .subscribe(
                trade -> {
                  log.info("Trade: {}", trade);
                },
                e -> log.error("Error", e)));

    Thread.sleep(Duration.ofSeconds(20).toMillis());

    exchange.disconnect().blockingAwait();
    disposables.dispose();
  }
}
