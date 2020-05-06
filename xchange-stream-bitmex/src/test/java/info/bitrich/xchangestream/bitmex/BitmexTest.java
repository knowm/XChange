package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexTicker;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.util.BookSanityChecker;
import io.reactivex.Completable;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Foat Akhmadeev 31/05/2018 */
@Ignore // Requires Bitmex to be up and contactable or the build fails.
public class BitmexTest {
  private static final Logger LOG = LoggerFactory.getLogger(BitmexTest.class);

  private static final CurrencyPair xbtUsd = CurrencyPair.XBT_USD;
  private static final int MIN_DATA_COUNT = 2;

  private StreamingExchange exchange;
  private BitmexStreamingMarketDataService streamingMarketDataService;

  @Before
  public void setup() {
    exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(BitmexStreamingExchange.class.getName());
    awaitCompletable(exchange.connect());
    streamingMarketDataService =
        (BitmexStreamingMarketDataService) exchange.getStreamingMarketDataService();
  }

  @After
  public void tearDown() {
    awaitCompletable(exchange.disconnect());
  }

  private void awaitCompletable(Completable completable) {
    completable.test().awaitDone(1, TimeUnit.MINUTES).assertComplete().assertNoErrors();
  }

  private <T> void awaitDataCount(Observable<T> observable) {
    observable
        .test()
        .assertSubscribed()
        .assertNoErrors()
        .awaitCount(BitmexTest.MIN_DATA_COUNT)
        .assertNoTimeout()
        .dispose();
  }

  @Test
  public void shouldReceiveBooks() {
    Observable<OrderBook> orderBookObservable = streamingMarketDataService.getOrderBook(xbtUsd);
    awaitDataCount(orderBookObservable);
  }

  @Test
  public void shouldReceiveRawTickers() {
    Observable<BitmexTicker> rawTickerObservable = streamingMarketDataService.getRawTicker(xbtUsd);
    awaitDataCount(rawTickerObservable);
  }

  @Test
  public void shouldReceiveTickers() {
    Observable<Ticker> tickerObservable = streamingMarketDataService.getTicker(xbtUsd);
    awaitDataCount(tickerObservable);
  }

  //    @Test
  public void shouldReceiveTrades() {
    Observable<Trade> orderBookObservable = streamingMarketDataService.getTrades(xbtUsd);
    awaitDataCount(orderBookObservable);
  }

  @Test
  public void shouldHaveNoBookErrors() {
    streamingMarketDataService
        .getOrderBook(xbtUsd)
        .test()
        .assertSubscribed()
        .assertNoErrors()
        .awaitCount(10)
        .assertNever(
            book -> {
              String err = BookSanityChecker.hasErrors(book);
              LOG.info("err {}", err);
              return err != null;
            })
        .assertNoTimeout()
        .dispose();
  }
}
