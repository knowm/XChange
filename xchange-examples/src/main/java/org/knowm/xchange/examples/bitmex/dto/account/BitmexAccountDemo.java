package org.knowm.xchange.examples.bitmex.dto.account;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.bitmex.service.BitmexAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.bitmex.BitmexDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class BitmexAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BitmexDemoUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    marginInfo(accountService);
    walletInfo(accountService);
  }

  private static void marginInfo(AccountService accountService) throws IOException {
    // Get the margin information
    BitmexAccountServiceRaw accountServiceRaw = (BitmexAccountServiceRaw) accountService;
    BitmexAccount bitmexAccountInfo = accountServiceRaw.getBitmexAccountInfo();
    System.out.println("Margin infos response: " + bitmexAccountInfo.toString());
    BitmexMarginAccount xBt =
        accountServiceRaw.getBitmexMarginAccountStatus(Currency.getInstance("XBt"));
    System.out.println(xBt);
    BitmexMarginAccount usd =
        accountServiceRaw.getBitmexMarginAccountStatus(Currency.getInstance("USD"));
    System.out.println(usd);
    List<BitmexMarginAccount> bitmexMarginAccountsStatus =
        accountServiceRaw.getBitmexMarginAccountsStatus();
    System.out.println(bitmexMarginAccountsStatus);
  }

  private static void walletInfo(AccountService accountService) throws IOException {
    BitmexAccountServiceRaw accountServiceRaw = (BitmexAccountServiceRaw) accountService;
    BitmexWallet xBt = accountServiceRaw.getBitmexWallet(Currency.getInstance("XBt"));
    System.out.println(xBt);

    List<BitmexWalletTransaction> walletHistory =
        accountServiceRaw.getBitmexWalletHistory(Currency.getInstance("XBt"));
    System.out.println(walletHistory);
    List<BitmexWalletTransaction> bitmexWalletSummary =
        accountServiceRaw.getBitmexWalletSummary(Currency.getInstance("XBt"));
    System.out.println(bitmexWalletSummary);
  }
}
