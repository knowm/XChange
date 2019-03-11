package org.knowm.xchange.examples.bibox.account;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.dto.account.BiboxCoin;
import org.knowm.xchange.bibox.service.BiboxAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.examples.bibox.BiboxExamplesUtils;
import org.knowm.xchange.service.account.AccountService;

public class BiboxAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BiboxExamplesUtils.getExchange();

    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((BiboxAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    System.out.println("----------GENERIC---------");

    Map<Currency, Balance> balances = accountService.getAccountInfo().getWallet().getBalances();
    System.out.println(balances);
  }

  private static void raw(BiboxAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW-----------");

    List<BiboxCoin> balances = accountService.getBiboxAccountInfo();
    System.out.println(balances);
  }
}
