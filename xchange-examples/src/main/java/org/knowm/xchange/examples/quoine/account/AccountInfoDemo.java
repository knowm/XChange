package org.knowm.xchange.examples.quoine.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.quoine.QuoineExamplesUtils;
import org.knowm.xchange.quoine.dto.account.QuoineAccountInfo;
import org.knowm.xchange.quoine.service.polling.QuoineAccountServiceRaw;
import org.knowm.xchange.service.polling.account.PollingAccountService;

/**
 * Demo requesting account info at Quoine
 */
public class AccountInfoDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = QuoineExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    PollingAccountService accountService = exchange.getPollingAccountService();

    generic(accountService);
    raw((QuoineAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();

    System.out.println(accountInfo.toString());
  }

  private static void raw(QuoineAccountServiceRaw quoineAccountServiceRaw) throws IOException {

    QuoineAccountInfo quoineAccountInfo = quoineAccountServiceRaw.getQuoineAccountInfo();

    System.out.println(quoineAccountInfo.toString());
  }

}
