package com.xeiam.xchange.examples.cexio.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.cexio.CexIODemoUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Author: brox Since: 2/6/14
 */

public class AccountInfoDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CexIODemoUtils.createExchange();
    PollingAccountService accountService = exchange.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());
  }

}
