package com.xeiam.xchange.examples.bittrex.v1.account;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalance;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexAccountServiceRaw;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.examples.bittrex.v1.BittrexExamplesUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class BittrexAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BittrexExamplesUtils.getExchange();

    PollingAccountService accountService = exchange.getPollingAccountService();

    generic(accountService);
    raw((BittrexAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    System.out.println("----------GENERIC---------");

    Map<Currency,Balance> balances = accountService.getAccountInfo().getWallet().getBalances();
    System.out.println(balances);

    System.out.println(accountService.requestDepositAddress(Currency.BTC));

  }

  private static void raw(BittrexAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW-----------");

    List<BittrexBalance> wallets = accountService.getBittrexAccountInfo();
    System.out.println(wallets);

    System.out.println(accountService.getBittrexDepositAddress("BTC"));

  }
}
