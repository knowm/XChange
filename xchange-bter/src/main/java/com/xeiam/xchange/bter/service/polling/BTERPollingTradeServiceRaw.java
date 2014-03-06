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
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.BTERAuthenticated;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;
import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrders;
import com.xeiam.xchange.bter.dto.trade.BTEROrderStatus;
import com.xeiam.xchange.bter.dto.trade.BTERPlaceOrderReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class BTERPollingTradeServiceRaw extends BTERBasePollingService<BTERAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTERPollingTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTERAuthenticated.class, exchangeSpecification);
  }

  public boolean placeBTERLimitOrder(LimitOrder limitOrder) throws IOException {

    BTEROrderType type = (limitOrder.getType() == Order.OrderType.BID) ? BTEROrderType.BUY : BTEROrderType.SELL;
    
    return placeBTERLimitOrder(limitOrder.getCurrencyPair(), type, limitOrder.getLimitPrice(), limitOrder.getTradableAmount());
  }

  public boolean placeBTERLimitOrder(CurrencyPair currencyPair, BTEROrderType orderType, BigDecimal rate, BigDecimal amount) throws IOException {

    String pair = String.format("%s_%s", currencyPair.baseCurrency, currencyPair.counterCurrency).toLowerCase();
    BTERPlaceOrderReturn orderId = bter.placeOrder(pair, orderType, rate, amount, apiKey, signatureCreator, nextNonce());

    return handleResponse(orderId).isResult();
  }
  
  public boolean cancelOrder(String orderId) throws IOException {
    
    BTERBaseResponse cancelOrderResult = bter.cancelOrder(orderId, apiKey, signatureCreator, nextNonce());
    
    return handleResponse(cancelOrderResult).isResult();
  }
  
  public BTEROpenOrders getBTEROpenOrders() throws IOException {

    BTEROpenOrders bterOpenOrdersReturn = bter.getOpenOrders(apiKey, signatureCreator, nextNonce());

    return handleResponse(bterOpenOrdersReturn);
  }

  public BTEROrderStatus getBTEROrderStatus(String orderId) throws IOException {

    BTEROrderStatus orderStatus = bter.getOrderStatus(orderId, apiKey, signatureCreator, nextNonce());

    return handleResponse(orderStatus);
  }

}
