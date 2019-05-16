package org.knowm.xchange.examples.gatecoin.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.gatecoin.GatecoinDemoUtils;
import org.knowm.xchange.gatecoin.dto.account.GatecoinBalance;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;
import org.knowm.xchange.gatecoin.service.GatecoinAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

/** @author sumedha */
public class GatecoinAccountInfoDemo {
  public static void main(String[] args) throws IOException {

    Exchange gatecoin = GatecoinDemoUtils.createExchange();
    AccountService accountService = gatecoin.getAccountService();

    generic(accountService);
    raw((GatecoinAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Wallet: " + accountInfo);
    System.out.println(
        "BTC balance: " + accountInfo.getWallet().getBalance(Currency.BTC).getAvailable());
  }

  private static void raw(GatecoinAccountServiceRaw accountService) throws IOException {

    // Get the account information
    GatecoinBalanceResult gatecoinBalanceResult = accountService.getGatecoinBalance();
    for (GatecoinBalance balance : gatecoinBalanceResult.getBalances()) {
      System.out.println("GatecoinBalance: " + balance);
    }
  }
}
