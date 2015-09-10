package com.xeiam.xchange.examples.independentreserve.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
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
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

  }

  private static void raw(IndependentReserveAccountService accountService) throws IOException {
    // Get the account information
    IndependentReserveBalance bitstampBalance = accountService.getIndependentReserveBalance();
    System.out.println("BitstampBalance: " + bitstampBalance);
  }
}
