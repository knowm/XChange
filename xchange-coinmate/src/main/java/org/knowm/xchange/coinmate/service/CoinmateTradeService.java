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
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
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
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CurrencyPair currencyPair = null;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    }

    if (currencyPair == null) {
      throw new ExchangeException("CurrencyPair parameter must not be null.");
    }

    String currencyPairString = CoinmateUtils.getPair(currencyPair);
    CoinmateOpenOrders coinmateOpenOrders = getCoinmateOpenOrders(currencyPairString);
    List<LimitOrder> orders = CoinmateAdapters.adaptOpenOrders(coinmateOpenOrders, currencyPair);
    return new OpenOrders(orders);
  }

  @Override
  public String placeMarketOrder(
      MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CoinmateTradeResponse response;

    if (marketOrder.getType().equals(Order.OrderType.ASK)) {
      response = sellCoinmateInstant(marketOrder.getTradableAmount(), CoinmateUtils.getPair(marketOrder.getCurrencyPair()));
    } else if (marketOrder.getType().equals(Order.OrderType.BID)) {
      response = buyCoinmateInstant(marketOrder.getTradableAmount(), CoinmateUtils.getPair(marketOrder.getCurrencyPair()));
    } else {
      throw new CoinmateException("Unknown order type");
    }

    return Long.toString(response.getData());
  }

  @Override
  public String placeLimitOrder(
      LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CoinmateTradeResponse response;

    if (limitOrder.getType().equals(Order.OrderType.ASK)) {
      response = sellCoinmateLimit(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), CoinmateUtils.getPair(limitOrder.getCurrencyPair()));
    } else if (limitOrder.getType().equals(Order.OrderType.BID)) {
      response = buyCoinmateLimit(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), CoinmateUtils.getPair(limitOrder.getCurrencyPair()));
    } else {
      throw new CoinmateException("Unknown order type");
    }

    return Long.toString(response.getData());
  }

  @Override
  public boolean cancelOrder(
      String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CoinmateCancelOrderResponse response = cancelCoinmateOrder(orderId);

    return response.getData();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    DefaultTradeHistoryParamPagingSorted myParams = (DefaultTradeHistoryParamPagingSorted) params;
    return CoinmateAdapters.adaptTradeHistory(
        getCoinmateTradeHistory(myParams.getPageNumber(), myParams.getPageLength(), CoinmateAdapters.adaptOrder(myParams.getOrder())));
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
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
