package com.xeiam.xchange.examples.hitbtc.account;

import java.io.IOException;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.examples.hitbtc.HitbtcExampleUtils;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcAccountServiceRaw;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class HitbtcAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = HitbtcExampleUtils.createExchange();
    PollingAccountService accountService = exchange.getPollingAccountService();

    generic(accountService);
    raw((HitbtcAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    Wallet wallet = accountService.getAccountInfo();
    System.out.println(wallet);
  }

  private static void raw(HitbtcAccountServiceRaw accountService) throws IOException {

    HitbtcBalance[] accountInfo = accountService.getWalletRaw();
    System.out.println(Arrays.toString(accountInfo));
  }
}
