package org.knowm.xchange.examples.kraken.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.service.KrakenAccountServiceRaw;

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
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    KrakenAccountServiceRaw rawKrakenAcctService = (KrakenAccountServiceRaw) krakenExchange.getAccountService();
    System.out.println("Balance Info: " + rawKrakenAcctService.getKrakenBalance());
  }
}
