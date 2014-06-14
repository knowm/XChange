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
package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class HitbtcTradeService extends HitbtcTradeServiceRaw implements PollingTradeService {

  public HitbtcTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcOrder[] openOrdersRaw = getOpenOrdersRaw();
    return HitbtcAdapters.adaptOpenOrders(openOrdersRaw);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReport placeMarketOrderRaw = placeMarketOrderRaw(marketOrder);
    return placeMarketOrderRaw.getClientOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReport placeLimitOrderRaw = placeLimitOrderRaw(limitOrder);
    return placeLimitOrderRaw.getClientOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReportResponse cancelOrderRaw = cancelOrderRaw(orderId);
    return cancelOrderRaw.getCancelReject() == null && cancelOrderRaw.getExecutionReport() != null;
  }

  @Override
  public Trades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    int startIndex = 0;
    int maxResults = 1000;
    String symbols = "BTCUSD";

    if (arguments.length == 3) {
      startIndex = (Integer) arguments[0];
      maxResults = (Integer) arguments[1];
      symbols = (String) arguments[2]; // comma separated
    }

    HitbtcOwnTrade[] tradeHistoryRaw = getTradeHistoryRaw(startIndex, maxResults, symbols);
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw);
  }
}
