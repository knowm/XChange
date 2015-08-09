package com.xeiam.xchange.bitmarket.service.account;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.ExchangeUtils;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author kfonal
 */
public class DepositFetchIntegration {

  private Exchange exchange;

  @Before
  public void setUp() {

    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Test
  public void fetchDepositTest() throws Exception {

    if (exchange.getExchangeSpecification().getApiKey() == null || exchange.getExchangeSpecification().getSecretKey() == null) {
      return; // forces pass if there is no keys passed
    }

    PollingAccountService service = exchange.getPollingAccountService();
    assertNotNull(service);
    //verify address for deposit for BTC exists
    String deposit = service.requestDepositAddress(Currencies.BTC);
    assertNotNull(deposit);
    //verify address for deposit for LTC exists
    deposit = service.requestDepositAddress(Currencies.LTC);
    assertNotNull(deposit);
  }
}
