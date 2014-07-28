/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.hitbtc.HitbtcAuthenticated;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrdersResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcTradeResponse;

public class HitbtcTradeServiceRaw extends HitbtcBasePollingService<HitbtcAuthenticated> {

  private static final BigDecimal LOT_MULTIPLIER = new BigDecimal("100");

  public HitbtcTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(HitbtcAuthenticated.class, exchangeSpecification);
  }

  public HitbtcOrder[] getOpenOrdersRaw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcActiveOrders(signatureCreator, nextNonce(), apiKey);
    return hitbtcActiveOrders.getOrders();
  }

  public HitbtcOrder[] getRecentOrdersRaw(int max_results) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcRecentOrders(signatureCreator, nextNonce(), apiKey, max_results);
    return hitbtcActiveOrders.getOrders();
  }

  public HitbtcExecutionReport placeMarketOrderRaw(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String symbol = marketOrder.getCurrencyPair().baseSymbol + marketOrder.getCurrencyPair().counterSymbol;

    long nonce = nextNonce();
    String side = getSide(marketOrder.getType());
    String orderId = createId(marketOrder, nonce);

    HitbtcExecutionReportResponse response = hitbtc.postHitbtcNewOrder(signatureCreator, nonce, apiKey, orderId,
        symbol, side, null, marketOrder.getTradableAmount().multiply(LOT_MULTIPLIER), "market", "GTC");

    return response.getExecutionReport();
  }

  public HitbtcExecutionReport placeLimitOrderRaw(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String symbol = createSymbol(limitOrder.getCurrencyPair());
    long nonce = nextNonce();
    String side = getSide(limitOrder.getType());
    String orderId = createId(limitOrder, nonce);

    HitbtcExecutionReportResponse postHitbtcNewOrder = hitbtc.postHitbtcNewOrder(signatureCreator, nonce, apiKey, orderId,
        symbol, side, limitOrder.getLimitPrice(), limitOrder.getTradableAmount().multiply(LOT_MULTIPLIER), "limit", "GTC");

    return postHitbtcNewOrder.getExecutionReport();
  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String originalSide = getSide(readOrderType(orderId));
    String symbol = readSymbol(orderId);

    return hitbtc.postHitbtcCancelOrder(signatureCreator, nextNonce(), apiKey, orderId, orderId, symbol, originalSide); // extract symbol and side from original order id: buy/sell
  }

  public HitbtcOwnTrade[] getTradeHistoryRaw(int startIndex, int maxResults, String symbols) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
      IOException {

    HitbtcTradeResponse hitbtcTrades = hitbtc.getHitbtcTrades(signatureCreator, nextNonce(), apiKey, "ts", startIndex, maxResults, symbols, "desc", null, null);

    return hitbtcTrades.getTrades();
  }

  private static String getSide(OrderType type) {
    return type == OrderType.BID ? "buy" : "sell";
  }

  private static String createSymbol(CurrencyPair pair){
    return pair.baseSymbol + pair.counterSymbol;
  }

  private String createId(Order order, long nonce) {
    // encoding side in client order id
    return order.getType() + createSymbol(order.getCurrencyPair()) + nonce;
  }

  private OrderType readOrderType(String orderId){
    return orderId.charAt(0) == 'A' ? OrderType.ASK : OrderType.BID;
  }

  public static String readSymbol(String orderId){
    return orderId.substring(3, 9);
  }

}
