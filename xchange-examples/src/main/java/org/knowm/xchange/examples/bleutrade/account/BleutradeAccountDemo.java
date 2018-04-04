package org.knowm.xchange.examples.bleutrade.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.service.BleutradeAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.bleutrade.BleutradeDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class BleutradeAccountDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange bleutrade = BleutradeDemoUtils.getExchange();
    AccountService accountService = bleutrade.getAccountService();

    // generic(accountService);
    raw((BleutradeAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService)
      throws IOException, InterruptedException {

    System.out.println(accountService.requestDepositAddress(Currency.BTC));
    Thread.sleep(1000);
    System.out.println(accountService.getAccountInfo());
    Thread.sleep(1000);
  }

  private static void raw(BleutradeAccountServiceRaw accountService) throws IOException {

    System.out.println(accountService.getBleutradeBalance("BTC"));
  }
}
