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
import java.util.List;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.huobi.HuobiAuth;
import com.xeiam.xchange.huobi.dto.trade.polling.*;
import com.xeiam.xchange.huobi.service.HuobiBaseService;
import com.xeiam.xchange.huobi.service.HuobiDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;

public class HuobiTradeServiceRaw extends HuobiBaseService {

  private final HuobiAuth huobiAuth;
  private final HuobiDigest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected HuobiTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.huobiAuth = RestProxyFactory.createProxy(HuobiAuth.class, exchangeSpecification.getSslUri());
    this.signatureCreator = HuobiDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  public HuobiOrder[] getHuobiOpenOrders() throws IOException {

      HuobiOrder[] orders = huobiAuth.get_orders(exchangeSpecification.getApiKey(), new Date().getTime() / 1000, signatureCreator);
//      checkResponce(orders);
      return orders;
  }

    public HuobiOrder getHuobiOrderInfo(LimitOrder limitOrder) throws IOException {

        HuobiOrder order = huobiAuth.order_info(exchangeSpecification.getApiKey(), new Date().getTime() / 1000,
                limitOrder.getId(), signatureCreator);
        checkResponce(order);
        return order;
    }

  public HuobiExecutionReport placeHuobiLimitOrder(LimitOrder limitOrder) throws IOException {

      String method = limitOrder.getType() == Order.OrderType.BID ? "buy" : "sell";

      HuobiExecutionReport huobiExecutionReport = huobiAuth.createNewOrder(exchangeSpecification.getApiKey(), new Date().getTime()/1000,
              limitOrder.getLimitPrice(), limitOrder.getTradableAmount(),
              method, signatureCreator);

      checkResponce(huobiExecutionReport);
      return huobiExecutionReport;
  }

    public HuobiExecutionReport modifyHuobiLimitOrder(LimitOrder limitOrder) throws IOException {

        HuobiExecutionReport huobiExecutionReport = huobiAuth.modify_order(exchangeSpecification.getApiKey(), new Date().getTime()/1000,
                limitOrder.getId(), limitOrder.getLimitPrice(), limitOrder.getTradableAmount(),
                signatureCreator);

        checkResponce(huobiExecutionReport);
        return huobiExecutionReport;
    }

  public HuobiExecutionReport cancelHuobiOrder(String orderId) throws IOException {

      HuobiExecutionReport huobiExecutionReport = huobiAuth.cancel_order(exchangeSpecification.getApiKey(), new Date().getTime() / 1000,
              orderId, signatureCreator);

      checkResponce(huobiExecutionReport);
      return huobiExecutionReport;
  }
}
