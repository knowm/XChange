package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Ignore
public class BinanceFuturesPublicStreamsTest {

    StreamingExchange exchange;
    Instrument instrument = new FuturesContract("BTC/USDT/PERP");

    @Before
    public void setup(){
        exchange = StreamingExchangeFactory.INSTANCE.createExchange(BinanceFutureStreamingExchange.class);
        exchange.connect(ProductSubscription.create().addOrderbook(instrument).addTicker(instrument).addTrades(instrument).build()).blockingAwait();
        InstrumentMetaData instrumentMetaData = exchange.getExchangeMetaData().getInstruments().get(instrument);
        assertThat(instrumentMetaData.getVolumeScale()).isNotNull();
        assertThat(instrumentMetaData.getPriceScale()).isNotNull();
        assertThat(instrumentMetaData.getMinimumAmount()).isNotNull();
    }
    @Test
    public void checkOrderBookStream() throws InterruptedException {

        Disposable dis = exchange.getStreamingMarketDataService().getOrderBook(instrument).subscribe(orderBook -> {
            assertThat(orderBook.getBids().get(0).getLimitPrice()).isLessThan(orderBook.getAsks().get(0).getLimitPrice());
            assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(instrument);
            System.out.println(orderBook.getBids().get(0).getLimitPrice()+"||"+orderBook.getBids().get(0).getOriginalAmount());
        });

        TimeUnit.SECONDS.sleep(3);
        dis.dispose();
    }

    @Test
    public void checkTickerStream() throws InterruptedException {

        Disposable dis = exchange.getStreamingMarketDataService().getTicker(instrument).subscribe(orderBook -> assertThat(orderBook.getInstrument()).isEqualTo(instrument));

        TimeUnit.SECONDS.sleep(3);
        dis.dispose();
    }

    @Test
    public void checkTradesStream() throws InterruptedException {

        Disposable dis = exchange.getStreamingMarketDataService().getTrades(instrument).subscribe(orderBook -> assertThat(orderBook.getInstrument()).isEqualTo(instrument));

        TimeUnit.SECONDS.sleep(3);
        dis.dispose();
    }
}
