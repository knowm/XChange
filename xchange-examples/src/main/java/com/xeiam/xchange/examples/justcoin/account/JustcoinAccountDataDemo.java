/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
