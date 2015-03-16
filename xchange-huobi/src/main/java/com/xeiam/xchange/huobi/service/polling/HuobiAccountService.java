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
package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.huobi.HuobiAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class HuobiAccountService extends HuobiAccountServiceRaw implements PollingAccountService {

  public HuobiAccountService(Exchange exchange) {

      super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return HuobiAdapters.adaptAccountInfo(getHuobiAccountInfo());
  }

    @Override
    public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {
        return null;
    }

    @Override
    public String requestDepositAddress(String currency, String... args) throws IOException {
        return null;
    }

}
