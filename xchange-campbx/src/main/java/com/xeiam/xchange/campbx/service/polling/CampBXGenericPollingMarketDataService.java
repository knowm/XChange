/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.campbx.CampBXAdapters;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author Matija Mazi
 */
public class CampBXGenericPollingMarketDataService extends CampBXPollingMarketDataService implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CampBXGenericPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency, Object... args) throws IOException {

    CampBXTicker campbxTicker = getCampBXTicker(tradableIdentifier, currency);

    return CampBXAdapters.adaptTicker(campbxTicker, currency, tradableIdentifier);
  }

  @Override
  public OrderBook getOrderBook(String tradableIdentifier, String currency, Object... args) throws IOException {

    CampBXOrderBook campBXOrderBook = getCampBXOrderBook(tradableIdentifier, currency);

    return CampBXAdapters.adaptOrders(campBXOrderBook, currency, tradableIdentifier);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return getCampBXExchangeSymbols();
  }
}
