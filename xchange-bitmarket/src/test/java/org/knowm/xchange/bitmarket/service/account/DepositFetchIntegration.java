package org.knowm.xchange.bitmarket.service.account;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.ExchangeUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.polling.account.PollingAccountService;

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
    String deposit = service.requestDepositAddress(Currency.BTC);
    assertNotNull(deposit);
    //verify address for deposit for LTC exists
    deposit = service.requestDepositAddress(Currency.LTC);
    assertNotNull(deposit);
  }
}
