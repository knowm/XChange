package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexTicker;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.util.BookSanityChecker;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
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
    exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitmexStreamingExchange.class);
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

  private <T> void awaitDataCount(Flowable<T> Flowable) {
    Flowable
        .test()
        .assertNoErrors()
        .awaitCount(BitmexTest.MIN_DATA_COUNT);
  }

  @Test
  public void shouldReceiveBooks() {
    Flowable<OrderBook> orderBookFlowable = streamingMarketDataService.getOrderBook(xbtUsd);
    awaitDataCount(orderBookFlowable);
  }

  @Test
  public void shouldReceiveRawTickers() {
    Flowable<BitmexTicker> rawTickerFlowable = streamingMarketDataService.getRawTicker(xbtUsd);
    awaitDataCount(rawTickerFlowable);
  }

  @Test
  public void shouldReceiveTickers() {
    Flowable<Ticker> tickerFlowable = streamingMarketDataService.getTicker(xbtUsd);
    awaitDataCount(tickerFlowable);
  }

  //    @Test
  public void shouldReceiveTrades() {
    Flowable<Trade> orderBookFlowable = streamingMarketDataService.getTrades(xbtUsd);
    awaitDataCount(orderBookFlowable);
  }

  @Test
  public void shouldHaveNoBookErrors() {
    streamingMarketDataService
        .getOrderBook(xbtUsd)
        .test()
        .assertNoErrors()
        .awaitCount(10);
  }
}
