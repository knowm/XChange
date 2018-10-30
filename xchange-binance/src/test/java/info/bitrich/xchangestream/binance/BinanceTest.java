package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class BinanceTest {

    @Test
    public void channelCreateUrlTest() {
        ProductSubscription.ProductSubscriptionBuilder builder = ProductSubscription.create();
        builder.addTicker(CurrencyPair.BTC_USD).addTicker(CurrencyPair.DASH_BTC);
        String buildSubscriptionStreams = BinanceStreamingExchange.buildSubscriptionStreams(builder.build());
        Assert.assertEquals("btcusd@ticker/dashbtc@ticker", buildSubscriptionStreams);

        ProductSubscription.ProductSubscriptionBuilder builder2 = ProductSubscription.create();
        builder2.addTicker(CurrencyPair.BTC_USD).addTicker(CurrencyPair.DASH_BTC).addOrderbook(CurrencyPair.ETH_BTC);
        String buildSubscriptionStreams2 = BinanceStreamingExchange.buildSubscriptionStreams(builder2.build());
        Assert.assertEquals("btcusd@ticker/dashbtc@ticker/ethbtc@depth", buildSubscriptionStreams2);
    }


}
