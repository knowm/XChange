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
package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bter.BTERAdapters;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class BTERPollingMarketDataService extends PollingMarketDataService {

  final ExchangeSpecification exchangeSpecification;
  final BTERPollingMarketDataServiceRaw raw;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTERPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    this.exchangeSpecification = exchangeSpecification;
    raw = new BTERPollingMarketDataServiceRaw(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    raw.verify(currencyPair);

    BTERTicker ticker = raw.getBTERTicker(currencyPair.baseCurrency, currencyPair.counterCurrency);

    return BTERAdapters.adaptTicker(currencyPair, ticker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    raw.verify(currencyPair);

    BTERDepth bterDepth = raw.getBTEROrderBook(currencyPair.baseCurrency, currencyPair.counterCurrency);

    return BTERAdapters.adaptOrderBook(bterDepth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERTradeHistory tradeHistory =
        (args != null && args.length > 0 && args[0] != null && args[0] instanceof String) ? raw.getBTERTradeHistorySince(currencyPair.baseCurrency, currencyPair.counterCurrency, (String) args[0])
            : raw.getBTERTradeHistory(currencyPair.baseCurrency, currencyPair.counterCurrency);

    return BTERAdapters.adaptTrades(tradeHistory, currencyPair);
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
