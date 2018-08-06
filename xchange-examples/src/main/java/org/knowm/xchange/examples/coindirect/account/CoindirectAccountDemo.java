package org.knowm.xchange.examples.coindirect.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectExchange;
import org.knowm.xchange.coindirect.service.CoindirectAccountService;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.coindirect.CoindirectDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class CoindirectAccountDemo {
  public static void main(String[] args) throws IOException {
    Exchange exchange = CoindirectDemoUtils.createExchange();

    /* create a data service from the exchange */
    AccountService accountService = exchange.getAccountService();

    generic(exchange, accountService);
    raw((CoindirectExchange) exchange, (CoindirectAccountService) accountService);
  }

  private static void generic(Exchange exchange, AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Account Info: " + accountInfo);

    //        String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    //        System.out.println("Deposit Address: " + depositAddress);

    // String transactionHash = accountService.withdrawFunds(new BigDecimal(".01"), "XXX");
    // System.out.println("Bitcoin blockchain transaction hash: " + transactionHash);
  }

  public static void raw(CoindirectExchange exchange, CoindirectAccountService accountService)
      throws IOException {}
}
