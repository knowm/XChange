/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.Coinmate;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBook;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTicker;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTransactions;
import si.mazi.rescu.RestProxyFactory;

/** @author Martin Stachon */
public class CoinmateMarketDataServiceRaw extends CoinmateBaseService {

  private final Coinmate coinmate;

  public CoinmateMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.coinmate =
        RestProxyFactory.createProxy(
            Coinmate.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public CoinmateTicker getCoinmateTicker(String currencyPair) throws IOException {
    CoinmateTicker ticker = coinmate.getTicker(currencyPair);

    throwExceptionIfError(ticker);

    return ticker;
  }

  public CoinmateOrderBook getCoinmateOrderBook(String currencyPair, boolean groupByPriceLimit)
      throws IOException {
    CoinmateOrderBook orderBook = coinmate.getOrderBook(currencyPair, groupByPriceLimit);

    throwExceptionIfError(orderBook);

    return orderBook;
  }

  public CoinmateTransactions getCoinmateTransactions(int minutesIntoHistory, String currencyPair)
      throws IOException {
    CoinmateTransactions transactions = coinmate.getTransactions(minutesIntoHistory, currencyPair);

    throwExceptionIfError(transactions);

    return transactions;
  }
}
