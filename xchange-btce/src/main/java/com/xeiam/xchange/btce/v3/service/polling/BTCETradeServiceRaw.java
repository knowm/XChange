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
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.btce.v3.BTCEUtils;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.utils.Assert;

/**
 * @author Matija Mazi
 * @author ObsessiveOrange
 */
public class BTCETradeServiceRaw extends BTCEBaseService{

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

    return btce.ActiveOrders(apiKey, signatureCreator, nextNonce(), null);
  }

  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new UnsupportedOperationException("Market orders not supported by BTCE API.");
  }

  public BTCEPlaceOrderReturn placeBTCELimitOrder(LimitOrder limitOrder) throws IOException {

    Assert.isTrue(BTCEUtils.isValidCurrencyPair(new CurrencyPair(limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency())), "currencyPair is not valid:"
        + limitOrder.getTradableIdentifier() + " " + limitOrder.getTransactionCurrency());

    String pair = String.format("%s_%s", limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency()).toLowerCase();
    BTCEOrder.Type type = limitOrder.getType() == Order.OrderType.BID ? BTCEOrder.Type.buy : BTCEOrder.Type.sell;
   
    return btce.Trade(apiKey, signatureCreator, nextNonce(), pair, type, limitOrder.getLimitPrice().getAmount(), limitOrder.getTradableAmount());
  }

  public BTCECancelOrderReturn cancelBTCEOrder(String orderId) throws IOException {

    return btce.CancelOrder(apiKey, signatureCreator, nextNonce(), Long.parseLong(orderId));
  }

  public BTCETradeHistoryReturn getBTCETradeHistory(final Object... arguments) throws IOException {

    Long numberOfTransactions = Long.MAX_VALUE;
    String tradableIdentifier = "";
    String transactionCurrency = "";
    Long id = null;
    try {
      numberOfTransactions = (Long) arguments[0];
      tradableIdentifier = (String) arguments[1];
      transactionCurrency = (String) arguments[2];
      id = (Long) arguments[3];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }
    String pair = null;
    if (!tradableIdentifier.equals("") && !transactionCurrency.equals("")) {
      pair = String.format("%s_%s", tradableIdentifier, transactionCurrency).toLowerCase();
    }
    
    return btce.TradeHistory(apiKey, signatureCreator, nextNonce(), null, numberOfTransactions, id, id, BTCEAuthenticated.SortOrder.DESC, null, null, pair);
  }

}
