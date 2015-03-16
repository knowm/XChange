package com.xeiam.xchange.examples.clevercoin.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.clevercoin.dto.account.CleverCoinBalance;
import com.xeiam.xchange.clevercoin.dto.account.CleverCoinDepositAddress;
import com.xeiam.xchange.clevercoin.dto.account.CleverCoinWithdrawal;
import com.xeiam.xchange.clevercoin.dto.account.DepositTransaction;
import com.xeiam.xchange.clevercoin.dto.account.WithdrawalRequest;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinOrder;
import com.xeiam.xchange.clevercoin.service.polling.CleverCoinAccountServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.clevercoin.CleverCoinDemoUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to CleverCoin exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li>
 * <li>List unconfirmed deposits (raw interface only)</li>
 * <li>List recent withdrawals (raw interface only)</li>
 * <li>Withdraw a small amount of BTC</li>
 * </ul>
 */
public class CleverCoinAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange clevercoin = CleverCoinDemoUtils.createExchange();
    PollingAccountService accountService = clevercoin.getPollingAccountService();

    generic(accountService);
    //raw((CleverCoinAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    String depositAddress = accountService.requestDepositAddress(Currencies.BTC);
    System.out.println("Deposit address: " + depositAddress);

    String withdrawResult = accountService.withdrawFunds("BTC", new BigDecimal(0.0001), "1FHR3UEvDR7q5BnjwYLukbBiyP48Bikegc");
    System.out.println("withdrawResult = " + withdrawResult);
    
  }

  private static void raw(CleverCoinAccountServiceRaw accountService) throws IOException {

    // Get the account information
	CleverCoinBalance[] CleverCoinBalance = accountService.getCleverCoinBalance();
    System.out.println("Wallets: " + CleverCoinBalance.length);
    for (CleverCoinBalance currencybalance : CleverCoinBalance) {
      System.out.println(currencybalance.toString());
    }

    //System.out.println("CleverCoinDepositAddress address: " + depositAddress);
    final String address = "1FHR3UEvDR7q5BnjwYLukbBiyP48Bikegc";

    CleverCoinWithdrawal withdrawResult = accountService
        .withdrawCleverCoinFunds(new BigDecimal(1).movePointLeft(3), address);
    System.out.println("CleverCoinWithdrawResponse = " + withdrawResult);
  }
}
