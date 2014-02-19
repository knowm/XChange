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
package com.xeiam.xchange.mtgox.v0.service.polling.marketdata;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.v0.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v0.MtGoxV0;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxDepth;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTrades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for Mt Gox V0
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 * <p>
 * 
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
public class MtGoxMarketDataService extends MtGoxBasePollingService implements PollingMarketDataService {

  private final MtGoxV0 mtGoxV0;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public MtGoxMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.mtGoxV0 = RestProxyFactory.createProxy(MtGoxV0.class, exchangeSpecification.getSslUri());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency, Object... args) {

    verify(tradableIdentifier, currency);

    // Request data
    MtGoxTicker mtGoxTicker = mtGoxV0.getTicker(currency);

    // Adapt to XChange DTOs
    return MtGoxAdapters.adaptTicker(mtGoxTicker, tradableIdentifier, currency);

  }

  @Override
  public OrderBook getOrderBook(String tradableIdentifier, String currency, Object... args) {

    verify(tradableIdentifier, currency);

    MtGoxDepth mtgoxFullDepth = mtGoxV0.getFullDepth(currency);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtgoxFullDepth.getAsks(), currency, "ask", "");
    List<LimitOrder> bids = MtGoxAdapters.adaptOrders(mtgoxFullDepth.getBids(), currency, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) {

    verify(tradableIdentifier, currency);

    // Request data
    MtGoxTrades[] mtGoxTrades = mtGoxV0.getTrades(currency);

    return MtGoxAdapters.adaptTrades(mtGoxTrades);
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
