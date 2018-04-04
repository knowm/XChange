package org.knowm.xchange.examples.hitbtc.account;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.service.HitbtcAccountServiceRaw;
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

    List<HitbtcBalance> accountInfo = accountService.getMainBalance();
    System.out.println(Arrays.toString(accountInfo.toArray()));
  }
}
