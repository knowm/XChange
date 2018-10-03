package org.knowm.xchange.examples.anx.v2.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.account.AccountService;

/** Demo requesting account info at ANX */
public class AccountInfoDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    AccountService accountService = anx.getAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();

    System.out.println("Wallet as String: " + accountInfo.toString());
  }
}
