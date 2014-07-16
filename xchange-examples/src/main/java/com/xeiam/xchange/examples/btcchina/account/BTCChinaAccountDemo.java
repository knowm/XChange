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
package com.xeiam.xchange.examples.btcchina.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaDeposit;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaAccountServiceRaw;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.utils.CertHelper;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Example showing the following:
 *         </p>
 *         <ul>
 *         <li>Connect to BTCChina exchange with authentication</li>
 *         <li>View account balance</li>
 *         <li>Get the bitcoin deposit address</li>
 *         </ul>
 */
public class BTCChinaAccountDemo {

  static Exchange btcchina = BTCChinaExamplesUtils.getExchange();
  static PollingAccountService accountService = btcchina.getPollingAccountService();

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    generic();
    raw();
  }

  public static void generic() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    String depositAddress = accountService.requestDepositAddress(null, null);
    System.out.println("Deposit address: " + depositAddress);

    // API key has no withdraw rights - returns 401 unauthorized
    // String withdrawResult = accountService.withdrawFunds(new BigDecimal(1).movePointLeft(5), "1CoPAWJtran45gNM21te1xgZqbDd5UqYWB");
    // System.out.println("withdrawResult = " + withdrawResult);
  }

  public static void raw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BTCChinaAccountServiceRaw btcChinaAccountService = (BTCChinaAccountServiceRaw) accountService;

    // Get the account information
    BTCChinaResponse<BTCChinaAccountInfo> accountInfo = btcChinaAccountService.getBTCChinaAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.getResult().toString());

    // Get deposits
    BTCChinaGetDepositsResponse response = btcChinaAccountService.getDeposits("BTC");
    for (BTCChinaDeposit deposit : response.getResult().getDeposits()) {
      System.out.println(deposit);
    }
    response = btcChinaAccountService.getDeposits("BTC", false);
    for (BTCChinaDeposit deposit : response.getResult().getDeposits()) {
      System.out.println(deposit);
    }

    // Not implemented for *Raw layer - retrieve from accountInfo
    /*
     * String depositAddress = btcChinaAccountService.requestBTCChinaBitcoinDepositAddress(null, null);
     * System.out.println("Deposit address: " + depositAddress);
     */
    System.out.println("AccountInfo as String: " + accountInfo.getResult().getProfile().getBtcDepositAddress());

    // API key has no withdraw rights - returns 401 unauthorized
    // BTCChinaResponse<BTCChinaID> withdrawResult = btcChinaAccountService.withdrawBTCChinaFunds(new BigDecimal(1).movePointLeft(5), "1CoPAWJtran45gNM21te1xgZqbDd5UqYWB");
    // System.out.println("withdrawResult = " + withdrawResult);
  }
}
