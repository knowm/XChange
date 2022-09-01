package org.knowm.xchange.ftx.dto.marketdata;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.ftx.FtxExchange;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FtxMarketDataTest {

    @Test
    public void checkTradesSortingByDate() throws IOException {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(FtxExchange.class);

        List<Trade> trades = exchange.getMarketDataService().getTrades(new CurrencyPair("BTC/USDT")).getTrades();
        assertThat(trades.get(0).getTimestamp()).isAfterOrEqualTo(trades.get(1).getTimestamp());
    }
}
