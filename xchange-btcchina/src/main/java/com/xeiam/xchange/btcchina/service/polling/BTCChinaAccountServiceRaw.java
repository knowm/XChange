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
package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaUtils;
import com.xeiam.xchange.btcchina.dto.BTCChinaID;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetAccountInfoRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetDepositsRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalsRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaRequestWithdrawalRequest;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalsResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaRequestWithdrawalResponse;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Implementation of the account data service for BTCChina
 *         </p>
 *         <ul>
 *         <li>Provides access to account data</li>
 *         </ul>
 */
public class BTCChinaAccountServiceRaw extends BTCChinaBasePollingService<BTCChina> {
  
  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaAccountServiceRaw(ExchangeSpecification exchangeSpecification) {
  
    super(BTCChina.class, exchangeSpecification);
  }
  
  public BTCChinaResponse<BTCChinaAccountInfo> getBTCChinaAccountInfo() throws IOException {
  
    return checkResult(btcChina.getAccountInfo(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaGetAccountInfoRequest()));
  }

  public BTCChinaGetDepositsResponse getDeposits(String currency)
      throws IOException {
    return getDeposits(currency, true);
  }

  public BTCChinaGetDepositsResponse getDeposits(
      String currency, boolean pendingOnly) throws IOException {
    BTCChinaGetDepositsRequest request = new BTCChinaGetDepositsRequest(
        currency, pendingOnly);
    BTCChinaGetDepositsResponse response = btcChina.getDeposits(
        signatureCreator, BTCChinaUtils.getNonce(), request);
    return checkResult(response);
  }

  public BTCChinaGetWithdrawalResponse getWithdrawal(long id)
      throws IOException {
    return getWithdrawal(id, "BTC");
  }

  public BTCChinaGetWithdrawalResponse getWithdrawal(long id, String currency)
      throws IOException {
    BTCChinaGetWithdrawalRequest request = new BTCChinaGetWithdrawalRequest(
        id, currency);
    BTCChinaGetWithdrawalResponse response = btcChina.getWithdrawal(
        signatureCreator, BTCChinaUtils.getNonce(), request);
    return checkResult(response);
  }

  public BTCChinaGetWithdrawalsResponse getWithdrawals(String currency)
      throws IOException {
    return getWithdrawals(currency, true);
  }

  public BTCChinaGetWithdrawalsResponse getWithdrawals(
      String currency, boolean pendingOnly) throws IOException {
    BTCChinaGetWithdrawalsRequest request = new BTCChinaGetWithdrawalsRequest(
        currency, pendingOnly);
    BTCChinaGetWithdrawalsResponse response = btcChina.getWithdrawals(
        signatureCreator, BTCChinaUtils.getNonce(), request);
    return checkResult(response);
  }

  /**
   * @deprecated use {@link #withdrawBTCChinaFunds(String, BigDecimal, String)} instead.
   */
  @Deprecated
  public BTCChinaResponse<BTCChinaID> withdrawBTCChinaFunds(BigDecimal amount, String address) throws IOException {
  
    return checkResult(btcChina.requestWithdrawal(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaRequestWithdrawalRequest(amount)));
  }

  public BTCChinaResponse<BTCChinaID> withdrawBTCChinaFunds(
      String currency, BigDecimal amount, String address) throws IOException {
    BTCChinaRequestWithdrawalRequest request
      = new BTCChinaRequestWithdrawalRequest(currency, amount);
    BTCChinaRequestWithdrawalResponse response = btcChina.requestWithdrawal(
        signatureCreator, BTCChinaUtils.getNonce(), request);
    return checkResult(response);
  }

  public String requestBTCChinaBitcoinDepositAddress() throws IOException {
  
    BTCChinaResponse<BTCChinaAccountInfo> response =
        checkResult(btcChina.getAccountInfo(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaGetAccountInfoRequest()));
    
    return response.getResult().getProfile().getBtcDepositAddress();
  }
}
