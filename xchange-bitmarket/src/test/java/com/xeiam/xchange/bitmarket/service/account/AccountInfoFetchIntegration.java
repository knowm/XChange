package com.xeiam.xchange.bitmarket.service.account;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.ExchangeUtils;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

/**
 * @author kfonal
 */
public class AccountInfoFetchIntegration {
  Exchange exchange;

  @Before
  public void setUp() {

    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Test
  public void fetchAccountInfoTest() throws Exception {

    if (exchange.getExchangeSpecification().getApiKey() == null || exchange.getExchangeSpecification().getSecretKey() == null) {
      return;  // forces pass if there is no keys passed
    }

    PollingAccountService service = exchange.getPollingAccountService();
    assertNotNull(service);
    //verify account info exists
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
  }

  @Test
  public void fetchDepositTest() throws Exception {

    if (exchange.getExchangeSpecification().getApiKey() == null || exchange.getExchangeSpecification().getSecretKey() == null) {
      return;  // forces pass if there is no keys passed
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
