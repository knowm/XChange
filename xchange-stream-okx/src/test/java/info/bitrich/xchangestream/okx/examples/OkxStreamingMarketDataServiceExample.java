package info.bitrich.xchangestream.okx.examples;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.okx.OkxStreamingExchange;
import info.bitrich.xchangestream.okx.OkxStreamingMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

public class OkxStreamingMarketDataServiceExample {

    private static OkxStreamingMarketDataService okxStreamingMarketDataService;

    public static void main(String[] args) throws InterruptedException {
        setUp();

        testGetTrades();

        testGetTicker();

        testGetOrderBook();

        Thread.sleep(5000);

        System.exit(0);
    }

    public static void setUp() {
        StreamingExchange okxExchange = StreamingExchangeFactory.INSTANCE.createExchange(OkxStreamingExchange.class);
        okxExchange.connect().blockingAwait();
        okxStreamingMarketDataService = (OkxStreamingMarketDataService) okxExchange.getStreamingMarketDataService();
    }

    public static void testGetTrades() throws InterruptedException {
        Instrument instrument = CurrencyPair.BTC_USDT;
        okxStreamingMarketDataService.getTrades(instrument).forEach(System.out::println);
        Thread.sleep(3000);
    }

    public static  void testGetTicker() throws InterruptedException {
        Instrument instrument = CurrencyPair.BTC_USDT;
        okxStreamingMarketDataService.getTicker(instrument).forEach(System.out::println);
        Thread.sleep(3000);
    }

    public static void testGetOrderBook() throws InterruptedException {
        Instrument instrument = CurrencyPair.BTC_USDT;
        okxStreamingMarketDataService.getOrderBook(instrument, "books5").forEach(System.out::println);
        Thread.sleep(3000);
    }

}
