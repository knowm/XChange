package org.knowm.xchange.examples.poloniex.account;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.poloniex.PoloniexExamplesUtils;
import org.knowm.xchange.poloniex.service.polling.PoloniexAccountServiceRaw;
import org.knowm.xchange.service.polling.account.PollingAccountService;
import org.knowm.xchange.utils.CertHelper;

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

    System.out.println(accountService.withdrawFunds(Currency.BTC, new BigDecimal("0.03"), "13ArNKUYZ4AmXP4EUzSHMAUsvgGok74jWu"));
  }

  private static void raw(PoloniexAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW------------");
    System.out.println(accountService.getDepositAddress("BTC"));
    System.out.println(accountService.getWallets());
  }

}
