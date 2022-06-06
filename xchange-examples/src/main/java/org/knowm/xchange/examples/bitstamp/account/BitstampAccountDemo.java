package org.knowm.xchange.examples.bitstamp.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.account.DepositTransaction;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;
import org.knowm.xchange.bitstamp.service.BitstampAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.bitstamp.BitstampDemoUtils;
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
public class BitstampAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitstamp = BitstampDemoUtils.createExchange();
    AccountService accountService = bitstamp.getAccountService();

    generic(accountService);
    raw((BitstampAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit address: " + depositAddress);

    String withdrawResult =
        accountService.withdrawFunds(Currency.BTC, new BigDecimal(1).movePointLeft(4), "XXX");
    System.out.println("withdrawResult = " + withdrawResult);
  }

  private static void raw(BitstampAccountServiceRaw accountService) throws IOException {

    // Get the account information
    BitstampBalance bitstampBalance = accountService.getBitstampBalance();
    System.out.println("BitstampBalance: " + bitstampBalance);

    BitstampDepositAddress depositAddress = accountService.getBitstampBitcoinDepositAddress();
    System.out.println("BitstampDepositAddress address: " + depositAddress);

    final List<DepositTransaction> unconfirmedDeposits = accountService.getUnconfirmedDeposits();
    System.out.println("Unconfirmed deposits:");
    for (DepositTransaction unconfirmedDeposit : unconfirmedDeposits) {
      System.out.println(unconfirmedDeposit);
    }

    final List<WithdrawalRequest> withdrawalRequests =
        accountService.getWithdrawalRequests(50000000l);
    System.out.println("Withdrawal requests:");
    for (WithdrawalRequest unconfirmedDeposit : withdrawalRequests) {
      System.out.println(unconfirmedDeposit);
    }

    BitstampWithdrawal withdrawResult =
        accountService.withdrawBitstampFunds(
            Currency.BTC, new BigDecimal(1).movePointLeft(4), "XXX");
    System.out.println("BitstampBooleanResponse = " + withdrawResult);
  }
}
