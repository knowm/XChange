package org.knowm.xchange.examples.kucoin.account;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.kucoin.KucoinExamplesUtils;
import org.knowm.xchange.kucoin.KucoinAccountServiceRaw;
import org.knowm.xchange.kucoin.dto.response.AccountBalancesResponse;
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

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);

    // Not yet implemented
    //    System.out.println(accountService.requestDepositAddress(Currency.BTC));
  }

  private static void raw(KucoinAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW-----------");

    List<AccountBalancesResponse> responseBalances = accountService.getKucoinAccounts();
    System.out.println(responseBalances);

    // Not yet implemented
    //    ????? responseAddress = accountService.getKucoinDepositAddress(Currency.BTC);
    //    System.out.println(responseAddress.?????);
  }
}
