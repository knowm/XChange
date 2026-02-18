package info.bitrich.xchangestream.binance.examples;

import static info.bitrich.xchangestream.binance.examples.Util.printOrderBookShortInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.SPOT;
import static org.knowm.xchange.binance.dto.marketdata.KlineInterval.d1;
import static org.knowm.xchange.binance.dto.marketdata.KlineInterval.m1;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.binance.KlineSubscription;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class BinanceSpotStreamPublicTest {
  private static final Logger LOG = LoggerFactory.getLogger(BinanceSpotStreamPublicTest.class);
  private static StreamingExchange exchange;
  BinanceStreamingExchange binanceStreamingExchange;
  private static final Instrument instrument = new CurrencyPair("ETH/USDT");
  private static final Instrument instrument2 = new CurrencyPair("SOL/USDT");
  private static final boolean logOutput = false;

  @Before
  public void setUp() {
    ExchangeSpecification spec = new ExchangeSpecification(BinanceStreamingExchange.class);
    // The most convenient way. Can store all keys in .ssh folder
    AuthUtils.setApiAndSecretKey(spec, "binance-demo");
    spec.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, SPOT);
    exchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
    binanceStreamingExchange = (BinanceStreamingExchange) exchange;
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
    binanceStreamingExchange.connect(klineSubscription, subscription).blockingAwait();
    Disposable kLineDisposable =
        binanceStreamingExchange
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
    exchange.disconnect().blockingAwait();
  }

  @Test
  public void streamingMarketDataServiceTest() throws InterruptedException {
    List<Disposable> disposables = new ArrayList<>();
    ProductSubscription subscription =
        ProductSubscription.create()
            .addOrderbook(instrument)
            .addTicker(instrument)
            .addTrades(instrument)
            .build();

    exchange.connect(subscription).blockingAwait();
    InstrumentMetaData instrumentMetaData =
        exchange.getExchangeMetaData().getInstruments().get(instrument);

    assertThat(instrumentMetaData.getVolumeScale()).isNotNull();
    assertThat(instrumentMetaData.getPriceScale()).isNotNull();
    assertThat(instrumentMetaData.getMinimumAmount()).isNotNull();

    disposables.add(
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(instrument)
            .subscribe(
                orderBook -> {
                  if (logOutput) {
                    LOG.info(printOrderBookShortInfo(orderBook));
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
        exchange
            .getStreamingMarketDataService()
            .getOrderBookUpdates(instrument)
            .subscribe(
                orderBookUpdates -> {
                  if (logOutput) {
                    LOG.info("orderBookUpdates subscribe: {}", orderBookUpdates);
                  }
                }));
    disposables.add(
        exchange
            .getStreamingMarketDataService()
            .getTicker(instrument)
            .subscribe(
                ticker -> {
                  if (logOutput) {
                    LOG.info("ticker subscribe: {}", ticker);
                  }
                  assertThat(ticker.getInstrument().equals(instrument)).isTrue();
                  assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
                }));
    disposables.add(
        exchange
            .getStreamingMarketDataService()
            .getTrades(instrument)
            .subscribe(
                trade -> {
                  if (logOutput) {
                    LOG.info("trades subscribe: {}", trade);
                  }
                  assertThat(trade.getInstrument().equals(instrument)).isTrue();
                }));
    Thread.sleep(3000);
    disposables.forEach(Disposable::dispose);
    exchange.disconnect().blockingAwait();
  }
}
