package org.knowm.xchange.examples.coinbase.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAccountChanges;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddress;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddresses;
import org.knowm.xchange.coinbase.dto.account.CoinbaseContacts;
import org.knowm.xchange.coinbase.dto.account.CoinbaseRecurringPayment;
import org.knowm.xchange.coinbase.dto.account.CoinbaseRecurringPayments;
import org.knowm.xchange.coinbase.dto.account.CoinbaseToken;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseRequestMoneyRequest;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransactions;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUser;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUsers;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.service.CoinbaseAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.coinbase.CoinbaseDemoUtils;
import org.knowm.xchange.service.account.AccountService;

/**
 * @author jamespedwards42
 */
public class CoinbaseAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    AccountService accountService = coinbase.getAccountService();

    generic(accountService);
    raw((CoinbaseAccountService) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Account Info: " + accountInfo);

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit Address: " + depositAddress);

    // String transactionHash = accountService.withdrawFunds(new BigDecimal(".01"), "XXX");
    // System.out.println("Bitcoin blockchain transaction hash: " + transactionHash);
  }

  public static void raw(CoinbaseAccountService accountService) throws IOException {

    CoinbaseMoney balance = accountService.getCoinbaseBalance();
    System.out.println(balance);

    demoUsers(accountService);

    demoAddresses(accountService);

    demoTransactions(accountService);

    CoinbaseAccountChanges accountChanges = accountService.getCoinbaseAccountChanges();
    System.out.println(accountChanges);

    CoinbaseContacts contacts = accountService.getCoinbaseContacts();
    System.out.println(contacts);

    demoTokens(accountService);

    demoRecurringPayments(accountService);
  }

  private static void demoRecurringPayments(CoinbaseAccountService accountService) throws IOException {

    CoinbaseRecurringPayments recurringPayments = accountService.getCoinbaseRecurringPayments();
    System.out.println(recurringPayments);

    List<CoinbaseRecurringPayment> recurringPaymentsList = recurringPayments.getRecurringPayments();
    if (!recurringPaymentsList.isEmpty()) {
      CoinbaseRecurringPayment recurringPayment = recurringPaymentsList.get(0);
      recurringPayment = accountService.getCoinbaseRecurringPayment(recurringPayment.getId());
      System.out.println(recurringPayment);
    }
  }

  private static void demoUsers(CoinbaseAccountService accountService) throws IOException {

    CoinbaseUsers users = accountService.getCoinbaseUsers();
    System.out.println("Current User: " + users);

    CoinbaseUser user = users.getUsers().get(0);
    user.updateTimeZone("Tijuana").updateNativeCurrency("MXN");
    user = accountService.updateCoinbaseUser(user);
    System.out.println("Updated User: " + user);

    CoinbaseUser newUser = CoinbaseUser.createCoinbaseNewUserWithReferrerId("demo@demo.com", "pass1234", "527d2a1ffedcb8b73b000028");
    String oauthClientId = ""; // optional
    CoinbaseUser createdUser = accountService.createCoinbaseUser(newUser, oauthClientId);
    System.out.println("Newly created user: " + createdUser);
  }

  private static void demoTokens(CoinbaseAccountService accountService) throws IOException {

    CoinbaseToken token = accountService.createCoinbaseToken();
    System.out.println(token);

    boolean isAccepted = accountService.redeemCoinbaseToken(token.getTokenId());
    System.out.println(isAccepted);
  }

  private static void demoAddresses(CoinbaseAccountService accountService) throws IOException {

    CoinbaseAddress receiveAddress = accountService.getCoinbaseReceiveAddress();
    System.out.println(receiveAddress);

    CoinbaseAddress generatedReceiveAddress = accountService.generateCoinbaseReceiveAddress("http://www.example.com/callback", "test");
    System.out.println(generatedReceiveAddress);

    CoinbaseAddresses addresses = accountService.getCoinbaseAddresses();
    System.out.println(addresses);
  }

  private static void demoTransactions(CoinbaseAccountService accountService) throws IOException {

    CoinbaseRequestMoneyRequest moneyRequest = CoinbaseTransaction.createMoneyRequest("xchange@demo.com", "BTC", new BigDecimal(".001"))
        .withNotes("test");
    CoinbaseTransaction pendingTransaction = accountService.requestMoneyCoinbaseRequest(moneyRequest);
    System.out.println(pendingTransaction);

    CoinbaseBaseResponse resendResponse = accountService.resendCoinbaseRequest(pendingTransaction.getId());
    System.out.println(resendResponse);

    CoinbaseBaseResponse cancelResponse = accountService.cancelCoinbaseRequest(pendingTransaction.getId());
    System.out.println(cancelResponse);

    // CoinbaseSendMoneyRequest sendMoneyRequest = CoinbaseTransaction
    // .createSendMoneyRequest("XXX", MoneyUtils.parse("BTC .01"))
    // .withNotes("Demo Money!").withInstantBuy(false).withUserFee("0.0");
    // CoinbaseTransaction sendTransaction = accountService.sendMoney(sendMoneyRequest);
    // System.out.println(sendTransaction);

    // CoinbaseTransaction completedTransaction = accountService.completeRequest("530010d62b342891e2000083");
    // System.out.println(completedTransaction);

    CoinbaseTransactions transactions = accountService.getCoinbaseTransactions();
    System.out.println(transactions);

    if (transactions.getTotalCount() > 0) {
      CoinbaseTransaction transaction = accountService.getCoinbaseTransaction(transactions.getTransactions().get(0).getId());
      System.out.println(transaction);
    }
  }
}
