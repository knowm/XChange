package org.knowm.xchange.examples.loyalbit;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.loyalbit.service.LoyalbitAccountServiceRaw;

public class LoyalbitAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange loyalbitExchange = LoyalbitExampleUtils.createTestExchange();

    generic(loyalbitExchange);
    raw(loyalbitExchange);
  }

  private static void generic(Exchange loyalbitExchange) throws IOException {
    AccountInfo accountInfo = loyalbitExchange.getAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo);
  }

  private static void raw(Exchange loyalbitExchange) throws IOException {
    LoyalbitAccountServiceRaw rawLoyalbitAcctService = (LoyalbitAccountServiceRaw) loyalbitExchange.getAccountService();
    System.out.println("Balance Info: " + rawLoyalbitAcctService.getLoyalbitBalance());
  }
}
