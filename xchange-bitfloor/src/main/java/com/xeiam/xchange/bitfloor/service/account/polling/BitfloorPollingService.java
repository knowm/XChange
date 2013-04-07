/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitfloor.service.account.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfloor.Bitfloor;
import com.xeiam.xchange.rest.HmacPostBodyDigest;
import com.xeiam.xchange.rest.ParamsDigest;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;

/**
 * @author Matija Mazi <br/>
 * @created 2/17/13 5:07 PM
 */
public class BitfloorPollingService extends BasePollingExchangeService {

  protected final Bitfloor bitfloor;
  protected final ParamsDigest bodyDigest;

  public BitfloorPollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitfloor = RestProxyFactory.createProxy(Bitfloor.class, exchangeSpecification.getSslUri());
    this.bodyDigest = HmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected long nextNonce() {

    return System.currentTimeMillis();
  }
}
