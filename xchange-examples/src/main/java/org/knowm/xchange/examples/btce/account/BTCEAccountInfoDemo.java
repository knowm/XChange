package org.knowm.xchange.examples.btce.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.dto.account.BTCEAccountInfo;
import org.knowm.xchange.btce.v3.service.BTCEAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.btce.BTCEExamplesUtils;
import org.knowm.xchange.service.account.AccountService;

/**
 * Demo requesting account info at BTC-E
 */
public class BTCEAccountInfoDemo {

  public static void main(String[] args) throws IOException {

    Exchange btce = BTCEExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the private account functionality (authentication)
    AccountService accountService = exchange.getAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("BTCE AccountInfo as String: " + accountInfo.toString());
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the private account functionality (authentication)
    BTCEAccountServiceRaw accountService = (BTCEAccountServiceRaw) exchange.getAccountService();

    // Get the account information
    BTCEAccountInfo accountInfo = accountService.getBTCEAccountInfo();
    System.out.println("BTCE Wallet as String: " + accountInfo.toString());
  }

}
