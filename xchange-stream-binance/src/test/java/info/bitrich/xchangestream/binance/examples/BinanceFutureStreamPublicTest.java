package info.bitrich.xchangestream.binance.examples;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.binance.KlineSubscription;
import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static info.bitrich.xchangestream.binance.BinanceStreamingExchange.USE_REALTIME_BOOK_TICKER;
import static info.bitrich.xchangestream.binance.examples.Util.printOrderBookShortInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;
import static org.knowm.xchange.binance.dto.marketdata.KlineInterval.d1;
import static org.knowm.xchange.binance.dto.marketdata.KlineInterval.m1;

@Ignore
public class BinanceFutureStreamPublicTest {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceFutureStreamPublicTest.class);
  private static StreamingExchange exchange1;
  BinanceFutureStreamingExchange binanceFutureStreamingExchange1;
  private static StreamingExchange exchange2;
  BinanceFutureStreamingExchange binanceFutureStreamingExchange2;
  private static final Instrument instrument = new FuturesContract("ETH/USDT/PERP");
  private static final Instrument instrument2 = new FuturesContract("SOL/USDT/PERP");
  private static final boolean logOutput = false;
  private static final boolean useRealtimeBookTicker = false;

  @Before
  public void setUp() {
    exchange1 = initExchange();
    binanceFutureStreamingExchange1 = (BinanceFutureStreamingExchange) exchange1;
  }

  private BinanceStreamingExchange initExchange() {
    ExchangeSpecification spec = new ExchangeSpecification(BinanceFutureStreamingExchange.class);
    // The most convenient way. Can store all keys in .ssh folder
    AuthUtils.setApiAndSecretKey(spec, "binance-demo-futures");
//    spec.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    // optional - more frequent OrderBook ticker updates
    if (useRealtimeBookTicker)
      spec.setExchangeSpecificParametersItem(USE_REALTIME_BOOK_TICKER, true);
    // optional more frequent order book updates
    //    spec.setExchangeSpecificParametersItem(USE_HIGHER_UPDATE_FREQUENCY, true);
    return (BinanceStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
  }

  @Test
  public void kLineSubscription() throws InterruptedException {
    Map<Instrument, Set<KlineInterval>> klineMap = new HashMap<>();
    Set<KlineInterval> klineSet = new HashSet<>();
    klineSet.add(m1);
    klineSet.add(d1);
    klineMap.put(instrument, klineSet);
    KlineSubscription klineSubscription = new KlineSubscription(klineMap);
    ProductSubscription subscription = ProductSubscription.create().build();
    binanceFutureStreamingExchange1.connect(klineSubscription, subscription).blockingAwait();
    Disposable kLineDisposable =
        binanceFutureStreamingExchange1
            .getStreamingMarketDataService()
            .getKlines(instrument, m1)
            .subscribe(
                kLines -> {
                  if (logOutput) {
                    LOG.info("kLines subscribe: {}", kLines);
                  }
                });
    Thread.sleep(3000);
    kLineDisposable.dispose();
    exchange1.disconnect().blockingAwait();
  }

  @Test
  public void streamingMarketDataServiceTest() throws InterruptedException {
    List<Disposable> disposables = new ArrayList<>();
    // separate connection for tickers, klines etc(market path) and
    // orderbook, bookTicker, trades
    ProductSubscription.ProductSubscriptionBuilder subscriptionBuilder1 =
        ProductSubscription.create()
            .addFundingRates(instrument);
    ProductSubscription.ProductSubscriptionBuilder subscriptionBuilder2 =
        ProductSubscription.create()
            .addOrderbook(instrument)
            .addTrades(instrument);
    if (useRealtimeBookTicker) subscriptionBuilder2.addTicker(instrument);
    else subscriptionBuilder1.addTicker(instrument);
    ProductSubscription subscription1 = subscriptionBuilder1.build();
    ProductSubscription subscription2 = subscriptionBuilder2.build();
    exchange1.connect(subscription1).blockingAwait();
    exchange2 = initExchange();
    exchange2.connect(subscription2).blockingAwait();
    InstrumentMetaData instrumentMetaData =
        exchange1.getExchangeMetaData().getInstruments().get(instrument);

    assertThat(instrumentMetaData.getVolumeScale()).isNotNull();
    assertThat(instrumentMetaData.getPriceScale()).isNotNull();
    assertThat(instrumentMetaData.getMinimumAmount()).isNotNull();

    disposables.add(
        exchange2
            .getStreamingMarketDataService()
            .getOrderBook(instrument)
            .subscribe(
                orderBook -> {
                  if (logOutput) {
                    printOrderBookShortInfo(orderBook);
                  }
                  assertThat(orderBook.getBids().get(0).getLimitPrice())
                      .isLessThan(orderBook.getAsks().get(0).getLimitPrice());
                  assertThat(
                      orderBook
                          .getAsks()
                          .get(0)
                          .getLimitPrice()
                          .compareTo(orderBook.getBids().get(0).getLimitPrice())
                          > 0)
                      .isTrue();
                }));
    disposables.add(
        exchange2
            .getStreamingMarketDataService()
            .getOrderBookUpdates(instrument)
            .subscribe(
                orderBookUpdates -> {
                  if (logOutput) {
                    LOG.info("orderBookUpdates subscribe: {}", orderBookUpdates);
                  }
                }));
    if (useRealtimeBookTicker)
      disposables.add(
          exchange2
              .getStreamingMarketDataService()
              .getTicker(instrument)
              .subscribe(
                  ticker -> {
                    if (logOutput) {
                      LOG.info("orderBook ticker subscribe: {}", ticker);
                    }
                    assertThat(ticker.getInstrument().equals(instrument)).isTrue();
                    assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
                  },
                  throwable -> LOG.error("ticker subscribe error", throwable)));
    else
      disposables.add(
          exchange1
              .getStreamingMarketDataService()
              .getTicker(instrument)
              .subscribe(
                  ticker -> {
                    if (logOutput) {
                      LOG.info("ticker subscribe: {}", ticker);
                    }
                    assertThat(ticker.getInstrument().equals(instrument)).isTrue();
                    assertThat(ticker.getHigh()).isGreaterThan(ticker.getLow());
                  },
                  throwable -> LOG.error("ticker subscribe error", throwable)));
    disposables.add(
        exchange2
            .getStreamingMarketDataService()
            .getTrades(instrument)
            .subscribe(
                trade -> {
                  if (logOutput) {
                    LOG.info("trades subscribe: {}", trade);
                  }
                  assertThat(trade.getInstrument().equals(instrument)).isTrue();
                }));
    // main net only
    disposables.add(
        exchange1
            .getStreamingMarketDataService()
            .getFundingRate(instrument)
            .subscribe(
                fundingRate -> {
                  if (logOutput) {
                    LOG.info("fundingRate subscribe: {}", fundingRate);
                  }
                  assertThat(fundingRate.getInstrument().equals(instrument)).isTrue();
                }));
    Thread.sleep(10000);
    disposables.forEach(Disposable::dispose);
    exchange1.disconnect().blockingAwait();
  }

  @Ignore
  @Test
  public void heavyLoadTest() throws InterruptedException {
    List<Disposable> disposables = new ArrayList<>();
    ProductSubscription subscription =
        exchange1.getExchangeInstruments().stream()
            .filter(instrument -> instrument instanceof FuturesContract)
            .limit(150)
            .reduce(
                ProductSubscription.create(),
                ProductSubscription.ProductSubscriptionBuilder::addFundingRates,
                (productSubscriptionBuilder, productSubscriptionBuilder2) -> {
                  throw new UnsupportedOperationException();
                })
            .addFundingRates(instrument)
            .build();
    exchange1.connect(subscription).blockingAwait();
    for (var s : subscription.getFundingRates()) {
      disposables.add(
          exchange1
              .getStreamingMarketDataService()
              .getFundingRate(s)
              .subscribe(
                  fund -> {
                    if (logOutput) {
                      Random random = new Random();
                      if (random.nextInt(100) == 1) {
                        System.out.println("fund subscribe: " + fund);
                      }
                    }
                  }));
    }
    Thread.sleep(30000000);
    disposables.forEach(Disposable::dispose);
    exchange1.disconnect().blockingAwait();
  }
}
