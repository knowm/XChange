package com.xeiam.xchange.examples.bter.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bter.dto.account.BTERFunds;
import com.xeiam.xchange.bter.service.polling.BTERPollingAccountServiceRaw;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.bter.BTERDemoUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

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
