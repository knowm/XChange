package com.xeiam.xchange.examples.anx.v2.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Demo requesting account info at ANX
 */
public class BitcoinDepositAddressDemo {

  public static void main(String[] args) throws IOException {

    Exchange ANX = ANXExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    PollingAccountService accountService = ANX.getPollingAccountService();

    // Request a Bitcoin deposit address
    String address = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Address to deposit Bitcoins to: " + address);
  }
}