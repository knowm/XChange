package com.xeiam.xchange.btce.v3.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.btce.v3.ExchangeUtils;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETransHistoryResult;
import com.xeiam.xchange.btce.v3.service.polling.trade.params.BTCETransHistoryParams;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for transaction history retrieval.
 *
 * For these tests to function, a file 'v3/exchangeConfiguration.json' must be on the
 * classpath and contain valid api and secret keys.
 *
 * @author Peter N. Steinmetz
 *         Date: 4/3/15
 *         Time: 8:47 AM
 */
public class TransHistoryFetchIntegrationTest {

  @Test
  public void defaultFetchTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange==null) return;  // forces pass if not configuration is available
    BTCETradeService service = (BTCETradeService)exchange.getPollingTradeService();
    assertNotNull(service);
    BTCETransHistoryParams params = new BTCETransHistoryParams();
    Map<Long, BTCETransHistoryResult> btceTransHistory = service.getTransHistory(params);
    assertNotNull(btceTransHistory);
  }
}
