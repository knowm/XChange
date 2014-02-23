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
package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.coinbase.CoinbaseAdapters;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author jamespedwards42
 */
public final class CoinbaseTradeService extends CoinbaseTradeServiceRaw implements PollingTradeService {

  public CoinbaseTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, IOException {

    final CoinbaseTransfer transfer = marketOrder.getType().equals(OrderType.BID) ? super.buy(marketOrder.getTradableAmount()) : super.sell(marketOrder.getTradableAmount());
    return transfer.getTransactionId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Authenticated resource which returns the user’s Bitcoin purchases and sells. Sorted in descending order by creation date.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transfers/index.html">coinbase.com/api/doc/1.0/transfers/index.html</a>
   * @param  arguments Optional Integer arguments page (arg[0]) and limit (arg[1]).  If no arguments are given then page 1 will 
   * be returned and the results are limited to 25 per page by coinbase by default.
   */
  @Override
  public Trades getTradeHistory(Object... arguments) throws ExchangeException, IOException {

    Integer page = null;
    Integer limit = null;
    if (arguments != null && arguments.length > 0) {
      if (!(arguments[0] instanceof Integer))
        throw new ExchangeException("args[0] must be of type Integer.");
      page = (Integer) arguments[0];

      if (arguments.length > 1) {
        if (!(arguments[1] instanceof Integer))
          throw new ExchangeException("args[1] must be of type Integer.");
        limit = (Integer) arguments[1];
      }
    }

    final CoinbaseTransfers transfers = super.getCoinbaseTransfers(page, limit);
    return CoinbaseAdapters.adaptTrades(transfers);
  }

}
