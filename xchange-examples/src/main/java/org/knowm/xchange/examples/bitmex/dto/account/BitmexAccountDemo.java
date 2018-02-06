package org.knowm.xchange.examples.bitmex.dto.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.dto.BitmexMargin;
import org.knowm.xchange.bitmex.dto.BitmexTransaction;
import org.knowm.xchange.bitmex.dto.BitmexUser;
import org.knowm.xchange.bitmex.service.BitmexAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.bitmex.BitmexDemoUtils;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.util.List;

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
    BitmexUser bitmexAccountInfo = accountServiceRaw.getBitmexAccountInfo();
    System.out.println("Margin infos response: " + bitmexAccountInfo.toString());

    BitmexMargin xBt = accountServiceRaw.getBitmexMarginAccountStatus(new Currency("XBt"));
    System.out.println(xBt);
    BitmexMargin usd = accountServiceRaw.getBitmexMarginAccountStatus(new Currency("USD"));
    System.out.println(usd);
    BitmexMargin bitmexMarginAccountsStatus = accountServiceRaw.getBitmexMarginAccountStatus(Currency.BTC);
    System.out.println(bitmexMarginAccountsStatus);
  }

  private static void walletInfo(AccountService accountService) throws IOException {
    BitmexAccountServiceRaw accountServiceRaw = (BitmexAccountServiceRaw) accountService;
    org.knowm.xchange.bitmex.dto.BitmexWallet xBt = accountServiceRaw.getBitmexWallet(new Currency("XBt"));
    System.out.println(xBt);

    List<BitmexTransaction> walletHistory = accountServiceRaw.getBitmexWalletHistory(new Currency("XBt"));
    System.out.println(walletHistory);
    List<BitmexTransaction> bitmexWalletSummary = accountServiceRaw.getBitmexWalletSummary(new Currency("XBt"));
    System.out.println(bitmexWalletSummary);

  }

}