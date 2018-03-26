package org.knowm.xchange.coinmate.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.ExchangeUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/**
 * Integration tests for Wallet retrieval. For these tests to function, a file 'exchangeConfiguration.json' must be on the classpath and contain valid
 * api and secret keys.
 */
public class AccountInfoFetchIntegration {

  @Test
  public void fetchAccountInfoTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    AccountService service = exchange.getAccountService();
    assertNotNull(service);
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
    Currency[] currencies = {Currency.BTC, Currency.EUR, Currency.CZK};
    for (Currency curr : currencies) {
      System.out.println(curr.toString() + " --- ");
      System.out.println("Balance : " + info.getWallet().getBalance(curr).getTotal());
      System.out.println("Available : " + info.getWallet().getBalance(curr).getAvailable());
      System.out.println("Reserved : " + info.getWallet().getBalance(curr).getFrozen());
    }
  }

  @Test
  public void depositTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    AccountService service = exchange.getAccountService();
    assertNotNull(service);
    String addr = service.requestDepositAddress(Currency.BTC);
    assertNotNull(addr);
    System.out.println("Deposit address: " + addr);
  }

  /*
   * @Test public void withdrawTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); AccountService service =
   * exchange.getAccountService(); assertNotNull(service); // donate to Apache Foundation String txid = service.withdrawFunds("BTC", new
   * BigDecimal("0.01"), "XXX"); assertNotNull(txid); System.out.println("Withdrawal txid: " + txid); }
   */
}
