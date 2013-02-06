/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.bitcoincentral.account;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoincentral.BitcoinCentralExchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.account.polling.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitcoin Central exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li> Please provide your username and password as program arguments.
 * </ul>
 */
public class BitcoinCentralAccountDemo {

  public static void main(String[] args) {

    ExchangeSpecification exSpec = new ExchangeSpecification(BitcoinCentralExchange.class.getCanonicalName());
    exSpec.setUri("https://en.bitcoin-central.net");
    exSpec.setUserName(args[0]);
    exSpec.setPassword(args[1]);

    Exchange btcCentral = ExchangeFactory.INSTANCE.createExchange(exSpec);

    PollingAccountService accountService = btcCentral.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo: " + accountInfo);

    String depositAddress = accountService.requestBitcoinDepositAddress();
    System.out.println("Deposit address: " + depositAddress);

    // Withdraw not yet implemented.
    // String ret = accountService.withdrawFunds(new BigDecimal("0.001"), "13nKTRtz9e7SaVZzqYRTbk6PgizdhvoUDN");
    // System.out.println("ret = " + ret);
  }
}
