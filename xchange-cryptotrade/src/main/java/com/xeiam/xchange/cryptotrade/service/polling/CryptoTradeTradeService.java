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
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.cryptotrade.CryptoTradeAdapters;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeCancelOrderReturn;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeHistoryQueryParams;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeHistoryQueryParams.CryptoTradeQueryParamsBuilder;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrdering;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class CryptoTradeTradeService extends CryptoTradeTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CryptoTradeTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    CryptoTradeOrders orderHistory = super.getCryptoTradeOrderHistory();

    return CryptoTradeAdapters.adaptOpenOrders(orderHistory);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return String.valueOf(placeCryptoTradeLimitOrder(limitOrder).getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    CryptoTradeCancelOrderReturn cancelOrderReturn = super.cancelCryptoTradeOrder(Long.valueOf(orderId));

    return cancelOrderReturn.getStatus().equalsIgnoreCase("success");
  }

  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    CryptoTradeQueryParamsBuilder paramsBuilder = CryptoTradeHistoryQueryParams.getQueryParamsBuilder();
    if (args != null) {
      switch (args.length) {
      case 7:
        if (args[6] != null && args[6] instanceof CurrencyPair) {
          paramsBuilder.withCurrencyPair((CurrencyPair) args[6]);
        }
      case 6:
        if (args[5] != null && args[5] instanceof CryptoTradeOrdering) {
          paramsBuilder.withOrdering((CryptoTradeOrdering) args[5]);
        }
      case 5:
        if (args[4] != null && args[4] instanceof Integer) {
          paramsBuilder.withCount((Integer) args[4]);
        }
      case 4:
        if (args[3] != null && args[3] instanceof Long) {
          paramsBuilder.withEndDate((Long) args[3]);
        }
      case 3:
        if (args[2] != null && args[2] instanceof Long) {
          paramsBuilder.withStartDate((Long) args[2]);
        }
      case 2:
        if (args[1] != null && args[1] instanceof Long) {
          paramsBuilder.withEndId((Long) args[1]);
        }
      case 1:
        if (args[0] != null && args[0] instanceof Long) {
          paramsBuilder.withStartId((Long) args[0]);
        }
      }
    }
    CryptoTradeTrades tradeHistory = super.getCryptoTradeTradeHistory(paramsBuilder.build());

    return CryptoTradeAdapters.adaptTrades(tradeHistory);
  }
}
