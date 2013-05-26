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
package com.xeiam.xchange.examples.mtgox.v1.service.trade.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.mtgox.v1.MtGoxV1ExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connecting to Mt Gox BTC exchange with authentication</li>
 * <li>Retrieving account info data</li>
 * </ul>
 * <p>
 * 
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
public class MtGoxWithdrawDemo {

  public static void main(String[] args) {

    Exchange mtgox = MtGoxV1ExamplesUtils.createExchange();

    PollingAccountService accountService = mtgox.getPollingAccountService();
    System.out.println(accountService.getAccountInfo());

    String withdrawResult = accountService.withdrawFunds(new BigDecimal(1).movePointLeft(2), "1Mh5brotRiiLYbbA1vqRDMNKgjSxoxLevi");
    System.out.println("withdrawResult = " + withdrawResult);
  }
}
