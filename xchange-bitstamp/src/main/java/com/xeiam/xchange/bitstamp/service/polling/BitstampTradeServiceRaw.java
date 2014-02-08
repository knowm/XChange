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
package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampAuthenticated;
import com.xeiam.xchange.bitstamp.BitstampUtils;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.bitstamp.service.BitstampDigest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author gnandiga
 */
public class BitstampTradeServiceRaw extends BasePollingExchangeService {

  protected final BitstampAuthenticated bitstampAuthenticated;
  protected final BitstampDigest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitstampTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchangeSpecification.getSslUri());
    this.signatureCreator = BitstampDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
  }

  public BitstampOrder[] getBitstampOpenOrders() throws IOException {

    return bitstampAuthenticated.getOpenOrders(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce());
  }

  public BitstampOrder sellBitstampOrder(BigDecimal tradableAmount, BigDecimal amount) throws IOException {

    return bitstampAuthenticated.sell(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), tradableAmount, amount);
  }

  public BitstampOrder buyBitStampOrder(BigDecimal tradableAmount, BigDecimal amount) throws IOException {

    return bitstampAuthenticated.buy(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), tradableAmount, amount);
  }

  public boolean cancelBitstampOrder(int orderId) throws IOException {

    return bitstampAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), orderId).equals(true);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions) throws IOException {

    return bitstampAuthenticated.getUserTransactions(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), numberOfTransactions);
  }
}
