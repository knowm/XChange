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
import java.util.ArrayList;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BitfinexTradeService extends BitfinexBaseService implements PollingTradeService {

  private final OpenOrders noOpenOrders = new OpenOrders(new ArrayList<LimitOrder>());

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitfinexTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    BitfinexOrderStatusResponse[] activeOrders = bitfinex.activeOrders(apiKey, payloadCreator, signatureCreator, new BitfinexNonceOnlyRequest("/v1/orders", String.valueOf(nextNonce())));

    if (activeOrders.length <= 0) {
      return noOpenOrders;
    }
    else {
      return BitfinexAdapters.adaptOrders(activeOrders);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new UnsupportedOperationException("Market orders not supported yet");
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = String.format("%s%s", limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency()).toLowerCase();
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    BitfinexOrderStatusResponse newOrder =
        bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, new BitfinexNewOrderRequest(String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount(), limitOrder.getLimitPrice()
            .getAmount(), "bitfinex", type, "exchange limit", false));

    return String.valueOf(newOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    BitfinexOrderStatusResponse cancelResponse = bitfinex.cancelOrders(apiKey, payloadCreator, signatureCreator, new BitfinexCancelOrderRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));

    return cancelResponse.isCancelled();
  }

  @Override
  public Trades getTradeHistory(final Object... arguments) throws IOException {

    String symbol = "btcusd";
    long timestamp = 0;
    int limit = 50;

    if (arguments.length == 3) {
      symbol = (String) arguments[0];
      timestamp = (Long) arguments[1];
      limit = (Integer) arguments[2];
    }

    BitfinexTradeResponse[] trades = bitfinex.pastTrades(apiKey, payloadCreator, signatureCreator, new BitfinexPastTradesRequest(String.valueOf(nextNonce()), symbol, timestamp, limit));

    return BitfinexAdapters.adaptTradeHistory(trades, symbol);
  }
}
