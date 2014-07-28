/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTrade;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrade;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrades;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * <p>
 * Implementation of the market data service for CryptoTrade
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class CryptoTradeMarketDataServiceRaw extends CryptoTradeBasePollingService<CryptoTrade> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CryptoTradeMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptoTrade.class, exchangeSpecification);
  }

  public CryptoTradeTicker getCryptoTradeTicker(CurrencyPair currencyPair) throws IOException {

    CryptoTradeTicker cryptoTradeTicker = cryptoTradeProxy.getTicker(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeTicker);
  }

  public CryptoTradeDepth getCryptoTradeOrderBook(CurrencyPair currencyPair) throws IOException {

    CryptoTradeDepth cryptoTradeDepth = cryptoTradeProxy.getFullDepth(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeDepth);
  }

  public List<CryptoTradePublicTrade> getCryptoTradeTradeHistory(CurrencyPair currencyPair) throws IOException {

    CryptoTradePublicTrades cryptoTradeDepth = cryptoTradeProxy.getTradeHistory(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return handleResponse(cryptoTradeDepth).getPublicTrades();
  }

  public List<CryptoTradePublicTrade> getCryptoTradeTradeHistory(CurrencyPair currencyPair, long sinceTimestamp) throws IOException {

    CryptoTradePublicTrades cryptoTradeDepth = cryptoTradeProxy.getTradeHistory(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase(), sinceTimestamp);

    return handleResponse(cryptoTradeDepth).getPublicTrades();
  }

}
