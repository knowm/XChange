package org.knowm.xchange.examples.cryptonit2.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.dto.account.*;
import org.knowm.xchange.cryptonit2.service.CryptonitAccountServiceRaw;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.cryptonit2.CryptonitDemoUtils;
import org.knowm.xchange.service.account.AccountService;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Bitstamp exchange with authentication
 *   <li>View account balance
 *   <li>Get the bitcoin deposit address
 *   <li>List unconfirmed deposits (raw interface only)
 *   <li>List recent withdrawals (raw interface only)
 *   <li>Withdraw a small amount of BTC
 * </ul>
 */
public class CryptonitAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitstamp = CryptonitDemoUtils.createExchange();
    AccountService accountService = bitstamp.getAccountService();

    generic(accountService);
    raw((CryptonitAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

  }

  private static void raw(CryptonitAccountServiceRaw accountService) throws IOException {

    // Get the account information
    CryptonitBalance bitstampBalance = accountService.getCryptonitBalance();
    System.out.println("CryptonitBalance: " + bitstampBalance);
    
  }
}
