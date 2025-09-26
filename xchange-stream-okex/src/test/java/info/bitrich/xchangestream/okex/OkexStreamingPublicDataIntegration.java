package info.bitrich.xchangestream.okex;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

public class OkexStreamingPublicDataIntegration {

  private StreamingExchange exchange;
  private final Instrument currencyPair = CurrencyPair.BTC_USDT;
  private final Instrument instrumentETH = new FuturesContract("ETH/USDT/SWAP");
  private final Instrument instrumentSHIB = new FuturesContract("SHIB/USDT/SWAP");

  @Before
  public void setUp() {
    exchange = StreamingExchangeFactory.INSTANCE.createExchange(OkexStreamingExchange.class);
    exchange.connect().blockingAwait();
  }

  @Test
  public void testTrades() throws InterruptedException {
    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getTrades(currencyPair)
            .subscribe(
                trade -> {
                  System.out.println(trade);
                  assertThat(trade.getInstrument()).isEqualTo(currencyPair);
                });
    Disposable dis2 =
        exchange
            .getStreamingMarketDataService()
            .getTrades(instrumentETH)
            .subscribe(
                trade -> {
                  System.out.println(trade);
                  assertThat(trade.getInstrument()).isEqualTo(instrumentETH);
                });
    TimeUnit.SECONDS.sleep(3);
    dis.dispose();
    dis2.dispose();
  }

  @Test
  public void testTicker() throws InterruptedException {
    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getTicker(currencyPair)
            .subscribe(System.out::println);
    Disposable dis2 =
        exchange
            .getStreamingMarketDataService()
            .getTicker(instrumentETH)
            .subscribe(System.out::println);
    TimeUnit.SECONDS.sleep(3);
    dis.dispose();
    dis2.dispose();
  }

  @Test
  public void testFundingRateStream() throws InterruptedException {
    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getFundingRate(instrumentETH)
            .subscribe(System.out::println);
    TimeUnit.SECONDS.sleep(3);
    dis.dispose();
  }

  @Test
  public void testOrderBook() throws InterruptedException {
    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(currencyPair)
            .subscribe(
                orderBook -> {
                  //                  System.out.println(orderBook);
                  assertThat(orderBook.getBids().get(0).getLimitPrice())
                      .isLessThan(orderBook.getAsks().get(0).getLimitPrice());
                  assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(currencyPair);
                });
    Disposable dis2 =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(instrumentSHIB)
            .subscribe(
                orderBook -> {
                  //                  System.out.println(orderBook);
                  assertThat(orderBook.getBids().get(0).getLimitPrice())
                      .isLessThan(orderBook.getAsks().get(0).getLimitPrice());
                  assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(instrumentSHIB);
                  // Min SHIB 100000
                  assertThat(
                          orderBook
                                  .getBids()
                                  .get(0)
                                  .getOriginalAmount()
                                  .compareTo(new BigDecimal(100000))
                              >= 0)
                      .isTrue();
                });
    Disposable dis3 =
        exchange
            .getStreamingMarketDataService()
            .getOrderBookUpdates(instrumentSHIB)
            .subscribe(
                orderBookUpdate -> {
                  System.out.println("orderBookUpdate " + orderBookUpdate);
                  // Min SHIB 100000
                  assertThat(
                          orderBookUpdate
                                  .get(0)
                                  .getLimitOrder()
                                  .getOriginalAmount()
                                  .compareTo(new BigDecimal(100000))
                              >= 0)
                      .isTrue();
                });
    TimeUnit.SECONDS.sleep(3);
    dis.dispose();
    dis2.dispose();
    dis3.dispose();
  }
}
