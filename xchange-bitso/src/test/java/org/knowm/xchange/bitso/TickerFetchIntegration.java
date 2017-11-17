package org.knowm.xchange.bitso;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import static org.assertj.core.api.Assertions.assertThat;

public class TickerFetchIntegration {

    @Test
    public void tickerFetchTest() throws Exception {

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitsoExchange.class.getName());
        exchange.remoteInit();
        MarketDataService marketDataService = exchange.getMarketDataService();
        Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_MXN);
        System.out.println(ticker.toString());
        assertThat(ticker).isNotNull();
    }
}
