/*
 * The MIT License
 * 
 * Copyright 2015-2016 Coinmate.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.knowm.xchange.coinmate.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.CoinmateAdapters;
import org.knowm.xchange.coinmate.CoinmateException;
import org.knowm.xchange.coinmate.CoinmateUtils;
import org.knowm.xchange.coinmate.dto.trade.CoinmateCancelOrderResponse;
import org.knowm.xchange.coinmate.dto.trade.CoinmateOpenOrders;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPagingSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author Martin Stachon
 */
public class CoinmateTradeService extends CoinmateTradeServiceRaw implements TradeService {

  public CoinmateTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws IOException {
    CurrencyPair currencyPair = null;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    }

    String currencyPairString = CoinmateUtils.getPair(currencyPair);
    CoinmateOpenOrders coinmateOpenOrders = getCoinmateOpenOrders(currencyPairString);
    List<LimitOrder> orders = CoinmateAdapters.adaptOpenOrders(coinmateOpenOrders);
    return new OpenOrders(orders);
  }

  @Override
  public String placeMarketOrder(
      MarketOrder marketOrder) throws IOException {
    CoinmateTradeResponse response;

    if (marketOrder.getType().equals(Order.OrderType.ASK)) {
      response = sellCoinmateInstant(marketOrder.getOriginalAmount(), CoinmateUtils.getPair(marketOrder.getCurrencyPair()));
    } else if (marketOrder.getType().equals(Order.OrderType.BID)) {
      response = buyCoinmateInstant(marketOrder.getOriginalAmount(), CoinmateUtils.getPair(marketOrder.getCurrencyPair()));
    } else {
      throw new CoinmateException("Unknown order type");
    }

    return Long.toString(response.getData());
  }

  @Override
  public String placeLimitOrder(
      LimitOrder limitOrder) throws IOException {
    CoinmateTradeResponse response;

    if (limitOrder.getType().equals(Order.OrderType.ASK)) {
      response = sellCoinmateLimit(limitOrder.getOriginalAmount(), limitOrder.getLimitPrice(), CoinmateUtils.getPair(limitOrder.getCurrencyPair()));
    } else if (limitOrder.getType().equals(Order.OrderType.BID)) {
      response = buyCoinmateLimit(limitOrder.getOriginalAmount(), limitOrder.getLimitPrice(), CoinmateUtils.getPair(limitOrder.getCurrencyPair()));
    } else {
      throw new CoinmateException("Unknown order type");
    }

    return Long.toString(response.getData());
  }

  @Override
  public boolean cancelOrder(
      String orderId) throws IOException {
    CoinmateCancelOrderResponse response = cancelCoinmateOrder(orderId);

    return response.getData();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    // here we use the Transaction History call, which includes also withdrawals etc.
    // there is also Order History call, but this returns orders rather than trades
    DefaultTradeHistoryParamPagingSorted myParams = (DefaultTradeHistoryParamPagingSorted) params;
    return CoinmateAdapters.adaptTransactionHistory(
        getCoinmateTradeHistory(myParams.getPageNumber(), myParams.getPageLength(), CoinmateAdapters.adaptSortOrder(myParams.getOrder())));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    DefaultTradeHistoryParamPagingSorted params = new DefaultTradeHistoryParamPagingSorted(100);
    params.setPageNumber(0);
    params.setOrder(TradeHistoryParamsSorted.Order.asc);
    return params;
  }

  @Override
  public CoinmateOpenOrdersParams createOpenOrdersParams() {
    return new CoinmateOpenOrdersParams();
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
