package org.knowm.xchange.examples.cryptonit2.account;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitBalance;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitWithdrawal;
import org.knowm.xchange.cryptonit2.service.CryptonitAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
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

    /*
    Api not implemented or uses other specification?
    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit address: " + depositAddress);*/

    String withdrawResult =
        accountService.withdrawFunds(Currency.BTC, new BigDecimal(1).movePointLeft(2), "XXX");
    System.out.println("withdrawResult = " + withdrawResult);
  }

  private static void raw(CryptonitAccountServiceRaw accountService) throws IOException {

    // Get the account information
    CryptonitBalance cryptonitBalance = accountService.getCryptonitBalance();
    System.out.println("CryptonitBalance: " + cryptonitBalance);

    /*
    Api not implemented or uses other specification?
    CryptonitDepositAddress depositAddress = accountService.getCryptonitBitcoinDepositAddress();
     System.out.println("CryptonitDepositAddress address: " + depositAddress);*/

    /*
    Api not implemented or uses other specification?
    final List<DepositTransaction> unconfirmedDeposits = accountService.getUnconfirmedDeposits();
     System.out.println("Unconfirmed deposits:");
     for (DepositTransaction unconfirmedDeposit : unconfirmedDeposits) {
       System.out.println(unconfirmedDeposit);
     }*/

    /*
    Api not implemented or uses other specification?
    final List<WithdrawalRequest> withdrawalRequests =
          accountService.getWithdrawalRequests(50000000l);
      System.out.println("Withdrawal requests:");
      for (WithdrawalRequest unconfirmedDeposit : withdrawalRequests) {
        System.out.println(unconfirmedDeposit);
      }*/

    CryptonitWithdrawal withdrawResult =
        accountService.withdrawCrypto(new BigDecimal(1).movePointLeft(4), "XXX", Currency.BTC);
    System.out.println("CryptonitBooleanResponse = " + withdrawResult);
  }
}
