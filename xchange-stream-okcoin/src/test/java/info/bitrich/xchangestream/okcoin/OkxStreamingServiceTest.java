package info.bitrich.xchangestream.okcoin;

import org.junit.Before;
import org.junit.Test;

public class OkxStreamingServiceTest {

    private OkxStreamingService streamingService;

    @Before
    public void setUp() {
        streamingService = new OkxStreamingService("wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999");
    }

    @Test
    public void testOne() throws Exception {
        streamingService.connect().blockingAwait();
        streamingService.subscribeChannel("tickers").forEach(System.out::println);
        Thread.sleep(31000);
    }
}
