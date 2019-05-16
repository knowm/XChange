package org.knowm.xchange.examples.independentreserve.account;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.examples.independentreserve.IndependentReserveDemoUtils;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBalance;
import org.knowm.xchange.independentreserve.service.IndependentReserveAccountService;
import org.knowm.xchange.service.account.AccountService;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class IndependentReserveAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange independentReserve = IndependentReserveDemoUtils.createExchange();
    AccountService accountService = independentReserve.getAccountService();

    generic(accountService);
    raw((IndependentReserveAccountService) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Account balances: (available / available for withdrawal / total)");

    Wallet wallet = accountInfo.getWallet();
    Map<Currency, Balance> balances = wallet.getBalances();
    for (Map.Entry<Currency, Balance> entry : balances.entrySet()) {
      Balance balance = entry.getValue();
      System.out.format(
          "%s balance: %s / %s / %s\n",
          entry.getKey().getCurrencyCode(),
          balance.getAvailable(),
          balance.getAvailableForWithdrawal(),
          balance.getTotal());
    }
  }

  private static void raw(IndependentReserveAccountService accountService) throws IOException {
    // Get the account information
    IndependentReserveBalance balance = accountService.getIndependentReserveBalance();
    System.out.println("Balance: " + balance);
  }
}
