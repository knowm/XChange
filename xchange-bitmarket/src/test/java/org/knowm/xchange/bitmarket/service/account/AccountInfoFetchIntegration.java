package org.knowm.xchange.bitmarket.service.account;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.ExchangeUtils;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/** @author kfonal */
public class AccountInfoFetchIntegration {

  private Exchange exchange;

  @Before
  public void setUp() {

    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Test
  public void fetchAccountInfoTest() throws Exception {

    if (exchange.getExchangeSpecification().getApiKey() == null
        || exchange.getExchangeSpecification().getSecretKey() == null) {
      return; // forces pass if there is no keys passed
    }

    AccountService service = exchange.getAccountService();
    assertNotNull(service);
    // verify account info exists
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
  }
}
