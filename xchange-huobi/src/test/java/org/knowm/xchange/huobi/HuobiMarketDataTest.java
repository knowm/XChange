package org.knowm.xchange.huobi;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HuobiMarketDataTest {

    @Test
    public void checkTradesSortingByDate() throws IOException {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class);

        List<Trade> trades = exchange.getMarketDataService().getTrades(new CurrencyPair("BTC/USDT")).getTrades();
        trades.forEach(System.out::println);
        assertThat(trades.get(0).getTimestamp()).isAfterOrEqualTo(trades.get(1).getTimestamp());
    }
}
