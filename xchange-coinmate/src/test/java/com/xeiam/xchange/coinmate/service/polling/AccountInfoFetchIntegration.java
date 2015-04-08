package com.xeiam.xchange.coinmate.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinmate.ExchangeUtils;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for AccountInfo retrieval.
 *
 * For these tests to function, a file 'exchangeConfiguration.json' must be on the
 * classpath and contain valid api and secret keys.
 *
 */
public class AccountInfoFetchIntegration {

  @Test
  public void fetchAccountInfoTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange==null) return;  // forces pass if not configuration is available
    assertNotNull(exchange);
    PollingAccountService service = exchange.getPollingAccountService();
    assertNotNull(service);
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
    System.out.println("Balance BTC: " + info.getBalance("BTC"));
    System.out.println("Balance USD: " + info.getBalance("USD"));
  }
}
