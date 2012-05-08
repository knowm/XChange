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
package com.xeiam.xchange.service.marketdata.async;

import java.util.List;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;

/**
 * <p>
 * Interface to provide the following to {@link Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to explore the market data</li>
 * </ul>
 */
public interface AsyncMarketDataService {

  /**
   * <p>
   * Ask the exchange what symbol pairs it supports
   * </p>
   * 
   * @return The symbol pairs supported by this exchange (e.g. EUR/USD)
   * @throws NotAvailableFromExchangeException
   */
  List<CurrencyPair> getExchangeSymbols() throws NotAvailableFromExchangeException;

  /**
   * <p>
   * Get a ticker representing the current exchange rate
   * </p>
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @return The Ticker
   * @throws NotAvailableFromExchangeException If the exchange does not support this request
   */
  Ticker getTicker(String tradableIdentifier, String currency) throws NotAvailableFromExchangeException;

  /**
   * <p>
   * Get an order book representing the current offered exchange rates
   * </p>
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @return The OrderBook
   * @throws NotAvailableFromExchangeException If the exchange does not support this request
   */
  OrderBook getOrderBook(String tradableIdentifier, String currency) throws NotAvailableFromExchangeException;

  /**
   * <p>
   * Get a full order book representing the current offered exchange rates
   * </p>
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @return The OrderBook
   * @throws NotAvailableFromExchangeException If the exchange does not support this request
   */
  OrderBook getFullOrderBook(String tradableIdentifier, String currency) throws NotAvailableFromExchangeException;

  /**
   * <p>
   * Get the trades recently performed by the exchange
   * </p>
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @return The Trades
   * @throws NotAvailableFromExchangeException If the exchange does not support this request
   */
  Trades getTrades(String tradableIdentifier, String currency) throws NotAvailableFromExchangeException;

}
