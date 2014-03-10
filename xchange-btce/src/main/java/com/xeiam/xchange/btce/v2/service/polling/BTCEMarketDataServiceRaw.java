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
package com.xeiam.xchange.btce.v2.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btce.v2.BTCE;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;

/**
 * <p>
 * Implementation of the market data service for BTCE
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
@Deprecated
public class BTCEMarketDataServiceRaw extends BTCEBasePollingService {

  protected final BTCE btce;

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    btce = RestProxyFactory.createProxy(BTCE.class, exchangeSpecification.getSslUri());
  }

  public BTCETickerWrapper getBTCETicker(CurrencyPair currencyPair) throws IOException {

    BTCETickerWrapper btceTicker = btce.getTicker(currencyPair.baseCurrency.toLowerCase(), currencyPair.counterCurrency.toLowerCase());

    // Adapt to XChange DTOs
    return btceTicker;
  }

  public BTCEDepth getBTCEOrderBook(CurrencyPair currencyPair) throws IOException {

    BTCEDepth btceDepth = btce.getDepth(currencyPair.baseCurrency.toLowerCase(), currencyPair.counterCurrency.toLowerCase());

    return btceDepth;
  }

  /**
   * Get recent trades from exchange
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @param args Optional arguments. This implementation assumes
   *          args[0] is integer value limiting number of trade items to get.
   *          -1 or missing -> use default output of 150 items from API v.2
   *          int from 1 to 2000 -> use API v.3 to get corresponding number of trades
   * @return Trades object
   * @throws IOException
   */
  public BTCETrade[] getBTCETrades(CurrencyPair currencyPair) throws IOException {

    BTCETrade[] BTCETrades = btce.getTrades(currencyPair.baseCurrency.toLowerCase(), currencyPair.counterCurrency.toLowerCase());

    return BTCETrades;

  }

  public ExchangeInfo getBTCEExchangeInfo() throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
