package com.xeiam.xchange.examples.bitso.account;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.dto.account.BitsoBalance;
import com.xeiam.xchange.bitso.dto.account.BitsoDepositAddress;
import com.xeiam.xchange.bitso.service.polling.BitsoAccountServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.bitso.BitsoDemoUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitso exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li>
 * <li>Withdraw a small amount of BTC</li>
 * </ul>
 */
public class  BitsoAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitso = BitsoDemoUtils.createExchange();
    PollingAccountService accountService = bitso.getPollingAccountService();

    generic(accountService);
    raw((BitsoAccountServiceRaw) accountService);
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

  private static void raw(BitsoAccountServiceRaw accountService) throws IOException {

    BitsoBalance bitsoBalance = accountService.getBitsoBalance();
    System.out.println("Bitso balance: " + bitsoBalance);

    BitsoDepositAddress depositAddress = accountService.getBitsoBitcoinDepositAddress();
    System.out.println("Bitcoin deposit address: " + depositAddress);

    String withdrawResult = accountService
        .withdrawBitsoFunds(new BigDecimal(1).movePointLeft(4), "1PxYUsgKdw75sdLmM7HYP2p74LEq3mxM6L");
    System.out.println("Bitso withdrawal response = " + withdrawResult);
  }
}
