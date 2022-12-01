package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.junit.Test;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BinanceFuturesPublicStreamsTest {

    StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BinanceFutureStreamingExchange.class);
    Instrument instrument = new FuturesContract("BTC/USDT/PERP");

    @Test
    public void checkOrderBookStream() throws InterruptedException {
        exchange.connect(ProductSubscription.create().addOrderbook(instrument).build()).blockingAwait();

        exchange.getStreamingMarketDataService().getOrderBook(instrument).subscribe(orderBook -> {
            System.out.println(orderBook.toString());
            assertThat(orderBook.getBids().get(0).getLimitPrice()).isLessThan(orderBook.getAsks().get(0).getLimitPrice());
        });

        TimeUnit.SECONDS.sleep(3);
    }
}
