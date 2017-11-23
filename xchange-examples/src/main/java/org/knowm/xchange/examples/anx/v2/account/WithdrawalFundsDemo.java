package org.knowm.xchange.examples.anx.v2.account;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.account.AccountService;

/**
 * Demo requesting account info at ANX
 */
public class WithdrawalFundsDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    AccountService accountService = anx.getAccountService();

    System.out.println("Wallet= " + accountService.getAccountInfo());

    // ANX does not return a transaction id on fund withdrawal at this moment
    String success = accountService.withdrawFunds(Currency.BTC, new BigDecimal("0.001"), "XXX");
    System.out.println("result= " + success);
  }

}
