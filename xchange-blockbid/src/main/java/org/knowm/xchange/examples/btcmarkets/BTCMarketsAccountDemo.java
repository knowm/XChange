package org.knowm.xchange.examples.btcmarkets;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.service.BTCMarketsAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;

public class BTCMarketsAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange btcMarketsExchange = BTCMarketsExampleUtils.createTestExchange();

    generic(btcMarketsExchange);
    raw(btcMarketsExchange);
  }

  private static void generic(Exchange btcMarketsExchange) throws IOException {
    AccountInfo accountInfo = btcMarketsExchange.getAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo);
  }

  private static void raw(Exchange btcMarketsExchange) throws IOException {
    BTCMarketsAccountServiceRaw rawBTCMarketsAcctService =
        (BTCMarketsAccountServiceRaw) btcMarketsExchange.getAccountService();
    System.out.println("Balance Info: " + rawBTCMarketsAcctService.getBTCMarketsBalance());
  }
}
