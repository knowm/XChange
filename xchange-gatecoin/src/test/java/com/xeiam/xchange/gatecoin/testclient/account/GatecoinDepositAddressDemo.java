
package com.xeiam.xchange.gatecoin.testclient.account;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.gatecoin.dto.account.GatecoinDepositAddress;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;
import com.xeiam.xchange.gatecoin.service.polling.GatecoinAccountServiceRaw;
import com.xeiam.xchange.gatecoin.testclient.GatecoinDemoUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import java.io.IOException;

/**
 *
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

    String depositAddress = accountService.requestDepositAddress("BTC");
    System.out.println("Deposit address: " + depositAddress);
  }

  private static void raw(GatecoinAccountServiceRaw accountService) throws IOException {

    // Get the account information
    GatecoinDepositAddressResult gatecoinDepositAddressResult = accountService.getGatecoinDepositAddress();
  
    for(GatecoinDepositAddress depositAddress : gatecoinDepositAddressResult.getAddresses())
    {
         System.out.println("GatecoinDepositAddess: " + depositAddress.getAddress());
    }
  }
}
    