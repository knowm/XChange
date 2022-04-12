package info.bitrich.xchangestream.okcoin;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

public class OkxStreamingMarketDataServiceTest {

    private OkxStreamingMarketDataService okxStreamingMarketDataService;

    @Before
    public void setUp() {
        OkxStreamingService okxStreamingService = new OkxStreamingService("wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999", null);
        okxStreamingService.connect().blockingAwait();
        okxStreamingMarketDataService = new OkxStreamingMarketDataService(okxStreamingService);
    }

    @Test
    public void testGetTrades() throws InterruptedException {
        Instrument instrument = new FuturesContract(CurrencyPair.BTC_USD, "220415");
        okxStreamingMarketDataService.getTrades(instrument).forEach(System.out::println);
        Thread.sleep(31000);
    }

    @Test
    public void testGetTicker() throws InterruptedException {
        Instrument instrument = new FuturesContract(CurrencyPair.BTC_USD, "220415");
        okxStreamingMarketDataService.getTicker(instrument).forEach(System.out::println);
        Thread.sleep(31000);
    }

    @Test
    public void testGetOrderBook() throws InterruptedException {
        Instrument instrument = CurrencyPair.BTC_USDT;
        okxStreamingMarketDataService.getOrderBook(instrument, "books5").forEach(System.out::println);
        Thread.sleep(31000);
    }

}
