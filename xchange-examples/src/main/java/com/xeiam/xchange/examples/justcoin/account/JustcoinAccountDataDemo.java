package com.xeiam.xchange.examples.justcoin.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.justcoin.JustcoinDemoUtils;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;
import com.xeiam.xchange.justcoin.service.polling.JustcoinAccountServiceRaw;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class JustcoinAccountDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange justcoinExchange = JustcoinDemoUtils.createExchange();

    generic(justcoinExchange);
    raw(justcoinExchange);
  }

  private static void generic(Exchange justcoinExchange) throws IOException {

    PollingAccountService genericAccountService = justcoinExchange.getPollingAccountService();

    AccountInfo accountInfo = genericAccountService.getAccountInfo();
    System.out.println(accountInfo);

    String depositAddr = genericAccountService.requestDepositAddress(Currencies.LTC);
    System.out.println("LTC deposit address: " + depositAddr);

    String txid = genericAccountService.withdrawFunds("BTC", new BigDecimal("0.001"), "1Fpx2Q6J8TX3PZffgEBTpWSHG37FQBgqKB");
    System.out.println("See the withdrawal transaction: http://blockchain.info/tx-index/" + txid);
  }

  private static void raw(Exchange justcoinExchange) throws IOException {

    JustcoinAccountServiceRaw justcoinSpecificAccountService = (JustcoinAccountServiceRaw) justcoinExchange.getPollingAccountService();

    JustcoinBalance[] balances = justcoinSpecificAccountService.getBalances();
    System.out.println(Arrays.toString(balances));

    String ltcDepositAddr = justcoinSpecificAccountService.requestDepositAddress(Currencies.LTC);
    System.out.println("LTC deposit address: " + ltcDepositAddr);

    String txid = justcoinSpecificAccountService.withdrawFunds(Currencies.LTC, new BigDecimal("0.001"), "LSi6YXDVZDed9zcdB4ubT8vffLk7RGFoF2");
    System.out.println("See the withdrawal transaction: http://ltc.blockr.io/" + txid);
  }
}
