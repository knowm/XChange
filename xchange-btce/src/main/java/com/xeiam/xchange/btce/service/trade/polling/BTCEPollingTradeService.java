/**
 * Copyright (C) 2012 - 2013 Matija Mazi
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btce.service.trade.polling;

import java.util.ArrayList;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.BTCEAdapters;
import com.xeiam.xchange.btce.BTCEAuthenticated;
import com.xeiam.xchange.btce.dto.marketdata.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.dto.marketdata.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.dto.marketdata.BTCEOrder;
import com.xeiam.xchange.btce.dto.marketdata.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.service.account.polling.BTCEBasePollingService;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

/** @author Matija Mazi <br/> */
public class BTCEPollingTradeService extends BTCEBasePollingService implements PollingTradeService {

  public BTCEPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() {

    BTCEOpenOrdersReturn orders = btce.OrderList(
        apiKey, signatureCreator, nextNonce(), null, null, null, null,
        BTCEAuthenticated.SortOrder.DESC, null, null, null, 1);
    if ("no orders".equals(orders.getError())) {
      return new OpenOrders(new ArrayList<LimitOrder>());
    }
    checkResult(orders);
    return BTCEAdapters.adaptOrders(orders.getReturnValue());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    throw new UnsupportedOperationException("Market orders not supported by BTCE API.");
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    String pair = String.format("%s_%s", limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency()).toLowerCase();
    BTCEOrder.Type type = limitOrder.getType() == Order.OrderType.BID ? BTCEOrder.Type.buy : BTCEOrder.Type.sell;
    BTCEPlaceOrderReturn ret = btce.Trade(apiKey, signatureCreator, nextNonce(), pair, type,
        limitOrder.getLimitPrice().getAmount(), limitOrder.getTradableAmount());
    checkResult(ret);
    return Long.toString(ret.getReturnValue().getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) {

    BTCECancelOrderReturn ret = btce.CancelOrder(apiKey, signatureCreator, nextNonce(), Long.parseLong(orderId));
    checkResult(ret);
    return ret.isSuccess();
  }
}
