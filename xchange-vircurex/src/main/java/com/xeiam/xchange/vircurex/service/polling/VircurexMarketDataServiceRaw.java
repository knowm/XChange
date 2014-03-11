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
package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.vircurex.Vircurex;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexDepth;
import com.xeiam.xchange.vircurex.service.VircurexBaseService;

/**
 * <p>
 * Implementation of the market data service for Vircurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VircurexMarketDataServiceRaw extends VircurexBaseService {

  private final Vircurex vircurex;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VircurexMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    vircurex = RestProxyFactory.createProxy(Vircurex.class, exchangeSpecification.getSslUri());
  }

  public VircurexDepth getVircurexOrderBook(CurrencyPair currencyPair) throws IOException {

    VircurexDepth vircurexDepth = vircurex.getFullDepth(currencyPair.counterSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return vircurexDepth;
  }

}
