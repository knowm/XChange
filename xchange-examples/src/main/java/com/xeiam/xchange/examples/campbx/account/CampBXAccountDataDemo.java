/*
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
package com.xeiam.xchange.examples.campbx.account;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.campbx.CampBXExchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * Demonstrate requesting Market Data from CampBX
 */
public class CampBXAccountDataDemo {

  public static void main(String[] args) {

    // Use the factory to get Campbx exchange API using default settings
    Exchange campbx = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());

    campbx.getExchangeSpecification().setUserName("XChange");
    campbx.getExchangeSpecification().setPassword("The Java API");

    PollingAccountService accountService = campbx.getPollingAccountService();

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("accountInfo = " + accountInfo);

    String depositAddr = accountService.requestBitcoinDepositAddress();
    System.out.println("depositAddr = " + depositAddr);

    String txid = accountService.withdrawFunds(new BigDecimal("0.1"), "1FgpMU9CgQffjLK5YoR2yK5XGj5cq4iCBf");
    System.out.println("See the withdrawal transaction: http://blockchain.info/tx-index/" + txid);
  }

}
