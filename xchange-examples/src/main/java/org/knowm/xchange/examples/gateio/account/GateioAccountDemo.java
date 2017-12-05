package org.knowm.xchange.examples.gateio.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bter.dto.account.BTERFunds;
import org.knowm.xchange.bter.service.BTERAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.gateio.GateioDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class GateioAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = GateioDemoUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((BTERAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(BTERAccountServiceRaw accountService) throws IOException {

    BTERFunds accountFunds = accountService.getBTERAccountInfo();
    System.out.println(accountFunds);
  }
}
