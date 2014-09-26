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
package com.xeiam.xchange.hitbtc;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.huobi.HuobiExchange;
import com.xeiam.xchange.huobi.service.polling.HuobiTradeService;
import com.xeiam.xchange.service.polling.PollingAccountService;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests the MtGoxAdapter class
 */
public class TestHuobi {

  @Test
  public void test() throws IOException {

      ExchangeSpecification exSpec = new ExchangeSpecification(HuobiExchange.class);
      exSpec.setSslUri("https://api.huobi.com/api.php");


      Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

      PollingAccountService pollingAccountService = exchange.getPollingAccountService();
      HuobiTradeService huobiTradeService = (HuobiTradeService) exchange.getPollingTradeService();

      OpenOrders openOrders = huobiTradeService.getOpenOrders();
      System.out.println(openOrders);

      AccountInfo accountInfo = pollingAccountService.getAccountInfo();
      System.out.println(accountInfo);
  }
}
