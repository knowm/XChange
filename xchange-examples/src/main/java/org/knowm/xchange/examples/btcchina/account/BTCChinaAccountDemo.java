package org.knowm.xchange.examples.btcchina.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import org.knowm.xchange.btcchina.dto.account.BTCChinaDeposit;
import org.knowm.xchange.btcchina.dto.account.BTCChinaWithdrawal;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalsResponse;
import org.knowm.xchange.btcchina.service.rest.BTCChinaAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.btcchina.BTCChinaExamplesUtils;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.utils.CertHelper;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Example showing the following:
 *         </p>
 *         <ul>
 *         <li>Connect to BTCChina exchange with authentication</li>
 *         <li>View account balance</li>
 *         <li>Get the bitcoin deposit address</li>
 *         </ul>
 */
public class BTCChinaAccountDemo {

  static Exchange btcchina = BTCChinaExamplesUtils.getExchange();
  static AccountService accountService = btcchina.getAccountService();

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    generic();
    raw();
  }

  public static void generic() throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit address: " + depositAddress);

    // API key has no withdraw rights - returns 401 unauthorized
    // String withdrawResult = accountService.withdrawFunds(new BigDecimal(1).movePointLeft(5), "1CoPAWJtran45gNM21te1xgZqbDd5UqYWB");
    // System.out.println("withdrawResult = " + withdrawResult);
  }

  public static void raw() throws IOException {

    BTCChinaAccountServiceRaw btcChinaAccountService = (BTCChinaAccountServiceRaw) accountService;

    // Get the account information
    BTCChinaResponse<BTCChinaAccountInfo> accountInfo = btcChinaAccountService.getBTCChinaAccountInfo();
    System.out.println("Wallet as String: " + accountInfo.getResult().toString());

    // Get deposits
    BTCChinaGetDepositsResponse depositsResponse = btcChinaAccountService.getDeposits("BTC");
    for (BTCChinaDeposit deposit : depositsResponse.getResult().getDeposits()) {
      System.out.println(deposit);
    }
    depositsResponse = btcChinaAccountService.getDeposits("BTC", false);
    for (BTCChinaDeposit deposit : depositsResponse.getResult().getDeposits()) {
      System.out.println(deposit);
    }

    // Get withdrawals
    BTCChinaGetWithdrawalsResponse withdrawalsResponse = btcChinaAccountService.getWithdrawals("BTC");
    for (BTCChinaWithdrawal withdrawal : withdrawalsResponse.getResult().getWithdrawals()) {
      System.out.println(withdrawal);
    }
    withdrawalsResponse = btcChinaAccountService.getWithdrawals("BTC", false);
    for (BTCChinaWithdrawal withdrawal : withdrawalsResponse.getResult().getWithdrawals()) {
      System.out.println(withdrawal);
    }

    // Get withdrawal
    // BTCChinaGetWithdrawalResponse withdrawalResponse = btcChinaAccountService.getWithdrawal(56102);
    // System.out.println(withdrawalResponse.getResult().getWithdrawal());

    // withdrawalResponse = btcChinaAccountService.getWithdrawal(56102, "BTC");
    // System.out.println(withdrawalResponse.getResult().getWithdrawal());

    // Not implemented for *Raw layer - retrieve from accountInfo
    /*
     * String depositAddress = btcChinaAccountService.requestBTCChinaBitcoinDepositAddress(null, null); System.out.println("Deposit address: " +
     * depositAddress);
     */
    System.out.println("Wallet as String: " + accountInfo.getResult().getProfile().getDepositAddress("btc"));

    // API key has no withdraw rights - returns 401 unauthorized
    // BTCChinaResponse<BTCChinaID> withdrawResult = btcChinaAccountService.withdrawBTCChinaFunds("BTC", new BigDecimal(1).movePointLeft(5), "1CoPAWJtran45gNM21te1xgZqbDd5UqYWB");
    // System.out.println("withdrawResult = " + withdrawResult);
  }
}
