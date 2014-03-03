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
package com.xeiam.xchange.bter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.bter.dto.account.BTERAccountInfoReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from Bter DTOs to XChange DTOs
 */
public final class BTERAdapters {

  // BTER specific bid/ask syntax
  public static final String BTER_BID = "buy";
  public static final String BTER_ASK = "sell";

  /**
   * private Constructor
   */
  private BTERAdapters() {

  }

  /**
   * Adapts a BTEROrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase(BTER_BID) ? OrderType.BID : OrderType.ASK;
    BigDecimal limitPrice;

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);
  }

  /**
   * Adapts a List of Bter Orders to a List of LimitOrders
   * 
   * @param orders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> orders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    // Bid orderbook is reversed order. Insert at index 0 instead of
    for (BigDecimal[] bterOrder : orders) {
      // appending
      if (orderType.equalsIgnoreCase(BTER_BID)) {
        limitOrders.add(0, adaptOrder(bterOrder[1], bterOrder[0], currencyPair, orderType, id));
      }
      else {
        limitOrders.add(adaptOrder(bterOrder[1], bterOrder[0], currencyPair, orderType, id));
      }
    }

    return limitOrders;
  }

  public static AccountInfo adaptAccountInfo(BTERAccountInfoReturn btceAccountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    Map<String, BigDecimal> funds = btceAccountInfo.getAvailableFunds();
    for (String lcCurrency : funds.keySet()) {
      String currency = lcCurrency.toUpperCase();
      wallets.add(new Wallet(currency, funds.get(lcCurrency)));
    }

    return new AccountInfo("", wallets);
  }

}
