package com.xeiam.xchange.examples.lakebtc.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCAccountServiceRaw;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to LakeBTC exchange with authentication</li>
 * <li>View account balance</li>
 * </ul>
 */
public class LakeBTCAccountDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {
    AccountInfo accountInfo = lakebtcExchange.getPollingAccountService().getAccountInfo();
    System.out.println("Account Info: " + accountInfo.toString());
  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCAccountServiceRaw rawLakeBTCAcctService = (LakeBTCAccountServiceRaw) lakeBtcExchange.getPollingAccountService();
    System.out.println("Balance Info: " + rawLakeBTCAcctService.getLakeBTCAccountInfo());
  }
}
