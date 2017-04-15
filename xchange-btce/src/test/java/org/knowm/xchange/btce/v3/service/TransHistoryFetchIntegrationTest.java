package org.knowm.xchange.btce.v3.service;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.ExchangeUtils;
import org.knowm.xchange.btce.v3.dto.trade.BTCETransHistoryResult;
import org.knowm.xchange.btce.v3.service.trade.params.BTCETransHistoryParams;

/**
 * Integration tests for transaction history retrieval. For these tests to function, a file 'v3/exchangeConfiguration.json' must be on the classpath
 * and contain valid api and secret keys.
 *
 * @author Peter N. Steinmetz Date: 4/3/15 Time: 8:47 AM
 */
public class TransHistoryFetchIntegrationTest {

  @Test
  public void defaultFetchTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null)
      return; // forces pass if not configuration is available
    BTCETradeService service = (BTCETradeService) exchange.getTradeService();
    assertNotNull(service);
    BTCETransHistoryParams params = new BTCETransHistoryParams();
    Map<Long, BTCETransHistoryResult> btceTransHistory = service.getTransHistory(params);
    assertNotNull(btceTransHistory);
  }
}
