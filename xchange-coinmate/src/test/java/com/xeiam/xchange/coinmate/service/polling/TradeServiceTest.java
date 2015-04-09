package com.xeiam.xchange.coinmate.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinmate.ExchangeUtils;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for AccountInfo retrieval.
 *
 * For these tests to function, a file 'exchangeConfiguration.json' must be on
 * the classpath and contain valid api and secret keys.
 *
 */
public class TradeServiceTest {

    @Test
    public void transactionHistoryTest() throws Exception {
        Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
        if (exchange == null) {
            return;  // forces pass if not configuration is available
        }
        assertNotNull(exchange);
        PollingTradeService service = exchange.getPollingTradeService();
        assertNotNull(service);
        UserTrades trades = service.getTradeHistory();
        assertNotNull(trades);
        System.out.println("Got " + trades.getUserTrades().size() + " trades.");
        for (Trade trade : trades.getTrades()) {
            System.out.println(trade.toString());
        }
    }
}
