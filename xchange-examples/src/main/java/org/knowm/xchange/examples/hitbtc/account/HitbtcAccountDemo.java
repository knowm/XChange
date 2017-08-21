package org.knowm.xchange.examples.hitbtc.account;

import java.io.IOException;
import java.util.Arrays;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.hitbtc.service.HitbtcAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

public class HitbtcAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = HitbtcExampleUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((HitbtcAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(HitbtcAccountServiceRaw accountService) throws IOException {

    HitbtcBalance[] accountInfo = accountService.getWalletRaw();
    System.out.println(Arrays.toString(accountInfo));
  }
}
