package org.knowm.xchange.jubi.service.account;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.jubi.JubiExchange;
import org.knowm.xchange.service.account.AccountService;

/**
 * Created by Dzf on 2017/7/10.
 */
public class BalanceFetchIntegration {

  @Test
  public void BalanceFetchTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchangeWithApiKeys(JubiExchange.class.getName(),
        "your public api key", "your secret api key");
    AccountService accountService = exchange.getAccountService();
    AccountInfo info = accountService.getAccountInfo();
    System.out.println(info.toString());
  }

}
