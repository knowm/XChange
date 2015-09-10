package com.xeiam.xchange.bitmarket.service.trade;

import static junit.framework.TestCase.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.ExchangeUtils;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * @author kfonal
 */
public class TradeHistoryFetchIntegration {
  private Exchange exchange;

  @Before
  public void setUp() {

    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Test
  public void fetchOpenOrdersTest() throws Exception {

    if (exchange.getExchangeSpecification().getApiKey() == null || exchange.getExchangeSpecification().getSecretKey() == null) {
      return; // forces pass if there is no keys passed
    }

    PollingTradeService service = exchange.getPollingTradeService();
    assertNotNull(service);

    //verify history info exists
    UserTrades trades = service.getTradeHistory(service.createTradeHistoryParams());
    assertNotNull(trades);
  }
}
