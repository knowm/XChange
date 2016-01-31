package com.xeiam.xchange.coinmate.service.polling;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinmate.ExchangeUtils;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Integration tests for Wallet retrieval. For these tests to function, a file 'exchangeConfiguration.json' must be on the classpath and contain
 * valid api and secret keys.
 */
public class AccountInfoFetchIntegration {

  @Test
  public void fetchAccountInfoTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    PollingAccountService service = exchange.getPollingAccountService();
    assertNotNull(service);
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
    System.out.println("Balance BTC: " + info.getWallet().getBalance(Currency.BTC).getTotal());
    System.out.println("Available BTC: " + info.getWallet().getBalance(Currency.BTC).getAvailable());
    System.out.println("Reserved BTC: " + info.getWallet().getBalance(Currency.BTC).getFrozen());
    System.out.println("Balance EUR: " + info.getWallet().getBalance(Currency.EUR).getTotal());
    System.out.println("Available EUR: " + info.getWallet().getBalance(Currency.EUR).getAvailable());
    System.out.println("Reserved EUR: " + info.getWallet().getBalance(Currency.EUR).getFrozen());
  }

  @Test
  public void depositTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    PollingAccountService service = exchange.getPollingAccountService();
    assertNotNull(service);
    String addr = service.requestDepositAddress(Currency.BTC);
    assertNotNull(addr);
    System.out.println("Deposit address: " + addr);
  }

  /*
   * @Test public void withdrawTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingAccountService service =
   * exchange.getPollingAccountService(); assertNotNull(service); // donate to Apache Foundation String txid = service.withdrawFunds("BTC", new
   * BigDecimal("0.01"), "1BtjAzWGLyAavUkbw3QsyzzNDKdtPXk95D"); assertNotNull(txid); System.out.println("Withdrawal txid: " + txid); }
   */
}
