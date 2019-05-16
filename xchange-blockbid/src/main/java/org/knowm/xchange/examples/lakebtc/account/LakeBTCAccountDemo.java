package org.knowm.xchange.examples.lakebtc.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import org.knowm.xchange.lakebtc.service.LakeBTCAccountServiceRaw;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to LakeBTC exchange with authentication
 *   <li>View account balance
 * </ul>
 */
public class LakeBTCAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {
    AccountInfo accountInfo = lakebtcExchange.getAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo.toString());
  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCAccountServiceRaw rawLakeBTCAcctService =
        (LakeBTCAccountServiceRaw) lakeBtcExchange.getAccountService();
    System.out.println("Balance Info: " + rawLakeBTCAcctService.getLakeBTCAccountInfo());
  }
}
