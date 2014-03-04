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
package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTradeAuthenticated;
import com.xeiam.xchange.cryptotrade.CryptoTradeUtils;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeTransactions;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeCancelOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeHistoryQueryParams;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradePlaceOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class CryptoTradeTradeServiceRaw extends CryptoTradeBasePollingService<CryptoTradeAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CryptoTradeTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptoTradeAuthenticated.class, exchangeSpecification);
  }

  public CryptoTradePlaceOrderReturn placeCryptoTradeLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = CryptoTradeUtils.getCryptoTradeCurrencyPair(limitOrder.getCurrencyPair());
    CryptoTradeOrderType type = limitOrder.getType() == Order.OrderType.BID ? CryptoTradeOrderType.Buy : CryptoTradeOrderType.Sell;
    CryptoTradePlaceOrderReturn cryptoTradePlaceOrderReturn =
        cryptoTradeProxy.trade(pair, type, limitOrder.getLimitPrice(), limitOrder.getTradableAmount(), apiKey, signatureCreator, nextNonce());

    return handleResponse(cryptoTradePlaceOrderReturn);
  }

  public CryptoTradeCancelOrderReturn cancelCryptoTradeOrder(long orderId) {

    CryptoTradeCancelOrderReturn cryptoTradeCancelOrderReturn = cryptoTradeProxy.cancelOrder(orderId, apiKey, signatureCreator, nextNonce());

    return handleResponse(cryptoTradeCancelOrderReturn);
  }

  private static final CryptoTradeHistoryQueryParams NO_QUERY_PARAMS = CryptoTradeHistoryQueryParams.getQueryParamsBuilder().build();

  public CryptoTradeTrades getCryptoTradeTradeHistory() {

    return getCryptoTradeTradeHistory(NO_QUERY_PARAMS);
  }

  public CryptoTradeTrades getCryptoTradeTradeHistory(CryptoTradeHistoryQueryParams queryParams) {

    CryptoTradeTrades trades =
        cryptoTradeProxy.getTradeHistory(queryParams.getStartId(), queryParams.getEndId(), queryParams.getStartDate(), queryParams.getEndDate(), queryParams.getCount(), queryParams.getOrdering(),
            queryParams.getCurrencyPair(), apiKey, signatureCreator, nextNonce());

    return handleResponse(trades);
  }

  public CryptoTradeOrders getCryptoTradeOrderHistory() {

    return getCryptoTradeOrderHistory(NO_QUERY_PARAMS);
  }

  public CryptoTradeOrders getCryptoTradeOrderHistory(CryptoTradeHistoryQueryParams queryParams) {

    CryptoTradeOrders orders =
        cryptoTradeProxy.getOrderHistory(queryParams.getStartId(), queryParams.getEndId(), queryParams.getStartDate(), queryParams.getEndDate(), queryParams.getCount(), queryParams.getOrdering(),
            queryParams.getCurrencyPair(), apiKey, signatureCreator, nextNonce());

    return handleResponse(orders);
  }

  public CryptoTradeTransactions getCryptoTradeTransactionHistory() {

    return getCryptoTradeTransactionHistory(NO_QUERY_PARAMS);
  }

  public CryptoTradeTransactions getCryptoTradeTransactionHistory(CryptoTradeHistoryQueryParams queryParams) {

    CryptoTradeTransactions transactions =
        cryptoTradeProxy.getTransactionHistory(queryParams.getStartId(), queryParams.getEndId(), queryParams.getStartDate(), queryParams.getEndDate(), queryParams.getCount(), queryParams
            .getOrdering(), apiKey, signatureCreator, nextNonce());

    return handleResponse(transactions);
  }
}
