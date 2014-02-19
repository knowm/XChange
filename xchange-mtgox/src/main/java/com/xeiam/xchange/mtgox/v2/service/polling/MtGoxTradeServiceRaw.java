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
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxException;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrder;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrderWrapper;
import com.xeiam.xchange.mtgox.v2.service.MtGoxBaseService;
import com.xeiam.xchange.mtgox.v2.service.MtGoxV2Digest;
import com.xeiam.xchange.utils.Assert;

public class MtGoxTradeServiceRaw extends MtGoxBaseService {

  private final MtGoxV2 mtGoxV2;
  private final MtGoxV2Digest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected MtGoxTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, exchangeSpecification.getSslUri());
    this.signatureCreator = MtGoxV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  public MtGoxOpenOrder[] getMtGoxOpenOrders() throws IOException {

    try {
      MtGoxOpenOrderWrapper mtGoxOpenOrderWrapper = mtGoxV2.getOpenOrders(MtGoxUtils.urlEncode(exchangeSpecification.getApiKey()), signatureCreator, MtGoxUtils.getNonce());
      return mtGoxOpenOrderWrapper.getMtGoxOpenOrders();
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getOpenOrders(): " + e.getError(), e);
    }
  }

  public MtGoxGenericResponse placeMtGoxMarketOrder(MarketOrder marketOrder) throws IOException {

    try {
      MtGoxGenericResponse mtGoxGenericResponse =
          mtGoxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), marketOrder.getTradableIdentifier(), marketOrder.getTransactionCurrency(), marketOrder
              .getType().equals(Order.OrderType.BID) ? "bid" : "ask", marketOrder.getTradableAmount().multiply(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)), null);
      return mtGoxGenericResponse;
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling placeMarketOrder(): " + e.getError(), e);
    }
  }

  public MtGoxGenericResponse placeMtGoxLimitOrder(String tradableIdentifier, String currency, String type, BigDecimal amount, String price) throws IOException {

    try {
      MtGoxGenericResponse mtGoxGenericResponse = mtGoxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), tradableIdentifier, currency, type, amount, price);
      return mtGoxGenericResponse;
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling placeLimitOrder(): " + e.getError(), e);
    }
  }

  public MtGoxGenericResponse cancelMtGoxOrder(String orderId) throws IOException {

    try {

      MtGoxGenericResponse mtGoxGenericResponse = mtGoxV2.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), orderId);
      return mtGoxGenericResponse;
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling cancelOrder(): " + e.getError(), e);
    }
  }
}
