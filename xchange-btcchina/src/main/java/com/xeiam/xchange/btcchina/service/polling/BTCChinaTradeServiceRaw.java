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
package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaUtils;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrders;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaBuyOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaCancelOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaSellOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import com.xeiam.xchange.dto.Order.OrderType;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Implementation of the trade service for BTCChina
 *         </p>
 *         <ul>
 *         <li>Provides access to trade functions</li>
 *         </ul>
 */
public class BTCChinaTradeServiceRaw extends BTCChinaBasePollingService<BTCChina> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTCChinaTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTCChina.class, exchangeSpecification);
  }

  /**
   * @return Set of BTCChina Orders
   * @throws IOException
   */
  public BTCChinaResponse<BTCChinaOrders> getBTCChinaOpenOrders() throws IOException {

    return btcChina.getOrders(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaGetOrdersRequest());
  }

  /**
   * @return BTCChinaIntegerResponse of new limit order status.
   */
  public BTCChinaIntegerResponse placeBTCChinaLimitOrder(BigDecimal price, BigDecimal amount, OrderType orderType) throws IOException {

    BTCChinaIntegerResponse response = null;

    if (orderType == OrderType.BID) {

      response = btcChina.buyOrder2(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaBuyOrderRequest(price, amount));
    }
    else {
      response = btcChina.sellOrder2(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaSellOrderRequest(price, amount));
    }

    return response;
  }

  /**
   * @return BTCChinaBooleanResponse of limit order cancellation status.
   */
  public BTCChinaBooleanResponse cancelBTCChinaOrder(String orderId) throws IOException {

    return btcChina.cancelOrder(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaCancelOrderRequest(Long.parseLong(orderId)));
  }

  public BTCChinaTransactionsResponse getTransactions() throws IOException {

    return btcChina.getTransactions(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaTransactionsRequest());
  }

}
