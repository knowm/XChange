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
package com.xeiam.xchange.btctrade.service.polling;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretData;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretResponse;
import com.xeiam.xchange.btctrade.service.BTCTradeDigest;

public class BTCTradeBaseTradePollingService extends BTCTradeBasePollingService {

  private long lastNonce = 0L;

  protected final String publicKey;
  private final String privateKey;

  private String secret;
  private long secretExpiresTime;
  private ParamsDigest signatureCreator;

  /**
   * @param exchangeSpecification
   */
  protected BTCTradeBaseTradePollingService(
      ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    publicKey = exchangeSpecification.getApiKey();
    privateKey = exchangeSpecification.getSecretKey();
  }

  protected synchronized long nextNonce() {
    long newNonce = System.currentTimeMillis() * 1000;
    while (newNonce <= lastNonce) {
      newNonce++;
    }
    lastNonce = newNonce;
    return newNonce;
  }

  protected synchronized ParamsDigest getSignatureCreator() {
    if (secret == null
        || secretExpiresTime - System.currentTimeMillis() < 60 * 1000){ 
      BTCTradeSecretData secretData = getSecretData();
      secret = secretData.getSecret();
      secretExpiresTime = BTCTradeAdapters.adaptDatetime(secretData.getExpires())
          .getTime();
      signatureCreator = BTCTradeDigest.createInstance(secret);
    }

    return signatureCreator;
  }

  private BTCTradeSecretData getSecretData() {
    BTCTradeSecretResponse response = btcTrade.getSecret(privateKey, publicKey);
    if (response.getResult()) {
      return response.getData();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

}
