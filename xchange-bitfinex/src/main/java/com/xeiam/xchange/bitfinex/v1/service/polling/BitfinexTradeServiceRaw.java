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
package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAuthenticated;
import com.xeiam.xchange.bitfinex.v1.BitfinexUtils;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class BitfinexTradeServiceRaw extends BitfinexBasePollingService<BitfinexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitfinexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BitfinexAuthenticated.class, exchangeSpecification);
  }

  public BitfinexOrderStatusResponse[] getBitfinexOpenOrders() throws IOException {

    BitfinexOrderStatusResponse[] activeOrders = bitfinex.activeOrders(apiKey, payloadCreator, signatureCreator, new BitfinexNonceOnlyRequest("/v1/orders", String.valueOf(nextNonce())));

    return activeOrders;
  }

  public BitfinexOrderStatusResponse placeBitfinexLimitOrder(LimitOrder limitOrder, boolean onMargin) throws IOException {
    String pair = BitfinexUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = onMargin ? "limit" : "exchange limit";
    BitfinexOrderStatusResponse newOrder =
        bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, new BitfinexNewOrderRequest(String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount(), limitOrder.getLimitPrice(),
            "bitfinex", type, orderType, false));

    return newOrder;
  }

  public BitfinexOrderStatusResponse cancelBitfinexOrder(String orderId) throws IOException {

    BitfinexOrderStatusResponse cancelResponse = bitfinex.cancelOrders(apiKey, payloadCreator, signatureCreator, new BitfinexCancelOrderRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));

    return cancelResponse;
  }

  public BitfinexOrderStatusResponse getBitfinexOrderStatus(String orderId) throws IOException {

    BitfinexOrderStatusResponse orderStatus = bitfinex.orderStatus(apiKey, payloadCreator, signatureCreator, new BitfinexOrderStatusRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));

    return orderStatus;

  }

  public BitfinexTradeResponse[] getBitfinexTradeHistory(String symbol, long timestamp, int limit) throws IOException {

    BitfinexTradeResponse[] trades = bitfinex.pastTrades(apiKey, payloadCreator, signatureCreator, new BitfinexPastTradesRequest(String.valueOf(nextNonce()), symbol, timestamp, limit));

    return trades;
  }
}
