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
package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.justcoin.JustcoinAuthenticated;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.Utils;
import com.xeiam.xchange.justcoin.dto.trade.in.OrderReq;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinOrder;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinTrade;

/**
 * @author jamespedwards42
 */
public class JustcoinTradeServiceRaw extends JustcoinBasePollingService<JustcoinAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public JustcoinTradeServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(JustcoinAuthenticated.class, exchangeSpecification);
  }

  public JustcoinOrder[] getOrders() throws IOException {

    return justcoin.getOrders(getBasicAuthentication(), exchangeSpecification.getApiKey());
  }

  public JustcoinTrade[] getOrderHistory() throws IOException {

    return justcoin.getOrderHistory(getBasicAuthentication(), exchangeSpecification.getApiKey());
  }

  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return justcoin.createMarketOrder(JustcoinUtils.getApiMarket(marketOrder.getCurrencyPair()), marketOrder.getType().toString().toLowerCase(), marketOrder.getTradableAmount(),
        getBasicAuthentication(), exchangeSpecification.getApiKey()).getId();
  }

  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    OrderReq req =
        new OrderReq(JustcoinUtils.getApiMarket(limitOrder.getCurrencyPair()), Utils.format(limitOrder.getLimitPrice()), Utils.format(limitOrder.getTradableAmount()), limitOrder.getType().toString()
            .toLowerCase());

    return justcoin.createLimitOrder(req, getBasicAuthentication(), exchangeSpecification.getApiKey()).getId();
  }

  public boolean cancelOrder(String orderId) throws IOException {

    justcoin.cancelOrder(orderId, getBasicAuthentication(), exchangeSpecification.getApiKey());
    return true;
  }
}
