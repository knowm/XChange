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
package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.huobi.HuobiAdapters;
import com.xeiam.xchange.huobi.dto.trade.polling.HuobiExecutionReport;
import com.xeiam.xchange.huobi.dto.trade.polling.HuobiOrder;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;

/**
 * @author timmolter
 */
public class HuobiTradeService extends HuobiTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public HuobiTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return new OpenOrders(HuobiAdapters.adaptOrders(getHuobiOpenOrders()));
  }

    public HuobiOrder.Status getOrderInfo(LimitOrder limitOrder) throws IOException {

        HuobiOrder orderInfo = getHuobiOrderInfo(limitOrder);
        return HuobiAdapters.adaptOrderStatus(orderInfo);
    }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

      throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

      HuobiExecutionReport executionReport = placeHuobiLimitOrder(limitOrder);
      if (executionReport != null && "success".equals(executionReport.getResult())) {
          return executionReport.getId();
      } else {
          return null;
      }
  }

    @Override
    public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        HuobiExecutionReport executionReport = cancelHuobiOrder(orderId);

        if (executionReport != null && "success".equals(executionReport.getResult())) {
            return true;
        } else {
            return false;
        }
    }

    public String modifyLimitOrder(LimitOrder limitOrder) throws IOException {

        HuobiExecutionReport executionReport = modifyHuobiLimitOrder(limitOrder);
        if (executionReport != null && "success".equals(executionReport.getResult())) {
            return executionReport.getId();
        } else {
            return null;
        }
    }

    @Override
    public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return null;
    }
}
