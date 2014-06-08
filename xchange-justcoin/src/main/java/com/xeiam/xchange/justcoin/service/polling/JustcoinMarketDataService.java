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
package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class JustcoinMarketDataService extends JustcoinMarketDataServiceRaw implements PollingMarketDataService {

  public JustcoinMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    final JustcoinTicker[] justcoinTickers = super.getTickers();

    return JustcoinAdapters.adaptTicker(justcoinTickers, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    final JustcoinDepth justcoinDepth = super.getMarketDepth(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return JustcoinAdapters.adaptOrderBook(currencyPair, justcoinDepth);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

}
