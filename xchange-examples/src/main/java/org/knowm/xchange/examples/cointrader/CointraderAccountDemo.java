package org.knowm.xchange.examples.cointrader;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cointrader.service.polling.CointraderAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;

public class CointraderAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange cointraderExchange = CointraderExampleUtils.createTestExchange();

    generic(cointraderExchange);
    raw(cointraderExchange);
  }

  private static void generic(Exchange cointraderExchange) throws IOException {
    AccountInfo accountInfo = cointraderExchange.getPollingAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo);
  }

  private static void raw(Exchange cointraderExchange) throws IOException {
    CointraderAccountServiceRaw rawCointraderAcctService = (CointraderAccountServiceRaw) cointraderExchange.getPollingAccountService();
    System.out.println("Balance Info: " + rawCointraderAcctService.getCointraderBalance());
  }
}
