/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.examples.bitstamp.account;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.bitstamp.BitstampDemoUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitstamp exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li>
 * </ul>
 */
public class BitstampAccountDemo {

  public static void main(String[] args) {

    Exchange bitstamp = BitstampDemoUtils.createExchange();

    PollingAccountService accountService = bitstamp.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    String depositAddress = accountService.requestBitcoinDepositAddress(null, null);
    System.out.println("Deposit address: " + depositAddress);

    String withdrawResult = accountService.withdrawFunds(new BigDecimal(1).movePointLeft(4), "1AU9vVDp5njxucauraN3G21i2Eou9gpxUW");
    System.out.println("withdrawResult = " + withdrawResult);
  }
}
