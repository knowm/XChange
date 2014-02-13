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
package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.util.ArrayList;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryReturn;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author Matija Mazi
 * @author ObsessiveOrange
 */
public class BTCETradeService extends BTCETradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   *          The {@link ExchangeSpecification}
   */
  public BTCETradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public OpenOrders getOpenOrders() throws IOException {
	  BTCEOpenOrdersReturn orders = getBTCEOpenOrders();
	    if ("no orders".equals(orders.getError())) {
	      return new OpenOrders(new ArrayList<LimitOrder>());
	    }
	    checkResult(orders);
    return BTCEAdapters.adaptOrders(orders.getReturnValue());
  }

  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new UnsupportedOperationException("Market orders not supported by BTCE API.");
  }

  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
	  
	BTCEPlaceOrderReturn ret = placeBTCELimitOrder(limitOrder);
    checkResult(ret);
    
    return Long.toString(ret.getReturnValue().getOrderId());
  }

  public boolean cancelOrder(String orderId) throws IOException {

    BTCECancelOrderReturn ret = cancelBTCEOrder(orderId);
    checkResult(ret);
    
    return ret.isSuccess();
  }

  public Trades getTradeHistory(final Object... arguments) throws IOException {

    BTCETradeHistoryReturn btceTradeHistory = getBTCETradeHistory(arguments);
    checkResult(btceTradeHistory);
    
    return BTCEAdapters.adaptTradeHistory(btceTradeHistory.getReturnValue());
  }

}
