package org.knowm.xchange.coinmate.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.ExchangeUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;

/**
 * Integration tests for Wallet retrieval. For these tests to function, a file
 * 'exchangeConfiguration.json' must be on the classpath and contain valid api and secret keys.
 */
class AccountInfoFetchIntegration {

  @Test
  void fetchAccountInfoTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertThat(exchange).isNotNull();
    AccountService service = exchange.getAccountService();
    assertThat(service).isNotNull();
    AccountInfo info = service.getAccountInfo();
    assertThat(info).isNotNull();
    Currency[] currencies = {Currency.BTC, Currency.EUR, Currency.CZK};
    for (Currency curr : currencies) {
      System.out.println(curr.toString() + " --- ");
      System.out.println("Balance : " + info.getWallet().getBalance(curr).getTotal());
      System.out.println("Available : " + info.getWallet().getBalance(curr).getAvailable());
      System.out.println("Reserved : " + info.getWallet().getBalance(curr).getFrozen());
    }
  }

  @Test
  void depositTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertThat(exchange).isNotNull();
    AccountService service = exchange.getAccountService();
    assertThat(service).isNotNull();
    String addr = service.requestDepositAddress(Currency.BTC);
    assertThat(addr).isNotNull();
    System.out.println("Deposit address: " + addr);
  }

  /*
   * @Test public void withdrawTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); AccountService service =
   * exchange.getAccountService(); assertNotNull(service); // donate to Apache Foundation String txid = service.withdrawFunds("BTC", new
   * BigDecimal("0.01"), "XXX"); assertNotNull(txid); System.out.println("Withdrawal txid: " + txid); }
   */

  @Test
  void dynamicFeesTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertThat(exchange).isNotNull();
    AccountService service = exchange.getAccountService();
    assertThat(service).isNotNull();
    Map<Instrument, Fee> fees = service.getDynamicTradingFeesByInstrument();
    System.out.println(fees);
  }
}
