package org.known.xchange.acx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory;
import org.known.xchange.acx.service.marketdata.AcxMarketDataService;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

public class AcxExchange extends BaseExchange implements Exchange {
    private final SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2014NonceFactory();

    @Override
    protected void initServices() {
        AcxApi api = RestProxyFactory.createProxy(AcxApi.class, getExchangeSpecification().getSslUri());
        AcxMapper mapper = new AcxMapper();
        this.marketDataService = new AcxMarketDataService(api, mapper);
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
        spec.setSslUri("https://acx.io//api/v2/");
        spec.setHost("acx.io");
        spec.setExchangeName("ACX");
        spec.setExchangeDescription("The largest liquidity pool and orderbook of Bitcoin in Australia");
        return spec;
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return nonceFactory;
    }

    public static void main(String[] args) throws IOException {
        Exchange exchange = createTestExchange();
        Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.ETH_AUD);
        System.out.println(ticker);

        OrderBook book = exchange.getMarketDataService().getOrderBook(CurrencyPair.ETH_AUD);
        System.out.println(book);

        Trades trades = exchange.getMarketDataService().getTrades(CurrencyPair.ETH_AUD);
        System.out.println(trades);
    }

    public static Exchange createTestExchange() {
        Exchange btcMarketsExchange = ExchangeFactory.INSTANCE.createExchange(AcxExchange.class.getName());
        ExchangeSpecification spec = btcMarketsExchange.getExchangeSpecification();

        btcMarketsExchange.applySpecification(spec);
        return btcMarketsExchange;
    }
}
