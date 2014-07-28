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
package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.BitcurexAuthenticated;
import com.xeiam.xchange.bitcurex.BitcurexUtils;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import com.xeiam.xchange.bitcurex.service.BitcurexDigest;

public class BitcurexAccountServiceRaw extends BitcurexBasePollingService {

  private final BitcurexDigest signatureCreator;
  private final BitcurexAuthenticated bitcurexAuthenticated;

  public BitcurexAccountServiceRaw(ExchangeSpecification exchangeSpecification) throws IOException {

    super(exchangeSpecification);

    this.bitcurexAuthenticated = RestProxyFactory.createProxy(BitcurexAuthenticated.class, exchangeSpecification.getSslUri());
    this.signatureCreator = BitcurexDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getApiKey());
  }

  public BitcurexFunds getFunds() throws IOException, ExchangeException {

    BitcurexFunds bitcurexFunds = bitcurexAuthenticated.getFunds(exchangeSpecification.getApiKey(), signatureCreator, BitcurexUtils.getNonce());
    if (bitcurexFunds.getError() != null) {
      throw new ExchangeException("Error getting balance. " + bitcurexFunds.getError());
    }
    return bitcurexFunds;
  }

}
