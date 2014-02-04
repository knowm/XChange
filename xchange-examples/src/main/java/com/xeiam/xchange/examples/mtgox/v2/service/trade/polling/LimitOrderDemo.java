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
package com.xeiam.xchange.examples.mtgox.v2.service.trade.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.money.BigMoney;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.mtgox.v2.MtGoxV2ExamplesUtils;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v2.service.polling.MtGoxTradeServiceRaw;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Test placing a limit order at MtGox
 */
public class LimitOrderDemo {

    public static void main(String[] args) throws IOException {

        Exchange mtgox = MtGoxV2ExamplesUtils.createExchange();

        // Interested in the private trading functionality (authentication)
        PollingTradeService tradeService = mtgox.getPollingTradeService();
        generic(tradeService);
        raw(tradeService);
    }

    private static void raw(PollingTradeService tradeService) throws IOException {
        // place a limit order for a random amount of BTC at USD 1.25
        OrderType orderType = (OrderType.BID);
        BigDecimal tradeableAmount = new BigDecimal(Math.random());
        String tradableIdentifier = "BTC";
        String transactionCurrency = "JPY";
        BigMoney limitPrice = MoneyUtils.parse("JPY 11000.0");
        BigDecimal amount = tradeableAmount.multiply(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));


        MtGoxGenericResponse orderID = ((MtGoxTradeServiceRaw)tradeService).placeMtGoxLimitOrder(tradableIdentifier,
                transactionCurrency, "bid", amount, MtGoxUtils.getPriceString(limitPrice));
        System.out.println("Limit Order ID: " + orderID.getDataString());

        // get open orders
        OpenOrders openOrders = tradeService.getOpenOrders();
        for (LimitOrder openOrder : openOrders.getOpenOrders()) {
            System.out.println(openOrder.toString());
        }
    }

    private static void generic(PollingTradeService tradeService) throws IOException {
        // place a limit order for a random amount of BTC at USD 1.25
        OrderType orderType = (OrderType.BID);
        BigDecimal tradeableAmount = new BigDecimal(Math.random());
        String tradableIdentifier = "BTC";
        String transactionCurrency = "JPY";
        BigMoney limitPrice = MoneyUtils.parse("JPY 11000.0");

        LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount,
                                                tradableIdentifier, transactionCurrency, "", null, limitPrice);

        String orderID = tradeService.placeLimitOrder(limitOrder);
        System.out.println("Limit Order ID: " + orderID);

        // get open orders
        OpenOrders openOrders = tradeService.getOpenOrders();
        for (LimitOrder openOrder : openOrders.getOpenOrders()) {
            System.out.println(openOrder.toString());
        }
    }
}
