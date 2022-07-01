package org.knowm.xchange.examples.bithumb.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.service.BithumbAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.bithumb.BithumbDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class BithumbAccountDemo {

  public static void main(String[] args) throws IOException {

    final Exchange exchange = BithumbDemoUtils.createExchange();
    final AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((BithumbAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    System.out.println("----------GENERIC----------");
    System.out.println(accountService.getAccountInfo());
    System.out.println(accountService.requestDepositAddress(Currency.BTC));
  }

  private static void raw(BithumbAccountServiceRaw accountServiceRaw) throws IOException {

    System.out.println("----------RAW----------");
    System.out.println(accountServiceRaw.getBithumbAddress());
    System.out.println(accountServiceRaw.getBithumbBalance());
    System.out.println(accountServiceRaw.getBithumbWalletAddress(Currency.BTC));
  }
}
