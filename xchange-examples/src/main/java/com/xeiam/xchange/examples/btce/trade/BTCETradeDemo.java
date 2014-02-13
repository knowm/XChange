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
package com.xeiam.xchange.examples.btce.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.v3.service.polling.BTCETradeServiceRaw;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.btce.BTCEExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author Matija Mazi
 */
public class BTCETradeDemo {

    static Exchange btce = BTCEExamplesUtils.createExchange();
    static PollingTradeService tradeService = btce.getPollingTradeService();
  public static void main(String[] args) throws IOException {
	  generic();
	  raw();
  }
  
  public static void generic() throws IOException{
    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, new BigDecimal("0.1"), "BTC", "USD", "", null, MoneyUtils.parse("USD 99.025"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }
  
  public static void raw() throws IOException{
	  printRawOpenOrders(tradeService);

	    // place a limit buy order
	    LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, new BigDecimal("0.1"), "BTC", "USD", "", null, MoneyUtils.parse("USD 99.025"));
	    BTCEPlaceOrderReturn limitOrderReturnValue = ((BTCETradeServiceRaw)tradeService).placeBTCELimitOrder(limitOrder);
	    System.out.println("Limit Order return value: " + limitOrderReturnValue);

	    printRawOpenOrders(tradeService);

	    
	    // Cancel the added order
	    BTCECancelOrderReturn cancelResult = ((BTCETradeServiceRaw)tradeService).cancelBTCEOrder(String.valueOf(limitOrderReturnValue.getReturnValue().getOrderId()));
	    System.out.println("Canceling returned " + cancelResult);

	    printRawOpenOrders(tradeService);
  }
  
  private static void printRawOpenOrders(PollingTradeService tradeService) throws IOException {
	    BTCEOpenOrdersReturn openOrders = ((BTCETradeServiceRaw)tradeService).getBTCEOpenOrders();
	    System.out.println("Open Orders: " + openOrders.toString());
	  }
}
