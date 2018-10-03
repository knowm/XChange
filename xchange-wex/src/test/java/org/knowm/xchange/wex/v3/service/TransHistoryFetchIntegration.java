package org.knowm.xchange.wex.v3.service;

import static org.junit.Assert.assertNotNull;

import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.wex.v3.ExchangeUtils;
import org.knowm.xchange.wex.v3.dto.trade.WexTransHistoryResult;
import org.knowm.xchange.wex.v3.service.trade.params.WexTransHistoryParams;

/**
 * Integration tests for transaction history retrieval. For these tests to function, a file
 * 'v3/exchangeConfiguration.json' must be on the classpath and contain valid api and secret keys.
 *
 * @author Peter N. Steinmetz Date: 4/3/15 Time: 8:47 AM
 */
public class TransHistoryFetchIntegration {

  @Test
  public void defaultFetchTest() throws Exception {

    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) return; // forces pass if not configuration is available
    WexTradeService service = (WexTradeService) exchange.getTradeService();
    assertNotNull(service);
    WexTransHistoryParams params = new WexTransHistoryParams();
    Map<Long, WexTransHistoryResult> btceTransHistory = service.getTransHistory(params);
    assertNotNull(btceTransHistory);
  }
}
