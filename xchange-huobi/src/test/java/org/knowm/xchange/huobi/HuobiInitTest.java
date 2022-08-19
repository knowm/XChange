package org.knowm.xchange.huobi;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.huobi.dto.marketdata.KlineInterval;
import org.knowm.xchange.huobi.service.HuobiMarketDataService;

import java.io.IOException;
import java.util.Arrays;

public class HuobiInitTest {

    @Test
    public void init() throws IOException {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class);

        HuobiMarketDataService marketDataService =
                (HuobiMarketDataService) exchange.getMarketDataService();


        // GET Klines
        Arrays.stream(marketDataService.getKlines(CurrencyPair.BTC_USDT, KlineInterval.m5, 10))
                .forEach(System.out::println);
    }
}
