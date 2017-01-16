import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.bitstamp.BitstampStreamingExchange;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BistampExchangeTest {
    private static final Logger LOG = LoggerFactory.getLogger(BistampExchangeTest.class);

    public static void main(String[] args) {

        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BitstampStreamingExchange.class.getName());
        exchange.connect().blockingAwait();

//        Disposable subscribe = exchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD).subscribe(orderBook -> {
//            LOG.info("First ask: {}", orderBook.getAsks().get(0));
//            LOG.info("First bid: {}", orderBook.getBids().get(0));
//        });

        exchange.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD).subscribe(ticker -> {
            LOG.info("Ticker {}", ticker);
        });


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOG.info("Dispose");
//        subscribe.dispose();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
