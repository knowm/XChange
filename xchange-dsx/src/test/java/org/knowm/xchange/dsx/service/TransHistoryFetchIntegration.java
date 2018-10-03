package org.knowm.xchange.dsx.service;

import static org.junit.Assert.assertNotNull;

import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.ExchangeUtils;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryResult;
import org.knowm.xchange.dsx.service.trade.params.DSXTransHistoryParams;

/**
 * Integration tests for transaction history retrieval. For these tests to function, a file
 * 'exchangeConfiguration.json' must be on the classpath and contain valid api and secret keys.
 *
 * @author Mikhail Wall
 */
public class TransHistoryFetchIntegration {

  @Test
  public void defaultFetchTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) return; // forces pass if not configuration is available
    DSXTradeService service = (DSXTradeService) exchange.getTradeService();
    assertNotNull(service);
    DSXTransHistoryParams params = new DSXTransHistoryParams();
    Map<Long, DSXTransHistoryResult> dsxTransHistory = service.getTransHistory(params);
    assertNotNull(dsxTransHistory);
  }
}
