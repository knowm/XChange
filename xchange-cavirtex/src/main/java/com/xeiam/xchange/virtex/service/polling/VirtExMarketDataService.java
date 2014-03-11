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
package com.xeiam.xchange.virtex.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.virtex.VirtExAdapters;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTrade;

/**
 * <p>
 * Implementation of the market data service for VirtEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VirtExMarketDataService extends PollingMarketDataService {

  final ExchangeSpecification exchangeSpecification;
  final VirtExMarketDataServiceRaw raw;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VirtExMarketDataService(ExchangeSpecification exchangeSpecification) {

    this.exchangeSpecification = exchangeSpecification;
    raw = new VirtExMarketDataServiceRaw(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    raw.verify(currencyPair);

    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTicker(raw.getVirtExTicker(currencyPair.baseSymbol), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    raw.verify(currencyPair);

    // Request data
    VirtExDepth virtExDepth = raw.getVirtExOrderBook(currencyPair.counterSymbol);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VirtExAdapters.adaptOrders(virtExDepth.getAsks(), currencyPair.counterSymbol, "ask", "");
    List<LimitOrder> bids = VirtExAdapters.adaptOrders(virtExDepth.getBids(), currencyPair.counterSymbol, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    raw.verify(currencyPair);

    // Request data
    VirtExTrade[] virtExTrades = raw.getVirtExTrades(currencyPair.counterSymbol);

    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTrades(virtExTrades, currencyPair);
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Object getRaw() {

    return raw;
  }
}
