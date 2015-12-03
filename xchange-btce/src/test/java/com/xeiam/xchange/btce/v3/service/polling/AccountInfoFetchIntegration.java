package com.xeiam.xchange.btce.v3.service.polling;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.ExchangeUtils;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Integration tests for Wallet retrieval. For these tests to function, a file 'v3/exchangeConfiguration.json' must be on the classpath and
 * contain valid api and secret keys.
 *
 * @author Peter N. Steinmetz Date: 3/30/15 Time: 7:15 PM
 */
public class AccountInfoFetchIntegration {

  @Test
  public void fetchAccountInfoTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null)
      return; // forces pass if not configuration is available
    PollingAccountService service = exchange.getPollingAccountService();
    assertNotNull(service);
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
  }
}
