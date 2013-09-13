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
package com.xeiam.xchange.mtgox.v2.service.polling;

import java.math.BigDecimal;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxException;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxAccountInfoWrapper;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxBitcoinDepositAddressWrapper;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWithdrawalResponseWrapper;
import com.xeiam.xchange.mtgox.v2.service.MtGoxV2Digest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingAccountService;
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
  private final MtGoxV2 mtGoxV2;
  private ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public MtGoxPollingAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, exchangeSpecification.getSslUri());
    signatureCreator = MtGoxV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public AccountInfo getAccountInfo() {

    try {
      MtGoxAccountInfoWrapper mtGoxAccountInfoWrapper = mtGoxV2.getAccountInfo(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce());
      if (mtGoxAccountInfoWrapper.getResult().equals("success")) {
        return MtGoxAdapters.adaptAccountInfo(mtGoxAccountInfoWrapper.getMtGoxAccountInfo());
      }
      else if (mtGoxAccountInfoWrapper.getResult().equals("error")) {
        throw new ExchangeException("Error calling getAccountInfo(): " + mtGoxAccountInfoWrapper.getError());
      }
      else {
        throw new ExchangeException("Error calling getAccountInfo(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getAccountInfo(): " + e.getError());
    }
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) {

    try {
      MtGoxWithdrawalResponseWrapper mtGoxWithdrawalResponseWrapper =
          mtGoxV2.withdrawBtc(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), address, amount.multiply(
              new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)).intValue(), 1, false, false);

      if (mtGoxWithdrawalResponseWrapper.getResult().equals("success")) {
        return mtGoxWithdrawalResponseWrapper.getMtGoxWithdrawalResponse().getTransactionId();
      }
      else if (mtGoxWithdrawalResponseWrapper.getResult().equals("error")) {
        throw new ExchangeException("Error calling withdrawFunds(): " + mtGoxWithdrawalResponseWrapper.getError());
      }
      else {
        throw new ExchangeException("Error calling withdrawFunds(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling withdrawFunds(): " + e.getError());
    }
  }

  @Override
  public String requestBitcoinDepositAddress(final String... arguments) {

    String description = arguments[0];
    String notificationUrl = arguments[1];
    try {
      MtGoxBitcoinDepositAddressWrapper mtGoxBitcoinDepositAddressWrapper =
          mtGoxV2.requestDepositAddress(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), description, notificationUrl);
      if (mtGoxBitcoinDepositAddressWrapper.getResult().equals("success")) {
        return mtGoxBitcoinDepositAddressWrapper.getMtGoxBitcoinDepositAddress().getAddres();
      }
      else if (mtGoxBitcoinDepositAddressWrapper.getResult().equals("error")) {
        throw new ExchangeException("Error calling requestBitcoinDepositAddress(): " + mtGoxBitcoinDepositAddressWrapper.getError());
      }
      else {
        throw new ExchangeException("Error calling requestBitcoinDepositAddress(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling requestBitcoinDepositAddress(): " + e.getError());
    }
  }
}
