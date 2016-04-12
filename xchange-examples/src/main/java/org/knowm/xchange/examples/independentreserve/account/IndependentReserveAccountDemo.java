package org.knowm.xchange.examples.independentreserve.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.independentreserve.IndependentReserveDemoUtils;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBalance;
import org.knowm.xchange.independentreserve.service.polling.IndependentReserveAccountService;
import org.knowm.xchange.service.polling.account.PollingAccountService;

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
    System.out.println("USD balance: " + accountInfo.getWallet().getBalance(Currency.USD).getAvailable());
    System.out.println("BTC balance: " + accountInfo.getWallet().getBalance(Currency.BTC).getAvailable());

  }

  private static void raw(IndependentReserveAccountService accountService) throws IOException {
    // Get the account information
    IndependentReserveBalance balance = accountService.getIndependentReserveBalance();
    System.out.println("Balance: " + balance);
  }
}
