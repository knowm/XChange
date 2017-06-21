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
import org.knowm.xchange.coinmate.CoinmateAdapters;
import org.knowm.xchange.coinmate.CoinmateUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author Martin Stachon
 */
public class CoinmateMarketDataService extends CoinmateMarketDataServiceRaw implements MarketDataService {

  private final static int TRANSACTIONS_MINUTES_INTO_HISTORY = 60;

  public CoinmateMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return CoinmateAdapters.adaptTicker(getCoinmateTicker(CoinmateUtils.getPair(currencyPair)), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return CoinmateAdapters.adaptOrderBook(getCoinmateOrderBook(CoinmateUtils.getPair(currencyPair), true), currencyPair);

  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return CoinmateAdapters.adaptTrades(getCoinmateTransactions(TRANSACTIONS_MINUTES_INTO_HISTORY));
  }

}
