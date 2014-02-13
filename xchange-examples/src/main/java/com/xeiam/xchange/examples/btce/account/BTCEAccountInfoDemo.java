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
package com.xeiam.xchange.examples.btce.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.btce.v3.dto.account.BTCEAccountInfoReturn;
import com.xeiam.xchange.btce.v3.service.polling.BTCEAccountServiceRaw;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.btce.BTCEExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * Demo requesting account info at BTC-E
 */
public class BTCEAccountInfoDemo {
  //Instantiate Exchange
  static Exchange btce = BTCEExamplesUtils.createExchange();
  
  // Interested in the private account functionality (authentication)
  static PollingAccountService accountService = btce.getPollingAccountService();
  
  public static void main(String[] args) throws IOException {
	  getGeneric();
	  getRaw();
  }
    
  public static void getGeneric() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException{
	  	  

    // Get the account information
	AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("BTCE AccountInfo as String: " + accountInfo.toString());
  }
    
  public static void getRaw() throws IOException{
	  
    // Get the account information
    BTCEAccountInfoReturn accountInfo = ((BTCEAccountServiceRaw) accountService).getBTCEAccountInfo();
    System.out.println("Raw BTCE AccountInfo as String: " + accountInfo.toString());
  }
}
