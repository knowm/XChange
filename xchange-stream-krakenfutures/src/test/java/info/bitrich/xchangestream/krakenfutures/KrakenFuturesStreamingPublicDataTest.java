package info.bitrich.xchangestream.krakenfutures;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class KrakenFuturesStreamingPublicDataTest {

    StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(KrakenFuturesStreamingExchange.class);
    Instrument instrument = new FuturesContract("BTC/USD/PERP");

    @Before
    public void setUp(){
        InstrumentMetaData metaData = exchange.getExchangeMetaData().getInstruments().get(instrument);
        assertThat(metaData.getPriceScale()).isNotNull();
        assertThat(metaData.getVolumeScale()).isNotNull();
        assertThat(metaData.getMinimumAmount()).isNotNull();
        exchange.connect(ProductSubscription.create().build()).blockingAwait();
    }
    @Test
    public void checkStreamingOrderBook() {
        OrderBook orderBook = exchange.getStreamingMarketDataService().getOrderBook(instrument).blockingFirst();
        assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(instrument);
        assertThat(orderBook.getBids().get(0).getLimitPrice()).isLessThan(orderBook.getAsks().get(0).getLimitPrice());
        assertThat(orderBook.getBids().get(0).getLimitPrice()).isGreaterThan(BigDecimal.ZERO);
        assertThat(orderBook.getBids().get(0).getOriginalAmount()).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    public void checkStreamingTicker() {
        exchange.getStreamingMarketDataService().getTicker(instrument)
                .map(ticker -> {
                    System.out.println(ticker);
                    assertThat(ticker).isNotNull();
                    return ticker;
                })
                .test()
                .assertSubscribed()
                .awaitCount(1)
                .assertValue(ticker -> ticker.getInstrument().equals(instrument))
                .dispose();
    }

    @Test
    public void checkStreamingFundingRate() {
        exchange.getStreamingMarketDataService().getFundingRate(instrument)
                .map(fundingRate -> {
                    System.out.println(fundingRate);
                    assertThat(fundingRate).isNotNull();
                    return fundingRate;
                })
                .test()
                .assertSubscribed()
                .awaitCount(1)
                .assertValue(fundingRate -> fundingRate.getFundingRateEffectiveInMinutes() < 61 && fundingRate.getFundingRate1h() != null)
                .dispose();
    }

    @Test
    public void checkStreamingTrades() throws InterruptedException {
        Disposable dis = exchange.getStreamingMarketDataService().getTrades(instrument)
                .subscribe(trade -> {
                    System.out.println(trade.toString());
                    assertThat(trade).isNotNull();
                    assertThat(trade.getInstrument()).isEqualTo(instrument);
                });
        TimeUnit.SECONDS.sleep(3);
        dis.dispose();
    }
}
