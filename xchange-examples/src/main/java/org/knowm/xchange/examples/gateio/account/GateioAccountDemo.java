package org.knowm.xchange.examples.gateio.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.gateio.GateioDemoUtils;
import org.knowm.xchange.gateio.dto.account.GateioFunds;
import org.knowm.xchange.gateio.service.GateioAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

public class GateioAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = GateioDemoUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((GateioAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(GateioAccountServiceRaw accountService) throws IOException {

    GateioFunds accountFunds = accountService.getGateioAccountInfo();
    System.out.println(accountFunds);
  }
}
