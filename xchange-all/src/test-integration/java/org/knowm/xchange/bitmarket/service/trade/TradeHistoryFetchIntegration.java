package org.knowm.xchange.bitmarket.service.trade;

import static junit.framework.TestCase.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.ExchangeUtils;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;

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

    TradeService service = exchange.getTradeService();
    assertNotNull(service);

    //verify history info exists
    UserTrades trades = service.getTradeHistory(service.createTradeHistoryParams());
    assertNotNull(trades);
  }
}
