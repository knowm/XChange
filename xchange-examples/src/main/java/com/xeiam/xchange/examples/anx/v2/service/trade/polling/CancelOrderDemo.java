/**
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
package com.xeiam.xchange.examples.anx.v2.service.trade.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

import java.io.IOException;

/**
 * Test placing a limit order at ANX
 */
public class CancelOrderDemo {

    public static void main(String[] args) throws IOException {
        Exchange anx = ANXExamplesUtils.createExchange();

        // Interested in the private trading functionality (authentication)
        PollingTradeService tradeService = anx.getPollingTradeService();

        boolean success = tradeService.cancelOrder("5aaef0f5-8c90-4a93-a097-0bad2dd475c5");
        System.out.println("success= " + success);

        // get open orders
        OpenOrders openOrders = tradeService.getOpenOrders();
        for (LimitOrder openOrder : openOrders.getOpenOrders()) {
            System.out.println(openOrder.toString());
        }
    }
}
