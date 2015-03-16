package com.xeiam.xchange.examples.bitcointoyou.account;

import java.io.IOException;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.account.BitcoinToYouBalance;
import com.xeiam.xchange.bitcointoyou.service.polling.BitcoinToYouAccountServiceRaw;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.bitcointoyou.InteractiveAuthenticatedExchange;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to BitcoinToYou exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li>
 * <li>Withdraw a small amount of BTC</li>
 * </ul>
 *
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class BitcoinToYouAccountDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange bitcoinToYou = InteractiveAuthenticatedExchange.createInstanceFromDefaultInput();
    PollingAccountService accountService = bitcoinToYou.getPollingAccountService();

    generic(accountService);
    Thread.sleep(2000);
    raw((BitcoinToYouAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());
  }

  private static void raw(BitcoinToYouAccountServiceRaw accountService) throws IOException {

    // Get the account information
    BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> bitcoinToYouBalance = accountService.getBitcoinToYouBalance();
    System.out.println("BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> as String: " + bitcoinToYouBalance.toString());

    if (bitcoinToYouBalance.getSuccess() == 1) {
      System.out.println("BitcoinToYouBalance[] as String: " + Arrays.toString(bitcoinToYouBalance.getTheReturn()));
    }
  }
}
