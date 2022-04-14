package info.bitrich.xchangestream.okcoin;

import info.bitrich.xchangestream.okcoin.dto.okx.OkxSubscribeMessage;
import org.junit.Before;
import org.junit.Test;

public class OkxStreamingServiceTest {

    private OkxStreamingService streamingService;

    @Before
    public void setUp() {
        streamingService = new OkxStreamingService("wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999", null);
    }

    @Test
    public void testOne() throws Exception {
        streamingService.connect().blockingAwait();
        OkxSubscribeMessage.SubscriptionTopic t = new OkxSubscribeMessage.SubscriptionTopic("candle1D", null, null , "BTC-USDT");
        OkxSubscribeMessage.SubscriptionTopic t2 = new OkxSubscribeMessage.SubscriptionTopic("tickers", null, null , "BTC-USDT-220930");
        OkxSubscribeMessage.SubscriptionTopic t3 = new OkxSubscribeMessage.SubscriptionTopic("trades", null, null , "BTC-USDT-220930");
        OkxSubscribeMessage m = new OkxSubscribeMessage();
        m.setOp("subscribe");
        m.getArgs().add(t3);
        streamingService.subscribeChannel("trades", m).forEach(System.out::println);
        Thread.sleep(31000);
    }
}
