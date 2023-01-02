package info.bitrich.xchangestream.krakenfutures;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

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
    public void checkStreamingOrderBook() throws InterruptedException {
        Disposable dis = exchange.getStreamingMarketDataService().getOrderBook(instrument)
                .subscribe(orderBook -> {
                    assertThat(orderBook.getBids().get(0).getLimitPrice()).isLessThan(orderBook.getAsks().get(0).getLimitPrice());
                    System.out.println("Ask3: "+orderBook.getAsks().get(2).getLimitPrice()+" || "+orderBook.getAsks().get(2).getOriginalAmount());
                    System.out.println("Ask2: "+orderBook.getAsks().get(1).getLimitPrice()+" || "+orderBook.getAsks().get(1).getOriginalAmount());
                    System.out.println("Ask1: "+orderBook.getAsks().get(0).getLimitPrice()+" || "+orderBook.getAsks().get(0).getOriginalAmount());
                    System.out.println("Bid1: "+orderBook.getBids().get(0).getLimitPrice()+" || "+orderBook.getBids().get(0).getOriginalAmount());
                    System.out.println("Bid2: "+orderBook.getBids().get(1).getLimitPrice()+" || "+orderBook.getBids().get(1).getOriginalAmount());
                    System.out.println("Bid3: "+orderBook.getBids().get(2).getLimitPrice()+" || "+orderBook.getBids().get(2).getOriginalAmount());
                });
        TimeUnit.SECONDS.sleep(3);
        dis.dispose();
    }

    @Test
    public void checkStreamingTicker() throws InterruptedException {
        Disposable dis = exchange.getStreamingMarketDataService().getTicker(instrument)
                .subscribe(ticker -> {
                    System.out.println(ticker.toString());
                    assertThat(ticker).isNotNull();
                });
        TimeUnit.SECONDS.sleep(3);
        dis.dispose();
    }

    @Test
    public void checkStreamingFundingRate() throws InterruptedException {
        Disposable dis = exchange.getStreamingMarketDataService().getFundingRate(instrument)
                .subscribe(fundingRate -> {
                    System.out.println(fundingRate.toString());
                    assertThat(fundingRate).isNotNull();
                    assertThat(fundingRate.getFundingRateEffectiveInMinutes()).isLessThan(61);
                });
        TimeUnit.SECONDS.sleep(3);
        dis.dispose();
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
