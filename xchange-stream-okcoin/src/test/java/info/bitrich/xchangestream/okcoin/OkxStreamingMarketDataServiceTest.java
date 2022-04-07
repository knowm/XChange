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
        OkxStreamingService okxStreamingService = new OkxStreamingService("wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999");
        okxStreamingService.connect().blockingAwait();
        okxStreamingMarketDataService = new OkxStreamingMarketDataService(okxStreamingService);
    }

    @Test
    public void testGetTrades() throws InterruptedException {
        Instrument instrument = new FuturesContract(CurrencyPair.BTC_USDT, "220408");
        okxStreamingMarketDataService.getTrades(instrument).forEach(System.out::println);
        Thread.sleep(31000);
    }

}
