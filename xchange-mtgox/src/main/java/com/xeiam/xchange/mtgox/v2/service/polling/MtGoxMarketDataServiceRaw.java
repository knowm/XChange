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
package com.xeiam.xchange.mtgox.v2.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxException;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxDepthWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTickerWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTradesWrapper;
import com.xeiam.xchange.mtgox.v2.service.MtGoxBaseService;
import com.xeiam.xchange.utils.Assert;

public class MtGoxMarketDataServiceRaw extends MtGoxBaseService {

  private final MtGoxV2 mtGoxV2;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected MtGoxMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, exchangeSpecification.getSslUri());
  }

  public MtGoxTicker getMtGoxTicker(String tradableIdentifier, String currency) throws IOException {

    try {
      MtGoxTickerWrapper mtGoxTickerWrapper = mtGoxV2.getTicker(tradableIdentifier, currency);
      return mtGoxTickerWrapper.getMtGoxTicker();
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getTicker(): " + e.getError(), e);
    }
  }

  public MtGoxDepthWrapper getMtGoxFullOrderBook(String tradableIdentifier, String currency) throws IOException {

    try {
      MtGoxDepthWrapper mtGoxDepthWrapper = null;
      mtGoxDepthWrapper = mtGoxV2.getFullDepth(tradableIdentifier, currency);
      return mtGoxDepthWrapper;
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getMtGoxFullOrderBook(): " + e.getError(), e);
    }
  }

  public MtGoxDepthWrapper getMtGoxPartialOrderBook(String tradableIdentifier, String currency) throws IOException {

    try {
      MtGoxDepthWrapper mtGoxDepthWrapper = null;
      mtGoxDepthWrapper = mtGoxV2.getPartialDepth(tradableIdentifier, currency);
      return mtGoxDepthWrapper;
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getMtGoxPartialOrderBook(): " + e.getError(), e);
    }
  }

  public MtGoxTradesWrapper getMtGoxTrades(String tradableIdentifier, String currency, Long sinceTimeStamp) throws IOException {

    try {
      MtGoxTradesWrapper mtGoxTradeWrapper = null;

      if (sinceTimeStamp != null) {
        // Request data with since param
        mtGoxTradeWrapper = mtGoxV2.getTrades(tradableIdentifier, currency, sinceTimeStamp);
      }
      else {
        // Request data
        mtGoxTradeWrapper = mtGoxV2.getTrades(tradableIdentifier, currency);
      }

      return mtGoxTradeWrapper;
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getTrades(): " + e.getError(), e);
    }
  }
}
