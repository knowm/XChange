package org.knowm.xchange.examples.therock.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.therock.TheRockExampleUtils;
import org.knowm.xchange.therock.service.TheRockAccountServiceRaw;

public class TheRockAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange loyalbitExchange = TheRockExampleUtils.createTestExchange();

    generic(loyalbitExchange);
    raw(loyalbitExchange);
  }

  private static void generic(Exchange loyalbitExchange) throws IOException {
    AccountInfo accountInfo = loyalbitExchange.getAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo);
  }

  private static void raw(Exchange loyalbitExchange) throws IOException {
    TheRockAccountServiceRaw rawTheRockAcctService =
        (TheRockAccountServiceRaw) loyalbitExchange.getAccountService();
    System.out.println("Balance Info: " + rawTheRockAcctService.balances());
  }
}
