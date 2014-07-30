package com.xeiam.xchange.examples.anx.v2.service.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * Demo requesting account info at MtGox
 */
public class AccountInfoDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    PollingAccountService accountService = anx.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();

    System.out.println("AccountInfo as String: " + accountInfo.toString());
  }
}
