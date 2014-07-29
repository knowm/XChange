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
package com.xeiam.xchange.examples.bittrex.v1.account;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalance;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexAccountServiceRaw;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.examples.bittrex.v1.BittrexExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class BittrexAccountDemo {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Exchange exchange = BittrexExamplesUtils.getExchange();

    PollingAccountService accountService = exchange.getPollingAccountService();

    generic(accountService);
    raw((BittrexAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    System.out.println("----------GENERIC---------");

    List<Wallet> wallets = accountService.getAccountInfo().getWallets();
    System.out.println(wallets);

    System.out.println(accountService.requestDepositAddress("BTC"));

  }

  private static void raw(BittrexAccountServiceRaw accountService) throws IOException {

    System.out.println("------------RAW-----------");
    
    List<BittrexBalance> wallets = accountService.getBittrexAccountInfo();
    System.out.println(wallets);

    System.out.println(accountService.getBittrexDepositAddress("BTC"));

  }
}
