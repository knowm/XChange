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
package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.BTERAdapters;
import com.xeiam.xchange.bter.BTERAuthenticated;
import com.xeiam.xchange.bter.BTERUtils;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrderSummary;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrdersReturn;
import com.xeiam.xchange.bter.dto.trade.BTEROrder;
import com.xeiam.xchange.bter.dto.trade.BTEROrderStatus;
import com.xeiam.xchange.bter.dto.trade.BTEROrderStatusReturn;
import com.xeiam.xchange.bter.dto.trade.BTERPlaceOrderReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;

public class BTERPollingTradeServiceRaw extends BTERBasePollingService<BTERAuthenticated> {


  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTERPollingTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTERAuthenticated.class, exchangeSpecification);
  }

  public String placeBTERLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = String.format("%s_%s", limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency()).toLowerCase();
    BTEROrder.Type type = limitOrder.getType() == Order.OrderType.BID ? BTEROrder.Type.buy : BTEROrder.Type.sell;
    BTERPlaceOrderReturn ret = bter.Trade(apiKey, signatureCreator, nextNonce(), pair, type, limitOrder.getLimitPrice().getAmount(), limitOrder.getTradableAmount());

    return ret.getOrderId();
  }

  public OpenOrders getBTEROpenOrders() {

    // get the summaries of the open orders
    BTEROpenOrdersReturn bterOpenOrdersReturn = bter.getOpenOrders(apiKey, signatureCreator, nextNonce());

    if (!bterOpenOrdersReturn.isResult()) {
      throw new ExchangeException("Failed to retrieve open orders because " + bterOpenOrdersReturn.getMsg());
    }

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>();

    // get the detailed information of each open order...
    for(BTEROpenOrderSummary orderSummary : bterOpenOrdersReturn.getOrders()) {

      openOrders.add(getBTEROrderStatus(orderSummary.getId()));
    }

    return new OpenOrders(openOrders);
  }

  public LimitOrder getBTEROrderStatus(String orderId) {

    BTEROrderStatusReturn orderStatusReturn = bter.getOrderStatus(apiKey, signatureCreator, nextNonce(), orderId);

    if (!orderStatusReturn.isResult()) {
      throw new ExchangeException("Failed to retrieve order status because " + orderStatusReturn.getMsg());
    }

    BTEROrderStatus orderStatus = orderStatusReturn.getBterOrderStatus();

    CurrencyPair currencyPair = BTERUtils.parseCurrencyPairString(orderStatus.getTradePair());

    return BTERAdapters.adaptOrder(orderStatus.getAmount(), orderStatus.getRate(), currencyPair.baseCurrency, currencyPair.counterCurrency,
                                   orderStatus.getType(),orderId);
  }

}
