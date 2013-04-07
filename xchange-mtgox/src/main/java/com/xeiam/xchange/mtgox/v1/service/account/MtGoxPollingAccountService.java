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
package com.xeiam.xchange.mtgox.v1.service.account;

import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.MtGoxV1;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxBitcoinDepositAddress;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxWithdrawalResponse;
import com.xeiam.xchange.rest.HmacPostBodyDigest;
import com.xeiam.xchange.rest.ParamsDigest;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * XChange service to provide the following to {@link com.xeiam.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>MtGox specific methods to handle account-related operations</li>
 * </ul>
 */
public class MtGoxPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final MtGoxV1 mtGoxV1;
  private ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public MtGoxPollingAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.mtGoxV1 = RestProxyFactory.createProxy(MtGoxV1.class, exchangeSpecification.getSslUri());
    signatureCreator = HmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public AccountInfo getAccountInfo() {

    MtGoxAccountInfo mtGoxAccountInfo = mtGoxV1.getAccountInfo(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return MtGoxAdapters.adaptAccountInfo(mtGoxAccountInfo);
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) {

    MtGoxWithdrawalResponse result = mtGoxV1.withdrawBtc(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), address, amount.multiply(
        new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)).intValue(), 1, false, false);
    return result.getTransactionId();
  }

  @Override
  public String requestBitcoinDepositAddress(final String... arguments) {

    String description = arguments[0];
    String notificationUrl = arguments[1];
    MtGoxBitcoinDepositAddress mtGoxBitcoinDepositAddress = mtGoxV1.requestDepositAddress(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), description, notificationUrl);

    return mtGoxBitcoinDepositAddress.getAddres();
  }

  private long getNonce() {

    return System.currentTimeMillis();
  }
}
