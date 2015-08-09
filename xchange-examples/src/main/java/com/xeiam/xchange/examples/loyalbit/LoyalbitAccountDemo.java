package com.xeiam.xchange.examples.loyalbit;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.loyalbit.service.polling.LoyalbitAccountServiceRaw;

public class LoyalbitAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange loyalbitExchange = LoyalbitExampleUtils.createTestExchange();

    generic(loyalbitExchange);
    raw(loyalbitExchange);
  }

  private static void generic(Exchange loyalbitExchange) throws IOException {
    AccountInfo accountInfo = loyalbitExchange.getPollingAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo);
  }

  private static void raw(Exchange loyalbitExchange) throws IOException {
    LoyalbitAccountServiceRaw rawLoyalbitAcctService = (LoyalbitAccountServiceRaw) loyalbitExchange.getPollingAccountService();
    System.out.println("Balance Info: " + rawLoyalbitAcctService.getLoyalbitBalance());
  }
}
