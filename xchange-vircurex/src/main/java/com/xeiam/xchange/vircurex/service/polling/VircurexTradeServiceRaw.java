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
package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.vircurex.VircurexAuthenticated;
import com.xeiam.xchange.vircurex.VircurexUtils;
import com.xeiam.xchange.vircurex.dto.trade.VircurexPlaceOrderReturn;
import com.xeiam.xchange.vircurex.service.VircurexBaseService;
import com.xeiam.xchange.vircurex.service.VircurexSha2Digest;

public class VircurexTradeServiceRaw extends VircurexBaseService {

  private VircurexAuthenticated vircurex;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VircurexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    vircurex = RestProxyFactory.createProxy(VircurexAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public String placeVircurexLimitOrder(LimitOrder limitOrder) throws IOException {

    String type = limitOrder.getType() == Order.OrderType.BID ? "buy" : "sell";
    String timestamp = VircurexUtils.getUtcTimestamp();
    String nonce = (System.currentTimeMillis() / 250L) + "";
    VircurexSha2Digest digest =
        new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "create_order", type.toString(), limitOrder.getTradableAmount().floatValue()
            + "", limitOrder.getTradableIdentifier().toLowerCase(), limitOrder.getLimitPrice().getAmount().floatValue() + "", limitOrder.getTransactionCurrency().toLowerCase());

    VircurexPlaceOrderReturn ret =
        vircurex.trade(exchangeSpecification.getApiKey(), nonce, digest.toString(), timestamp, type.toString(), limitOrder.getTradableAmount().floatValue() + "", limitOrder.getTradableIdentifier()
            .toLowerCase(), limitOrder.getLimitPrice().getAmount().floatValue() + "", limitOrder.getTransactionCurrency().toLowerCase());

    timestamp = VircurexUtils.getUtcTimestamp();
    nonce = (System.currentTimeMillis() / 200L) + "";

    digest = new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "release_order", ret.getOrderId());

    ret = vircurex.release(exchangeSpecification.getApiKey(), nonce, digest.toString(), timestamp, ret.getOrderId());
    return ret.getOrderId();
  }

}
