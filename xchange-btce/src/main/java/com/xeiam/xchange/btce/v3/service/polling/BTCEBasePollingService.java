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
package com.xeiam.xchange.btce.v3.service.polling;

import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;
import com.xeiam.xchange.btce.v3.service.BTCEBaseService;
import com.xeiam.xchange.btce.v3.service.BTCEHmacPostBodyDigest;

/**
 * @author Matija Mazi
 */
public class BTCEBasePollingService extends BTCEBaseService {

  // private final Logger logger = LoggerFactory.getLogger(BTCEBasePollingService.class);

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  // counter for the nonce
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final BTCEAuthenticated btce;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.btce = RestProxyFactory.createProxy(BTCEAuthenticated.class, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected int nextNonce() {

    int nextNonce = lastNonce.incrementAndGet();
    // logger.debug("nextNonce in BTCEBaseService: " + nextNonce);

    return nextNonce;
  }

  protected void checkResult(BTCEReturn<?> info) {

    if (!info.isSuccess()) {
      throw new ExchangeException("BTCE returned an error: " + info.getError());
    }
    else if (info.getReturnValue() == null) {
      throw new ExchangeException("Didn't recieve any return value. Message: " + info.getError());
    }
    else if (info.getError() != null) {
      throw new ExchangeException("Got error message: " + info.getError());
    }
  }

}
