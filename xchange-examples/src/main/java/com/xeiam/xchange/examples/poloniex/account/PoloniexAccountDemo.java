package com.xeiam.xchange.examples.poloniex.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.examples.poloniex.PoloniexExamplesUtils;
import com.xeiam.xchange.poloniex.service.polling.PoloniexAccountServiceRaw;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.utils.CertHelper;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange poloniex = PoloniexExamplesUtils.getExchange();
    PollingAccountService accountService = poloniex.getPollingAccountService();

    generic(accountService);
    raw((PoloniexAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    System.out.println("----------GENERIC----------");
    System.out.println(accountService.requestDepositAddress(Currency.BTC));
    System.out.println(accountService.getAccountInfo());
  }

  private static void raw(PoloniexAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW------------");
    System.out.println(accountService.getDepositAddress("BTC"));
    System.out.println(accountService.getWallets());
  }

}
