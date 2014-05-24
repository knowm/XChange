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
package com.xeiam.xchange.virtex.v1.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.virtex.v1.VirtEx;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTrade;
import com.xeiam.xchange.virtex.v1.service.VirtexBaseService;

/**
 * <p>
 * Implementation of the market data service for VirtEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */

@Deprecated
public class VirtExMarketDataServiceRaw extends VirtexBaseService {

  private final VirtEx virtEx;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VirtExMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.virtEx = RestProxyFactory.createProxy(VirtEx.class, exchangeSpecification.getSslUri());
  }

  public VirtExTicker getVirtExTicker(String currency) throws IOException {

    // Request data
    VirtExTicker virtExTicker = virtEx.getTicker(currency);

    // Adapt to XChange DTOs
    return virtExTicker;
  }

  public VirtExDepth getVirtExOrderBook(String currency) throws IOException {

    // Request data
    VirtExDepth virtExDepth = virtEx.getFullDepth(currency);

    return virtExDepth;
  }

  public VirtExTrade[] getVirtExTrades(String currency) throws IOException {

    // Request data
    VirtExTrade[] virtExTrades = virtEx.getTrades(currency);

    return virtExTrades;
  }

}
