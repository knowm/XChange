package org.knowm.xchange.bankera.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bankera.ExchangeUtils;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class AccountInfoFetchIntegration {

  private Exchange exchange;

  @Before
  public void setUp() {

    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Test
  public void fetchAccountInfoTest() throws Exception {
    AccountService service = exchange.getAccountService();
    assertNotNull(service);
    // verify account info exists
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
  }
}
