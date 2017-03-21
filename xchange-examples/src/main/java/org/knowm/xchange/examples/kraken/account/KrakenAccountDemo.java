package org.knowm.xchange.examples.kraken.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundsInfo;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.examples.util.AccountServiceTestUtil;
import org.knowm.xchange.kraken.service.KrakenAccountService;
import org.knowm.xchange.kraken.service.KrakenAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Kraken exchange with authentication</li>
 * <li>View account balance</li>
 * </ul>
 */
public class KrakenAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    AccountInfo accountInfo = krakenExchange.getAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo.toString());

    fundsInfo(krakenExchange.getAccountService());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    KrakenAccountServiceRaw rawKrakenAcctService = (KrakenAccountServiceRaw) krakenExchange.getAccountService();
    System.out.println("Balance Info: " + rawKrakenAcctService.getKrakenBalance());
  }

  private static void fundsInfo(AccountService accountService) throws IOException {
    // Get the funds information
    Date startDate = new Date(System.currentTimeMillis() - (1 * 12 * 30 * 24 * 60 * 60 * 1000L)); // approx 1 year history
    KrakenAccountService.KrakenFundsInfoHistoryParams histParams =
            new KrakenAccountService.KrakenFundsInfoHistoryParams(startDate, null, null, new Currency[] {Currency.BTC, Currency.USD});
    FundsInfo fundsInfo = accountService.getFundsInfo(histParams);
    AccountServiceTestUtil.printFundsInfo(fundsInfo);
  }
}
