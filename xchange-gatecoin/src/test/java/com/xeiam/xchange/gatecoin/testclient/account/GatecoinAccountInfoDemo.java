
package com.xeiam.xchange.gatecoin.testclient.account;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.gatecoin.dto.account.GatecoinBalance;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;
import com.xeiam.xchange.gatecoin.service.polling.GatecoinAccountServiceRaw;
import com.xeiam.xchange.gatecoin.testclient.GatecoinDemoUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import java.io.IOException;

/**
 *
 * @author sumedha
 */
public class GatecoinAccountInfoDemo {
   public static void main(String[] args) throws IOException {

    Exchange gatecoin = GatecoinDemoUtils.createExchange();
    PollingAccountService accountService = gatecoin.getPollingAccountService();

    generic(accountService);
    raw((GatecoinAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());   
  }

  private static void raw(GatecoinAccountServiceRaw accountService) throws IOException {

    // Get the account information
    GatecoinBalanceResult gatecoinBalanceResult = accountService.getGatecoinBalance();
    for(GatecoinBalance balance : gatecoinBalanceResult.getBalances())
    {
         System.out.println("GatecoinBalance: " + balance);
    }    

  }
}
