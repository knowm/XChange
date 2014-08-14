package com.xeiam.xchange.examples.bitstamp.account;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.account.BitstampDepositAddress;
import com.xeiam.xchange.bitstamp.dto.account.BitstampWithdrawal;
import com.xeiam.xchange.bitstamp.service.polling.BitstampAccountServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.bitstamp.BitstampDemoUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitstamp exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li>
 * <li>Withdraw a small amount of BTC</li>
 * </ul>
 */
public class BitstampAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitstamp = BitstampDemoUtils.createExchange();
    PollingAccountService accountService = bitstamp.getPollingAccountService();

    generic(accountService);
    raw((BitstampAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    String depositAddress = accountService.requestDepositAddress(Currencies.BTC);
    System.out.println("Deposit address: " + depositAddress);

    String withdrawResult = accountService.withdrawFunds("BTC", new BigDecimal(1).movePointLeft(4), "1PxYUsgKdw75sdLmM7HYP2p74LEq3mxM6L");
    System.out.println("withdrawResult = " + withdrawResult);
  }

  private static void raw(BitstampAccountServiceRaw accountService) throws IOException {

    // Get the account information
    BitstampBalance bitstampBalance = accountService.getBitstampBalance();
    System.out.println("BitstampBalance as String: " + bitstampBalance.toString());

    BitstampDepositAddress depositAddress = accountService.getBitstampBitcoinDepositAddress();
    System.out.println("BitstampDepositAddress address: " + depositAddress);

    BitstampWithdrawal withdrawResult = accountService.withdrawBitstampFunds(new BigDecimal(1).movePointLeft(4), "1PxYUsgKdw75sdLmM7HYP2p74LEq3mxM6L");
    System.out.println("BitstampBooleanResponse = " + withdrawResult);
  }
}
