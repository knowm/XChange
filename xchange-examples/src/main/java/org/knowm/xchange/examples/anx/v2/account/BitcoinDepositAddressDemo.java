package org.knowm.xchange.examples.anx.v2.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.account.AccountService;

/** Demo requesting account info at ANX */
public class BitcoinDepositAddressDemo {

  public static void main(String[] args) throws IOException {

    Exchange ANX = ANXExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    AccountService accountService = ANX.getAccountService();

    // Request a Bitcoin deposit address
    String address = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Address to deposit Bitcoins to: " + address);
  }
}
