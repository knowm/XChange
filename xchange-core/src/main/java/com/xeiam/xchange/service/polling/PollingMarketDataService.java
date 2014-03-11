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
package com.xeiam.xchange.service.polling;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
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
 * <p>
 * The implementation of this service is expected to be based on a client polling mechanism of some kind
 * </p>
 */
public abstract class PollingMarketDataService {

  private static final ExecutorService executorService = Executors.newCachedThreadPool();

  /**
   * <p>
   * Get a ticker representing the current exchange rate
   * </p>
   * 
   * @param CurrencyPair currencyPair (e.g. BTC/USD)
   * @return The Ticker, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public abstract Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * <p>
   * Get an order book representing the current offered exchange rates (market depth)
   * </p>
   * 
   * @param CurrencyPair currencyPair (e.g. BTC/USD)
   * @param args Optional arguments. Exchange-specific
   * @return The OrderBook, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public abstract OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * <p>
   * Get the trades recently performed by the exchange
   * </p>
   * 
   * @param CurrencyPair currencyPair (e.g. BTC/USD)
   * @param args Optional arguments. Exchange-specific
   * @return The Trades, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public abstract Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * <p>
   * Get public exchange info, such as allowed currency pairs, fees etc.
   * </p>
   * 
   * @return ExchangeInfo object
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public abstract ExchangeInfo getExchangeInfo() throws ExchangeException, IOException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException;

  /*
   * The following methods are Asynchronous versions of the ones above. They create a new thread to retrieve data (thus allowing multiple requests
   * at the same time), and return the Future<?> object. The get() method may be called upon that Future<?> Object to retrieve the result, or block
   * until the result has been retrieved.
   */

  /**
   * <p>
   * Asynchronously get a ticker representing the current exchange rate
   * </p>
   * 
   * @param CurrencyPair currencyPair (e.g. BTC/USD)
   * @return The Future<Ticker> object. Call get() to retrieve result (or block until result retrieved)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public Future<Ticker> getTickerAsync(final CurrencyPair currencyPair, final Object... args) {

    class CallableAccountInfoRequest implements Callable<Ticker> {

      @Override
      public Ticker call() throws Exception {

        return getTicker(currencyPair, args);
      }

    }
    return executorService.submit(new CallableAccountInfoRequest());
  }

  /**
   * <p>
   * Asynchronously get an order book representing the current offered exchange rates (market depth)
   * </p>
   * 
   * @param CurrencyPair currencyPair (e.g. BTC/USD)
   * @param args Optional arguments. Exchange-specific
   * @return The Future<OrderBook> object. Call get() to retrieve result (or block until result retrieved)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public Future<OrderBook> getOrderBookAsync(final CurrencyPair currencyPair, final Object... args) {

    class CallableAccountInfoRequest implements Callable<OrderBook> {

      @Override
      public OrderBook call() throws Exception {

        return getOrderBook(currencyPair, args);
      }

    }
    return executorService.submit(new CallableAccountInfoRequest());
  }

  /**
   * <p>
   * Asynchronously get the trades recently performed by the exchange
   * </p>
   * 
   * @param CurrencyPair currencyPair (e.g. BTC/USD)
   * @param args Optional arguments. Exchange-specific
   * @return The Future<Trades> object. Call get() to retrieve result (or block until result retrieved)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public Future<Trades> getTradesAsync(final CurrencyPair currencyPair, final Object... args) {

    class CallableAccountInfoRequest implements Callable<Trades> {

      @Override
      public Trades call() throws Exception {

        return getTrades(currencyPair, args);
      }

    }
    return executorService.submit(new CallableAccountInfoRequest());
  }

  /**
   * <p>
   * Asynchronously get public exchange info, such as allowed currency pairs, fees etc.
   * </p>
   * 
   * @return The Future<ExchangeInfo> object. Call get() to retrieve result (or block until result retrieved)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public Future<ExchangeInfo> getExchangeInfoAsync() {

    class CallableAccountInfoRequest implements Callable<ExchangeInfo> {

      @Override
      public ExchangeInfo call() throws Exception {

        return getExchangeInfo();
      }

    }
    return executorService.submit(new CallableAccountInfoRequest());
  }

  /**
   * Retrieves the raw layer for calling of raw methods.
   * 
   * @return
   */
  public abstract Object getRaw();
}
