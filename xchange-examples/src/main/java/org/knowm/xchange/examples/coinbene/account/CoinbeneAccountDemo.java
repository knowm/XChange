package org.knowm.xchange.examples.coinbene.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.dto.account.CoinbeneCoinBalances;
import org.knowm.xchange.coinbene.service.CoinbeneAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.coinbene.CoinbeneDemoUtils;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;

public class CoinbeneAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CoinbeneDemoUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((CoinbeneAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(CoinbeneAccountServiceRaw accountService) throws IOException {

    CoinbeneCoinBalances balances = accountService.getCoinbeneBalances();
    System.out.println(balances);
  }
}
