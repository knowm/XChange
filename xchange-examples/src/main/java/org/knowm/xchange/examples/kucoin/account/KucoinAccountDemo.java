package org.knowm.xchange.examples.kucoin.account;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.examples.kucoin.KucoinExamplesUtils;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.KucoinSimpleResponse;
import org.knowm.xchange.kucoin.dto.account.KucoinCoinBalances;
import org.knowm.xchange.kucoin.dto.account.KucoinDepositAddress;
import org.knowm.xchange.kucoin.service.KucoinAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

public class KucoinAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = KucoinExamplesUtils.getExchange();

    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((KucoinAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    System.out.println("----------GENERIC---------");

    Map<Currency, Balance> balances = accountService.getAccountInfo().getWallet().getBalances();
    System.out.println(balances);

    System.out.println(accountService.requestDepositAddress(Currency.BTC));
  }

  private static void raw(KucoinAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW-----------");

    KucoinSimpleResponse<KucoinCoinBalances> responseBalances =
        accountService.getKucoinBalances(20, 1);
    System.out.println(responseBalances.getData());

    KucoinResponse<KucoinDepositAddress> responseAddress =
        accountService.getKucoinDepositAddress(Currency.BTC);
    System.out.println(responseAddress.getData().getAddress());
  }
}
