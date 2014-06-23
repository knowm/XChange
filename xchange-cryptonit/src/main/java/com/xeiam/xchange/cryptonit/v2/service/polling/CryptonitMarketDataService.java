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
package com.xeiam.xchange.cryptonit.v2.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for Cryptonit
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */

public class CryptonitMarketDataService extends CryptonitMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptonitMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    return CryptonitAdapters.adaptTicker(getCryptonitTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    List<LimitOrder> asks = CryptonitAdapters.adaptOrders(getCryptonitAsks(currencyPair, 1000), currencyPair, "ask", "");
    List<LimitOrder> bids = CryptonitAdapters.adaptOrders(getCryptonitBids(currencyPair, 1000), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
	CryptonitOrders cryptonitTrades = getCryptonitTrades(currencyPair, 1000);

    // Adapt to XChange DTOs
    return CryptonitAdapters.adaptTrades(cryptonitTrades, currencyPair);
  }

}
