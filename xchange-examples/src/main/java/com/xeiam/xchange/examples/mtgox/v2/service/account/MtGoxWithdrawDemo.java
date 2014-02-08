/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.examples.mtgox.v2.service.account;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.mtgox.v2.MtGoxV2ExamplesUtils;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWithdrawalResponse;
import com.xeiam.xchange.mtgox.v2.service.polling.MtGoxAccountServiceRaw;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connecting to Mt Gox BTC exchange with authentication</li>
 * <li>Retrieving account info data</li>
 * </ul>
 */
public class MtGoxWithdrawDemo {

  public static void main(String[] args) throws IOException {

    Exchange mtgox = MtGoxV2ExamplesUtils.createExchange();

    PollingAccountService accountService = mtgox.getPollingAccountService();
    generic(accountService);
    raw(((MtGoxAccountServiceRaw) accountService));
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    System.out.println(accountService.getAccountInfo());

    String withdrawResult = accountService.withdrawFunds(new BigDecimal(1).movePointLeft(2), "1Mh5brotRiiLYbbA1vqRDMNKgjSxoxLevi");
    System.out.println("withdrawResult = " + withdrawResult);
  }

  private static void raw(MtGoxAccountServiceRaw accountService) throws IOException {

    System.out.println(accountService.getMtGoxAccountInfo());

    MtGoxWithdrawalResponse withdrawResult = accountService.mtGoxWithdrawFunds(new BigDecimal(1).movePointLeft(2), "1Mh5brotRiiLYbbA1vqRDMNKgjSxoxLevi");
    System.out.println("withdrawResult = " + withdrawResult.getTransactionId());
  }
}
