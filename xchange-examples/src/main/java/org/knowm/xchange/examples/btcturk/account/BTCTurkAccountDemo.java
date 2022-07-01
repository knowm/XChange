package org.knowm.xchange.examples.btcturk.account;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.dto.account.BTCTurkAccountBalance;
import org.knowm.xchange.btcturk.service.BTCTurkAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.examples.btcturk.BTCTurkDemoUtils;
import org.knowm.xchange.service.account.AccountService;

/** @author mertguner */
public class BTCTurkAccountDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTCTurk exchange API using default settings
    Exchange exchange = BTCTurkDemoUtils.createExchange();

    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((BTCTurkAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    Map<Currency, Balance> balances = accountService.getAccountInfo().getWallet().getBalances();
    System.out.println(balances.toString());

    System.out.println(accountService.requestDepositAddress(Currency.BTC));
  }

  private static void raw(BTCTurkAccountServiceRaw accountService) throws IOException {
    BTCTurkAccountBalance responseBalances = accountService.getBTCTurkBalance();

    System.out.println(responseBalances.toString());

    System.out.println(responseBalances.getBtc_balance().toString());
  }
}
