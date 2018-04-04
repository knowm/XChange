package org.knowm.xchange.examples.wex.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.wex.WexExamplesUtils;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.wex.v3.dto.account.WexAccountInfo;
import org.knowm.xchange.wex.v3.service.WexAccountServiceRaw;

/** Demo requesting account info at BTC-E */
public class WexAccountInfoDemo {

  public static void main(String[] args) throws IOException {

    Exchange btce = WexExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the private account functionality (authentication)
    AccountService accountService = exchange.getAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Wex AccountInfo as String: " + accountInfo.toString());
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the private account functionality (authentication)
    WexAccountServiceRaw accountService = (WexAccountServiceRaw) exchange.getAccountService();

    // Get the account information
    WexAccountInfo accountInfo = accountService.getBTCEAccountInfo();
    System.out.println("Wex Wallet as String: " + accountInfo.toString());
  }
}
