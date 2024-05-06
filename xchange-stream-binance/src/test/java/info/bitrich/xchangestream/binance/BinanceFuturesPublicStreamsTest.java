package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.binance.BinanceStreamingExchange.USE_REALTIME_BOOK_TICKER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.BinanceExchange.FUTURES_URL;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

public class BinanceFuturesPublicStreamsTest {

  StreamingExchange exchange;
  Instrument instrument = new FuturesContract("BTC/USDT/PERP");

  @Before
  public void setup() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BinanceFutureStreamingExchange.class);
    exchangeSpecification.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    exchange
        .connect(
            ProductSubscription.create()
                .addOrderbook(instrument)
                .addTicker(instrument)
                .addFundingRates(instrument)
                .addTrades(instrument)
                .build())
        .blockingAwait();
    InstrumentMetaData instrumentMetaData =
        exchange.getExchangeMetaData().getInstruments().get(instrument);
    assertThat(instrumentMetaData.getVolumeScale()).isNotNull();
    assertThat(instrumentMetaData.getPriceScale()).isNotNull();
    assertThat(instrumentMetaData.getMinimumAmount()).isNotNull();
  }

  @Test
  public void checkOrderBookStream() throws InterruptedException {

    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getOrderBook(instrument)
            .subscribe(
                orderBook -> {
                  assertThat(orderBook.getBids().get(0).getLimitPrice())
                      .isLessThan(orderBook.getAsks().get(0).getLimitPrice());
                  assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(instrument);
                });

    TimeUnit.SECONDS.sleep(3);
    dis.dispose();
  }

  @Test
  public void checkTickerStream() throws InterruptedException {

    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getTicker(instrument)
            .subscribe(ticker -> assertThat(ticker.getInstrument()).isEqualTo(instrument));

    TimeUnit.SECONDS.sleep(3);
    dis.dispose();
  }

  @Test
  public void checkTradesStream() throws InterruptedException {

    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getTrades(instrument)
            .subscribe(trade -> assertThat(trade.getInstrument()).isEqualTo(instrument));

    TimeUnit.SECONDS.sleep(3);
    dis.dispose();
  }

  @Test
  public void checkFundingRateStream() throws InterruptedException {

    Disposable dis =
        exchange
            .getStreamingMarketDataService()
            .getFundingRate(instrument)
            .subscribe(
                fundingRate -> {
                  assertThat(fundingRate.getInstrument()).isEqualTo(instrument);
                  //                    System.out.println(fundingRate);
                });

    TimeUnit.SECONDS.sleep(3);
    dis.dispose();
  }
}
