package org.knowm.xchange.examples.bter.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bter.dto.account.BTERFunds;
import org.knowm.xchange.bter.service.polling.BTERPollingAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.bter.BTERDemoUtils;
import org.knowm.xchange.service.polling.account.PollingAccountService;

public class BTERAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BTERDemoUtils.createExchange();
    PollingAccountService accountService = exchange.getPollingAccountService();

    generic(accountService);
    raw((BTERPollingAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(BTERPollingAccountServiceRaw accountService) throws IOException {

    BTERFunds accountFunds = accountService.getBTERAccountInfo();
    System.out.println(accountFunds);
  }
}
