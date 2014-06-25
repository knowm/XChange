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
package com.xeiam.xchange.btctrade.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretData;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeSecretDataService;

import si.mazi.rescu.ParamsDigest;

/**
 * Represents an API key status.
 * 
 * <p>
 * For one API key, we can only have one single session,
 * and all requests on one session should be synchronized,
 * because:
 * <ol>
 * <li>the {@code BTCTradeSecretData} of one API key is single in server side.</li>
 * <li>the nonce of one API key should be incrementing.</li>
 * </ol>
 * </p>
 */
public class BTCTradeSession {

  private final ExchangeSpecification exchangeSpecification;
  private final BTCTradeSecretDataService secretDataService;

  private long lastNonce = 0L;

  private BTCTradeSecretData secretData;
  private long secretExpiresTime;

  private BTCTradeDigest signatureCreator;

  /**
   * Constructor in package access level for {@link BTCTradeSessionFactory}.
   * 
   * @param exchangeSpecification the {@link ExchangeSpecification}.
   */
  BTCTradeSession(ExchangeSpecification exchangeSpecification) {
    this.exchangeSpecification = exchangeSpecification;
    secretDataService = new BTCTradeSecretDataService(exchangeSpecification);
  }

  public ExchangeSpecification getExchangeSpecification() {
    return exchangeSpecification;
  }

  /**
   * Returns the public key of the API key.
   *
   * @return the public key of the API key.
   */
  public String getKey() {
    return exchangeSpecification.getApiKey();
  }

  /**
   * Returns the next nonce of the session.
   *
   * @return the next nonce of the session.
   */
  public synchronized long nextNonce() {
    long newNonce = System.currentTimeMillis() * 1000;
    while (newNonce <= lastNonce) {
      newNonce++;
    }
    lastNonce = newNonce;
    return newNonce;
  }

  public synchronized ParamsDigest getSignatureCreator() {
    if (secretData == null
        || secretExpiresTime - System.currentTimeMillis() < 60 * 1000) {
      refresh();
    }

    return signatureCreator;
  }

  private void refresh() {
    secretData = secretDataService.getSecretData();
    secretExpiresTime = BTCTradeAdapters.adaptDatetime(
        secretData.getExpires()).getTime();

    signatureCreator = BTCTradeDigest.createInstance(secretData.getSecret());
  }

}
