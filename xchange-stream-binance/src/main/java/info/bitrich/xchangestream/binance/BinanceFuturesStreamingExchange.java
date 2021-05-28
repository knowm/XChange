package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.ProductSubscription;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceFuturesAuthenticated;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceFuturesAccountService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.utils.AuthUtils;

public class BinanceFuturesStreamingExchange extends BinanceStreamingExchange {
    private static final String WS_USD_FUTURES_API_BASE_URI = "wss://fstream.binance.com/";

    @Override
    protected BinanceAuthenticated binance() {
        return ExchangeRestProxyBuilder.forInterface(
                BinanceFuturesAuthenticated.class, getExchangeSpecification())
                .build();
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
        spec.setSslUri("https://fapi.binance.com");
        spec.setHost("www.binance.com");
        spec.setPort(80);
        spec.setExchangeName("Binance");
        spec.setExchangeDescription("Binance Exchange.");
        AuthUtils.setApiAndSecretKey(spec, "binance");
        return spec;
    }

    @Override
    protected String wsUri(ExchangeSpecification exchangeSpecification) {
        return WS_USD_FUTURES_API_BASE_URI;
    }

    @Override
    protected BinanceAccountService binanceAccountService(BinanceAuthenticated binance) {
        return new BinanceFuturesAccountService(this, binance, getResilienceRegistries());
    }

    @Override
    protected BinanceStreamingService createStreamingService(ProductSubscription subscription) {
        return new BinanceFuturesStreamingService(streamingUri(subscription), subscription);
    }
}
