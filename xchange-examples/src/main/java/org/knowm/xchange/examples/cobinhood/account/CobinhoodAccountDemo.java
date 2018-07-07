package org.knowm.xchange.examples.cobinhood.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.dto.account.CobinhoodCoinBalances;
import org.knowm.xchange.cobinhood.service.CobinhoodAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.cobinhood.CobinhoodDemoUtils;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;

public class CobinhoodAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CobinhoodDemoUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((CobinhoodAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(CobinhoodAccountServiceRaw accountService) throws IOException {

    CobinhoodCoinBalances balances = accountService.getCobinhoodBalances();
    System.out.println(balances);
  }
}
