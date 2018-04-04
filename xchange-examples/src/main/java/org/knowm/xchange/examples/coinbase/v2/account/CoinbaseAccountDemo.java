package org.knowm.xchange.examples.coinbase.v2.account;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData.CoinbaseAccount;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbasePaymentMethodsData.CoinbasePaymentMethod;
import org.knowm.xchange.coinbase.v2.service.CoinbaseAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.coinbase.v2.CoinbaseDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class CoinbaseAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CoinbaseDemoUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    // [TODO] generic(accountService);
    raw((CoinbaseAccountService) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Account Info: " + accountInfo);

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit Address: " + depositAddress);

    // String transactionHash = accountService.withdrawFunds(new BigDecimal(".01"), "???");
    // System.out.println("Bitcoin blockchain transaction hash: " + transactionHash);
  }

  public static void raw(CoinbaseAccountService accountService) throws IOException {

    demoAccounts(accountService);

    demoPaymentMethods(accountService);

    // [TODO] CoinbaseMoney balance = accountService.getCoinbaseBalance();
    // System.out.println(balance);

    // [TODO] demoUsers(accountService);

    // [TODO] demoAddresses(accountService);

    // [TODO] demoTransactions(accountService);

    // [TODO] CoinbaseAccountChanges accountChanges = accountService.getCoinbaseAccountChanges();
    // [TODO] System.out.println(accountChanges);

    // [TODO] CoinbaseContacts contacts = accountService.getCoinbaseContacts();
    // [TODO] System.out.println(contacts);

    // [TODO] demoTokens(accountService);

    // [TODO] demoRecurringPayments(accountService);
  }

  private static void demoAccounts(CoinbaseAccountService accountService) throws IOException {

    List<CoinbaseAccount> accounts = accountService.getCoinbaseAccounts();
    for (CoinbaseAccount aux : accounts) {
      System.out.println(aux);
    }
  }

  private static void demoPaymentMethods(CoinbaseAccountService accountService) throws IOException {

    List<CoinbasePaymentMethod> methods = accountService.getCoinbasePaymentMethods();
    for (CoinbasePaymentMethod aux : methods) {
      System.out.println(aux);
    }
  }
}
