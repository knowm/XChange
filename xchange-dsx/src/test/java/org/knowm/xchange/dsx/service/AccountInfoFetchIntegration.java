package org.knowm.xchange.dsx.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.ExchangeUtils;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/**
 * Integration tests for Wallet retrieval. For these tests to function, a file
 * 'exchangeConfiguration.json' must be on the classpath and contain valid api and secret keys
 *
 * @author Mikhail Wall
 */
public class AccountInfoFetchIntegration {

  @Test
  public void fetchAccountInfoTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    AccountService service = exchange.getAccountService();
    assertNotNull(service);
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
  }
}
