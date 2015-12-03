package com.xeiam.xchange.examples.btce.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.dto.account.BTCEAccountInfo;
import com.xeiam.xchange.btce.v3.service.polling.BTCEAccountServiceRaw;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.btce.BTCEExamplesUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

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
    PollingAccountService accountService = exchange.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("BTCE AccountInfo as String: " + accountInfo.toString());
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the private account functionality (authentication)
    BTCEAccountServiceRaw accountService = (BTCEAccountServiceRaw) exchange.getPollingAccountService();

    // Get the account information
    BTCEAccountInfo accountInfo = accountService.getBTCEAccountInfo(null, null, null, null, null, null, null);
    System.out.println("BTCE Wallet as String: " + accountInfo.toString());
  }

}
