package com.xeiam.xchange.examples.therock;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.therock.service.polling.TheRockAccountServiceRaw;

public class TheRockAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange loyalbitExchange = TheRockExampleUtils.createTestExchange();

    generic(loyalbitExchange);
    raw(loyalbitExchange);
  }

  private static void generic(Exchange loyalbitExchange) throws IOException {
    AccountInfo accountInfo = loyalbitExchange.getPollingAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo);
  }

  private static void raw(Exchange loyalbitExchange) throws IOException {
    TheRockAccountServiceRaw rawTheRockAcctService = (TheRockAccountServiceRaw) loyalbitExchange.getPollingAccountService();
    System.out.println("Balance Info: " + rawTheRockAcctService.balances());
  }
}
