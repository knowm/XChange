package com.xeiam.xchange.examples.anx.v2.service.account;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * Demo requesting account info at MtGox
 */
public class WithdrawalFundsDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    PollingAccountService accountService = anx.getPollingAccountService();

    System.out.println("AccountInfo= " + accountService.getAccountInfo());

    // ANX does not return a transaction id on fund withdrawal at this moment
    String success = accountService.withdrawFunds("BTC", new BigDecimal("0.001"), "1DTZHQF47QzETutRRQVr2o2Rjcku8gBWft");
    System.out.println("result= " + success);
  }

}
