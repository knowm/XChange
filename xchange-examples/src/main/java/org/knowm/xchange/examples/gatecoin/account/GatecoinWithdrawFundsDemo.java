package org.knowm.xchange.examples.gatecoin.account;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.gatecoin.GatecoinDemoUtils;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinWithdrawResult;
import org.knowm.xchange.gatecoin.service.GatecoinAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

/** @author sumedha */
public class GatecoinWithdrawFundsDemo {
  public static void main(String[] args) throws IOException {

    Exchange gatecoin = GatecoinDemoUtils.createExchange();
    AccountService accountService = gatecoin.getAccountService();

    generic(accountService);
    raw((GatecoinAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    String result =
        accountService.withdrawFunds(Currency.BTC, BigDecimal.valueOf(0.1), "AddresssName");
    System.out.println("WithdrawResult: " + result);
  }

  private static void raw(GatecoinAccountServiceRaw accountService) throws IOException {

    // Get the account information
    GatecoinWithdrawResult gatecoinDepositAddressResult =
        accountService.withdrawGatecoinFunds("BTC", BigDecimal.valueOf(0.1), "BATMAN");
    System.out.println(
        "GatecoinDepositAddess: " + gatecoinDepositAddressResult.getResponseStatus());
  }
}
