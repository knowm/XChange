/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
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

package com.xeiam.xchange.coinmate.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinmate.CoinmateAdapters;
import com.xeiam.xchange.coinmate.CoinmateException;
import com.xeiam.xchange.coinmate.CoinmateUtils;
import com.xeiam.xchange.coinmate.dto.trade.CoinmateCancelOrderResponse;
import com.xeiam.xchange.coinmate.dto.trade.CoinmateOpenOrders;
import com.xeiam.xchange.coinmate.dto.trade.CoinmateTradeResponse;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPagingSorted;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsSorted;

/**
 * @author Martin Stachon
 */
public class CoinmateTradeService extends CoinmateTradeServiceRaw implements PollingTradeService {

  public CoinmateTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    // BTC_EUR by default
    String currencyPair = CoinmateUtils.getPair(CoinmateAdapters.COINMATE_DEFAULT_PAIR);

    CoinmateOpenOrders coinmateOpenOrders = getCoinmateOpenOrders(currencyPair);
    return CoinmateAdapters.adaptOpenOrders(coinmateOpenOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
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
  public String placeLimitOrder(LimitOrder limitOrder)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
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
  public boolean cancelOrder(String orderId)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CoinmateCancelOrderResponse response = cancelCoinmateOrder(orderId);

    return response.getData();
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return getTradeHistory(createTradeHistoryParams());
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

}
