package org.knowm.xchange.examples.cexio.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.cexio.CexIODemoUtils;
import org.knowm.xchange.service.polling.account.PollingAccountService;

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
