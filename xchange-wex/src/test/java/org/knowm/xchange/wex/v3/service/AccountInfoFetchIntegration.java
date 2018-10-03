package org.knowm.xchange.wex.v3.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.wex.v3.ExchangeUtils;

/**
 * Integration tests for Wallet retrieval. For these tests to function, a file
 * 'v3/exchangeConfiguration.json' must be on the classpath and contain valid api and secret keys.
 *
 * @author Peter N. Steinmetz Date: 3/30/15 Time: 7:15 PM
 */
public class AccountInfoFetchIntegration {

  @Test
  public void fetchAccountInfoTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) return; // forces pass if not configuration is available
    AccountService service = exchange.getAccountService();
    assertNotNull(service);
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
  }
}
