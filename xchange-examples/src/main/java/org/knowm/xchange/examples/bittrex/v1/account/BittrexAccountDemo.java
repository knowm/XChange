package org.knowm.xchange.examples.bittrex.v1.account;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.service.BittrexAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.examples.bittrex.v1.BittrexExamplesUtils;
import org.knowm.xchange.service.account.AccountService;

public class BittrexAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BittrexExamplesUtils.getExchange();

    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((BittrexAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    System.out.println("----------GENERIC---------");

    Map<Currency, Balance> balances = accountService.getAccountInfo().getWallet().getBalances();
    System.out.println(balances);

    System.out.println(accountService.requestDepositAddress(Currency.BTC));

  }

  private static void raw(BittrexAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW-----------");

    List<BittrexBalance> wallets = accountService.getBittrexAccountInfo();
    System.out.println(wallets);

    System.out.println(accountService.getBittrexDepositAddress("BTC"));

  }
}
