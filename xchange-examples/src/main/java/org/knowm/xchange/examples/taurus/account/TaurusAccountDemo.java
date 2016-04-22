package org.knowm.xchange.examples.taurus.account;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.taurus.TaurusDemoUtils;
import org.knowm.xchange.service.polling.account.PollingAccountService;
import org.knowm.xchange.taurus.dto.account.TaurusBalance;
import org.knowm.xchange.taurus.service.polling.TaurusAccountServiceRaw;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Taurus exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li>
 * <li>List unconfirmed deposits (raw interface only)</li>
 * <li>List recent withdrawals (raw interface only)</li>
 * <li>Withdraw a small amount of BTC</li>
 * </ul>
 */
public class TaurusAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange taurus = TaurusDemoUtils.createExchange();
    PollingAccountService accountService = taurus.getPollingAccountService();

    generic(accountService);
    raw((TaurusAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Wallet as String: " + accountInfo.toString());

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit address: " + depositAddress);

    String withdrawResult = accountService.withdrawFunds(Currency.BTC, new BigDecimal(1).movePointLeft(4), "1MqzGxp6fPdkCyEHe3hZK7rgnSSzHABh7f");
    System.out.println("withdrawResult = " + withdrawResult);
  }

  private static void raw(TaurusAccountServiceRaw accountService) throws IOException {

    // Get the account information
    TaurusBalance taurusBalance = accountService.getTaurusBalance();
    System.out.println("TaurusBalance: " + taurusBalance);

    String depositAddress = accountService.getTaurusBitcoinDepositAddress();
    System.out.println("TaurusDepositAddress address: " + depositAddress);

    String withdrawResult = accountService.withdrawTaurusFunds(new BigDecimal(1).movePointLeft(4), "1MqzGxp6fPdkCyEHe3hZK7rgnSSzHABh7f");
    System.out.println("TaurusBooleanResponse = " + withdrawResult);
  }
}
