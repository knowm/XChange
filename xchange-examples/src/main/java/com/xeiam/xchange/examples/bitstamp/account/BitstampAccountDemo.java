package com.xeiam.xchange.examples.bitstamp.account;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampExchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.account.polling.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitstamp exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li>
 * </ul>
 * <p>
 * Provide the username and password as the first two program arguments.
 * </p>
 */
public class BitstampAccountDemo {

  public static void main(String[] args) {

    ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
    exSpec.setUserName(args[0]);
    exSpec.setPassword(args[1]);

    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(exSpec);

    PollingAccountService accountService = bitstamp.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    String depositAddress = accountService.requestBitcoinDepositAddress(null, null);
    System.out.println("Deposit address: " + depositAddress);

    String withdrawResult = accountService.withdrawFunds(new BigDecimal(1).movePointLeft(4), "13nKTRtz9e7SaVZzqYRTbk6PgizdhvoUDN");
    System.out.println("withdrawResult = " + withdrawResult);
  }
}
