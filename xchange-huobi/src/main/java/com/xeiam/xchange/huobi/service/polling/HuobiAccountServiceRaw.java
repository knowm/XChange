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
package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.huobi.Huobi;
import com.xeiam.xchange.huobi.HuobiAuth;
import com.xeiam.xchange.huobi.dto.account.polling.HuobiAccountInfo;
import com.xeiam.xchange.huobi.service.HuobiBaseService;
import com.xeiam.xchange.huobi.service.HuobiDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;

public class HuobiAccountServiceRaw extends HuobiBaseService {

  private final HuobiAuth huobiAuth;
  private final HuobiDigest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected HuobiAccountServiceRaw(Exchange exchange) {

      super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");
    this.huobiAuth = RestProxyFactory.createProxy(HuobiAuth.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = HuobiDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public HuobiAccountInfo getHuobiAccountInfo() throws IOException {

      HuobiAccountInfo accountInfo = huobiAuth.get_account_info(exchange.getExchangeSpecification().getApiKey(), new Date().getTime()/1000, signatureCreator);
      checkResponce(accountInfo);
      return accountInfo;
  }

}
