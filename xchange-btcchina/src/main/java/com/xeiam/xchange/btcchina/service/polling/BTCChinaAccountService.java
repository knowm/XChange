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
package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.BTCChinaUtils;
import com.xeiam.xchange.btcchina.dto.BTCChinaID;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetAccountInfoRequest;
import com.xeiam.xchange.btcchina.service.BTCChinaDigest;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author ObsessiveOrange
 * <p>
 * Implementation of the account data service for BTCChina
 * </p>
 * <ul>
 * <li>Provides access to account data</li>
 * </ul>
 */
public class BTCChinaAccountService extends BTCChinaAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    RestProxyFactory.createProxy(BTCChina.class, exchangeSpecification.getSslUri());
    BTCChinaDigest.createInstance(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey());
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    BTCChinaResponse<BTCChinaAccountInfo> response = getBTCChinaAccountInfo();
    return BTCChinaAdapters.adaptAccountInfo(response);
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) throws IOException {

    BTCChinaResponse<BTCChinaID> response = withdrawBTCChinaFunds(amount, address);
    return response.getResult().getId();
  }

  @Override
  public String requestBitcoinDepositAddress(String... arguments) throws IOException {

    BTCChinaResponse<BTCChinaAccountInfo> response = btcchina.getAccountInfo(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaGetAccountInfoRequest());

    return response.getResult().getProfile().getBtcDepositAddress();
  }
}
