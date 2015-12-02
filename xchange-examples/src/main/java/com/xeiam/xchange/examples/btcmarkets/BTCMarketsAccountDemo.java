package com.xeiam.xchange.examples.btcmarkets;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcmarkets.service.polling.BTCMarketsAccountServiceRaw;
import com.xeiam.xchange.dto.account.Wallet;

public class BTCMarketsAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange btcMarketsExchange = BTCMarketsExampleUtils.createTestExchange();

    generic(btcMarketsExchange);
    raw(btcMarketsExchange);
  }

  private static void generic(Exchange btcMarketsExchange) throws IOException {
    Wallet wallet = btcMarketsExchange.getPollingAccountService().getAccountInfo();
    System.out.println("Account Info: " + wallet);
  }

  private static void raw(Exchange btcMarketsExchange) throws IOException {
    BTCMarketsAccountServiceRaw rawBTCMarketsAcctService = (BTCMarketsAccountServiceRaw) btcMarketsExchange.getPollingAccountService();
    System.out.println("Balance Info: " + rawBTCMarketsAcctService.getBTCMarketsBalance());
  }
}
