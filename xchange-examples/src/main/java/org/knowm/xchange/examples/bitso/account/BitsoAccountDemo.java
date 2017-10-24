package org.knowm.xchange.examples.bitso.account;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.dto.account.BitsoBalance;
import org.knowm.xchange.bitso.dto.account.BitsoDepositAddress;
import org.knowm.xchange.bitso.service.BitsoAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.bitso.BitsoDemoUtils;
import org.knowm.xchange.service.account.AccountService;

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
public class BitsoAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitso = BitsoDemoUtils.createExchange();
    AccountService accountService = bitso.getAccountService();

    generic(accountService);
    raw((BitsoAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    // Get the account information
    AccountInfo wallet = accountService.getAccountInfo();
    System.out.println("Wallet as String: " + wallet.toString());

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit address: " + depositAddress);

    String withdrawResult = accountService.withdrawFunds(Currency.BTC, new BigDecimal(1).movePointLeft(4), "XXX");
    System.out.println("withdrawResult = " + withdrawResult);
  }

  private static void raw(BitsoAccountServiceRaw accountService) throws IOException {

    BitsoBalance bitsoBalance = accountService.getBitsoBalance();
    System.out.println("Bitso balance: " + bitsoBalance);

    BitsoDepositAddress depositAddress = accountService.getBitsoBitcoinDepositAddress();
    System.out.println("Bitcoin deposit address: " + depositAddress);

    String withdrawResult = accountService.withdrawBitsoFunds(new BigDecimal(1).movePointLeft(4), "XXX");
    System.out.println("Bitso withdrawal response = " + withdrawResult);
  }
}
