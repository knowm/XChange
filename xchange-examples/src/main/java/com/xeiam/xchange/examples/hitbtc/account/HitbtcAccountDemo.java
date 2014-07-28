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
package com.xeiam.xchange.examples.hitbtc.account;

import java.io.IOException;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.hitbtc.HitbtcExampleUtils;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcAccountServiceRaw;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class HitbtcAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = HitbtcExampleUtils.createExchange();
    PollingAccountService accountService = exchange.getPollingAccountService();

    generic(accountService);
    raw((HitbtcAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(HitbtcAccountServiceRaw accountService) throws IOException {

    HitbtcBalance[] accountInfo = accountService.getAccountInfoRaw();
    System.out.println(Arrays.toString(accountInfo));
  }
}
