package com.xeiam.xchange.examples.independentreserve.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.examples.independentreserve.IndependentReserveDemoUtils;
import com.xeiam.xchange.independentreserve.dto.account.IndependentReserveBalance;
import com.xeiam.xchange.independentreserve.service.polling.IndependentReserveAccountService;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Author: Kamil Zbikowski Date: 4/10/15
 */
public class IndependentReserveAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange independentReserve = IndependentReserveDemoUtils.createExchange();
    PollingAccountService accountService = independentReserve.getPollingAccountService();

    generic(accountService);
    raw((IndependentReserveAccountService) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    // Get the account information
    Wallet wallet = accountService.getAccountInfo();
    System.out.println("USD balance: " + wallet.getBalance("USD").getAvailable());
    System.out.println("BTC balance: " + wallet.getBalance("BTC").getAvailable());

  }

  private static void raw(IndependentReserveAccountService accountService) throws IOException {
    // Get the account information
    IndependentReserveBalance balance = accountService.getIndependentReserveBalance();
    System.out.println("Balance: " + balance);
  }
}
