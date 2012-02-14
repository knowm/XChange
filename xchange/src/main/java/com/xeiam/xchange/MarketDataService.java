/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.xeiam.xchange.marketdata.dto.OrderBook;
import com.xeiam.xchange.marketdata.dto.Ticker;
import com.xeiam.xchange.marketdata.dto.Trades;

/**
 * <p>
 * Interface to provide the following to {@link Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to explore the market data</li>
 * </ul>
 * TODO Consider the blocking nature and perhaps go for a TODO MarketDataCallback or MarketDataListener approach?  
 * 
 * @since 0.0.1
 */
public interface MarketDataService {

  /**
   * @return A collection of {@link Ticker}s representing the market data within the range
   */
  Collection<Ticker> getHistoricalMarketData(DateTime validFrom, DateTime validTo) throws NotAvailableFromExchangeException;

  List<String> getExchangeSymbols() throws NotAvailableFromExchangeException;

  OrderBook getDepth(String symbol) throws NotAvailableFromExchangeException;

  OrderBook getFullDepth(String symbol) throws NotAvailableFromExchangeException;

  Trades getTrades(String symbol) throws NotAvailableFromExchangeException;

  Ticker getTicker(String symbol) throws NotAvailableFromExchangeException;

}
