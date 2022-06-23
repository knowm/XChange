package org.knowm.xchange.bybit;


import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.bybit.service.BybitMarketDataService;
import org.knowm.xchange.bybit.service.BybitTradeService;

public class BybitExchange extends BaseExchange implements Exchange {

    @Override
    protected void initServices() {
        this.marketDataService = new BybitMarketDataService(this);
        this.tradeService = new BybitTradeService(this);
        this.accountService = new BybitAccountService(this);
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
        exchangeSpecification.setSslUri("https://api.bybit.com");
        exchangeSpecification.setHost("bybit.com");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("Bybit");
        exchangeSpecification.setExchangeDescription("BYBIT");
        return exchangeSpecification;
    }
}
