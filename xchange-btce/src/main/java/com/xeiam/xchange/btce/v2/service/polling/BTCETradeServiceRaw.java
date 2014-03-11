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
package com.xeiam.xchange.btce.v2.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btce.v2.BTCEAuthenticated;
import com.xeiam.xchange.btce.v2.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCETradeHistoryReturn;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

/** @author Matija Mazi */
@Deprecated
public class BTCETradeServiceRaw extends BTCEBasePollingService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   *          The {@link ExchangeSpecification}
   */
  public BTCETradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public BTCEOpenOrdersReturn getBTCEOpenOrders() throws IOException {

    BTCEOpenOrdersReturn orders = btce.ActiveOrders(apiKey, signatureCreator, nextNonce(), null);
    checkResult(orders);
    return orders;
  }

  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  public BTCEPlaceOrderReturn placeBTCELimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = String.format("%s_%s", limitOrder.getCurrencyPair().baseSymbol, limitOrder.getCurrencyPair().counterSymbol).toLowerCase();
    BTCEOrder.Type type = limitOrder.getType() == Order.OrderType.BID ? BTCEOrder.Type.buy : BTCEOrder.Type.sell;
    BTCEPlaceOrderReturn ret = btce.Trade(apiKey, signatureCreator, nextNonce(), pair, type, limitOrder.getLimitPrice(), limitOrder.getTradableAmount());
    checkResult(ret);
    return ret;
  }

  public BTCECancelOrderReturn cancelBTCEOrder(String orderId) throws IOException {

    BTCECancelOrderReturn ret = btce.CancelOrder(apiKey, signatureCreator, nextNonce(), Long.parseLong(orderId));
    checkResult(ret);
    return ret;
  }

  public BTCETradeHistoryReturn getBTCETradeHistory(long numberOfTransactions, String tradableIdentifier, String transactionCurrency) throws IOException {

    String pair = String.format("%s_%s", tradableIdentifier, transactionCurrency).toLowerCase();
    BTCETradeHistoryReturn btceTradeHistory = btce.TradeHistory(apiKey, signatureCreator, nextNonce(), null, numberOfTransactions, null, null, BTCEAuthenticated.SortOrder.DESC, null, null, pair);
    checkResult(btceTradeHistory);
    return btceTradeHistory;
  }

}
