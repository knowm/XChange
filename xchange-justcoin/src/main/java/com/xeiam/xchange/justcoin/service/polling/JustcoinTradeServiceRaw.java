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

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.justcoin.JustcoinAuthenticated;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.trade.JustcoinOrder;
import com.xeiam.xchange.justcoin.dto.trade.JustcoinTrade;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author jamespedwards42
 */
public class JustcoinTradeServiceRaw extends BasePollingExchangeService {

  private JustcoinAuthenticated justcoinAuthenticated;

  public JustcoinTradeServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.justcoinAuthenticated = RestProxyFactory.createProxy(JustcoinAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public JustcoinOrder[] getOrders() throws IOException {

    return justcoinAuthenticated.getOrders(getBasicAuthentication(), exchangeSpecification.getApiKey());
  }

  public JustcoinTrade[] getOrderHistory() throws IOException {

    return justcoinAuthenticated.getOrderHistory(getBasicAuthentication(), exchangeSpecification.getApiKey());
  }

  public String placeMarketOrder(final MarketOrder marketOrder) throws IOException {

    final String market = JustcoinUtils.getApiMarket(marketOrder.getTradableIdentifier(), marketOrder.getTransactionCurrency());
    return justcoinAuthenticated
        .createMarketOrder(market, marketOrder.getType().toString().toLowerCase(), marketOrder.getTradableAmount(), getBasicAuthentication(), exchangeSpecification.getApiKey()).getId();
  }

  public String placeLimitOrder(final LimitOrder limitOrder) throws IOException {

    final String market = JustcoinUtils.getApiMarket(limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency());
    return justcoinAuthenticated.createLimitOrder(market, limitOrder.getType().toString().toLowerCase(), limitOrder.getLimitPrice().getAmount(), limitOrder.getTradableAmount(),
        getBasicAuthentication(), exchangeSpecification.getApiKey()).getId();
  }

  public boolean cancelOrder(final String orderId) throws IOException {

    justcoinAuthenticated.cancelOrder(orderId, getBasicAuthentication(), exchangeSpecification.getApiKey());
    return true;
  }

  private String getBasicAuthentication() {

    return JustcoinUtils.getBasicAuth(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
  }
}
