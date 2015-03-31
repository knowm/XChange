package com.xeiam.xchange.btce.v3.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEExchange;
import com.xeiam.xchange.btce.v3.dto.account.BTCEAccountInfo;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Peter N. Steinmetz
 *         Date: 3/30/15
 *         Time: 7:15 PM
 */
public class AccountInfoFetchIntegration {

  @Test
  public void fetchAccountInfoTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(BTCEExchange.class);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    assertNotNull(exchange);
    PollingAccountService service = exchange.getPollingAccountService();
    assertNotNull(service);
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
  }
}
