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
package com.xeiam.xchange.mtgox.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxException;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxAccountInfoWrapper;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxBitcoinDepositAddress;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxBitcoinDepositAddressWrapper;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWithdrawalResponse;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWithdrawalResponseWrapper;
import com.xeiam.xchange.mtgox.v2.service.MtGoxBaseService;
import com.xeiam.xchange.mtgox.v2.service.MtGoxV2Digest;
import com.xeiam.xchange.utils.Assert;

public class MtGoxAccountServiceRaw extends MtGoxBaseService {

  private final MtGoxV2 mtGoxV2;
  private final MtGoxV2Digest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected MtGoxAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, exchangeSpecification.getSslUri());
    this.signatureCreator = MtGoxV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  public MtGoxAccountInfo getMtGoxAccountInfo() throws IOException {

    try {
      MtGoxAccountInfoWrapper mtGoxAccountInfoWrapper = mtGoxV2.getAccountInfo(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce());
      return mtGoxAccountInfoWrapper.getMtGoxAccountInfo();
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getAccountInfo(): " + e.getError(), e);
    }
  }

  public MtGoxWithdrawalResponse mtGoxWithdrawFunds(BigDecimal amount, String address) throws IOException {

    try {
      MtGoxWithdrawalResponseWrapper mtGoxWithdrawalResponseWrapper =
          mtGoxV2.withdrawBtc(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), address, amount.multiply(
              new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)).intValue(), 1, false, false);
      return mtGoxWithdrawalResponseWrapper.getMtGoxWithdrawalResponse();
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling withdrawFunds(): " + e.getError(), e);
    }
  }

  public MtGoxBitcoinDepositAddress mtGoxRequestDepositAddress(String description, String notificationUrl) throws IOException {

    try {
      MtGoxBitcoinDepositAddressWrapper mtGoxBitcoinDepositAddressWrapper =
          mtGoxV2.requestDepositAddress(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), description, notificationUrl);
      return mtGoxBitcoinDepositAddressWrapper.getMtGoxBitcoinDepositAddress();
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling requestBitcoinDepositAddress(): " + e.getError(), e);
    }
  }
}
