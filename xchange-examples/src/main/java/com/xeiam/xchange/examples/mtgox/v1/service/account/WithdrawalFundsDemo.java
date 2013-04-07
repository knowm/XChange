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
package com.xeiam.xchange.examples.mtgox.v1.service.account;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.mtgox.v1.MtGoxExchange;
import com.xeiam.xchange.service.account.polling.PollingAccountService;

/**
 * Demo requesting account info at MtGox
 */
public class WithdrawalFundsDemo {

  public static void main(String[] args) {

    // Use the factory to get the version 1 MtGox exchange API using default settings
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(MtGoxExchange.class.getName());
    exchangeSpecification.setApiKey("150c6db9-e5ab-47ac-83d6-4440d1b9ce49");
    exchangeSpecification.setSecretKey("olHM/yl3CAuKMXFS2+xlP/MC0Hs1M9snHpaHwg0UZW52Ni0Tf4FhGFELO9cHcDNGKvFrj8CgyQUA4VsMTZ6dXg==");
    exchangeSpecification.setSslUri("https://mtgox.com");
    Exchange mtgox = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Interested in the private account functionality (authentication)
    PollingAccountService accountService = mtgox.getPollingAccountService();

    System.out.println(accountService.getAccountInfo());

    // Withdrawal transactions may be slow to appear for amounts less than 0.01, even though the API returns success.
    // Change the amount to 0.01 or more to see the transaction appear in the block chain quickly.
    // May be a general Bitcoin thing.
    String transactionID = accountService.withdrawFunds(new BigDecimal("0.001"), "17dQktcAmU4urXz7tGk2sbuiCqykm3WLs6");
    System.out.println("transactionID= " + transactionID);
  }

}
