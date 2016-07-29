package org.knowm.xchange.examples.gatecoin.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.gatecoin.GatecoinDemoUtils;
import org.knowm.xchange.gatecoin.dto.account.GatecoinDepositAddress;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;
import org.knowm.xchange.gatecoin.service.polling.GatecoinAccountServiceRaw;
import org.knowm.xchange.service.polling.account.PollingAccountService;

/**
 * @author sumedha
 */
public class GatecoinDepositAddressDemo {
  public static void main(String[] args) throws IOException {

    Exchange gatecoin = GatecoinDemoUtils.createExchange();
    PollingAccountService accountService = gatecoin.getPollingAccountService();

    generic(accountService);
    raw((GatecoinAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit address: " + depositAddress);
  }

  private static void raw(GatecoinAccountServiceRaw accountService) throws IOException {

    // Get the account information
    GatecoinDepositAddressResult gatecoinDepositAddressResult = accountService.getGatecoinDepositAddress();

    for (GatecoinDepositAddress depositAddress : gatecoinDepositAddressResult.getAddresses()) {
      System.out.println("GatecoinDepositAddess: " + depositAddress.getAddress());
    }
  }
}
