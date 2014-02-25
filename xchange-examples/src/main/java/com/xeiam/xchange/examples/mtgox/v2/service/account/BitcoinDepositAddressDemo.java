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

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.mtgox.v2.MtGoxV2ExamplesUtils;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxBitcoinDepositAddress;
import com.xeiam.xchange.mtgox.v2.service.polling.MtGoxAccountServiceRaw;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * Demo requesting account info at MtGox
 */
public class BitcoinDepositAddressDemo {

  public static void main(String[] args) throws IOException {

    Exchange mtgox = MtGoxV2ExamplesUtils.createExchange();

    // Interested in the private account functionality (authentication)
    PollingAccountService accountService = mtgox.getPollingAccountService();
    generic(accountService);
    raw(((MtGoxAccountServiceRaw) accountService));

  }

  private static void generic(PollingAccountService accountService) throws IOException {

    // Request a Bitcoin deposit address
    String address = accountService.requestDepositAddress("Demonstation address", null);
    System.out.println("Address to deposit Bitcoins to: " + address);
  }

  private static void raw(MtGoxAccountServiceRaw accountService) throws IOException {

    // Request a Bitcoin deposit address
    MtGoxBitcoinDepositAddress address = accountService.mtGoxRequestDepositAddress("Demonstation address", null);
    System.out.println("Address to deposit Bitcoins to: " + address.getAddres());
  }
}