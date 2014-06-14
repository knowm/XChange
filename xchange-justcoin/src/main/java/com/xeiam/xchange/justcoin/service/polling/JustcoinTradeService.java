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
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author jamespedwards42
 */
public class JustcoinTradeService extends JustcoinTradeServiceRaw implements PollingTradeService {

  public JustcoinTradeService(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return JustcoinAdapters.adaptOpenOrders(super.getOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return super.placeMarketOrder(marketOrder);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return super.placeLimitOrder(limitOrder);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return super.cancelOrder(orderId);
  }

  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    return JustcoinAdapters.adaptTrades(super.getOrderHistory());
  }
}
