package com.xeiam.xchange.coinmate.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.coinmate.CoinmateExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateBasePollingServiceTest {

    @Test
    public void tickerFetchTest() throws Exception {

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinmateExchange.class.getName());
        PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
        Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
        System.out.println(ticker.toString());
        assertThat(ticker).isNotNull();
    }
}
