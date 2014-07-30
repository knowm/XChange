package com.xeiam.xchange.examples.anx.v2.service.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * Demo requesting account info at ANX
 */
public class BitcoinDepositAddressDemo {

  public static void main(String[] args) throws IOException {

    Exchange ANX = ANXExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    PollingAccountService accountService = ANX.getPollingAccountService();

    // Request a Bitcoin deposit address
    String address = accountService.requestDepositAddress(Currencies.BTC.toString());
    System.out.println("Address to deposit Bitcoins to: " + address);
  }
}