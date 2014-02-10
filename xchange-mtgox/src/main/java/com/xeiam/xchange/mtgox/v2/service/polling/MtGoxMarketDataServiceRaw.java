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
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

public class MtGoxMarketDataServiceRaw extends BasePollingExchangeService {

  protected final MtGoxV2 mtGoxV2;

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
      // Request data
      MtGoxTickerWrapper mtGoxTickerWrapper = mtGoxV2.getTicker(tradableIdentifier, currency);

      if (mtGoxTickerWrapper.getResult().equals("success")) {
        // Adapt to XChange DTOs
        return mtGoxTickerWrapper.getMtGoxTicker();
      }
      else if (mtGoxTickerWrapper.getResult().equals("error")) {
        throw new ExchangeException("Error calling getTicker(): " + mtGoxTickerWrapper.getError());
      }
      else {
        throw new ExchangeException("Error calling getTicker(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getTicker(): " + e.getError(), e);
    }
  }

  public MtGoxDepthWrapper getMtGoxOrderBook(String tradableIdentifier, String currency, Object... args) throws IOException {

    try {
      // Request data
      MtGoxDepthWrapper mtGoxDepthWrapper = null;
      if (args.length > 0) {
        if (args[0] instanceof PollingMarketDataService.OrderBookType) {
          if (PollingMarketDataService.OrderBookType.FULL == args[0]) {
            mtGoxDepthWrapper = mtGoxV2.getFullDepth(tradableIdentifier, currency);
          }
          else {
            mtGoxDepthWrapper = mtGoxV2.getPartialDepth(tradableIdentifier, currency);
          }
        }
        else {
          throw new ExchangeException("Orderbook size argument must be OrderBookType enum!");
        }
      }
      else { // default to full orderbook
        mtGoxDepthWrapper = mtGoxV2.getFullDepth(tradableIdentifier, currency);
      }
      if (mtGoxDepthWrapper.getResult().equals("success")) {
        return mtGoxDepthWrapper;
      }
      else if (mtGoxDepthWrapper.getResult().equals("error")) {
        throw new ExchangeException("Error calling getMtGoxOrderBook(): " + mtGoxDepthWrapper.getError());
      }
      else {
        throw new ExchangeException("Error calling getMtGoxOrderBook(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getMtGoxOrderBook(): " + e.getError(), e);
    }
  }

  public MtGoxTradesWrapper getMtGoxTrades(String tradableIdentifier, String currency, Object... args) throws IOException {

    try {
      MtGoxTradesWrapper mtGoxTradeWrapper = null;

      if (args.length > 0) {
        Long sinceTimeStamp = (Long) args[0];
        // Request data
        mtGoxTradeWrapper = mtGoxV2.getTrades(tradableIdentifier, currency, sinceTimeStamp);
      }
      else {
        // Request data
        mtGoxTradeWrapper = mtGoxV2.getTrades(tradableIdentifier, currency);
      }

      if (mtGoxTradeWrapper.getResult().equals("success")) {
        return mtGoxTradeWrapper;
      }
      else if (mtGoxTradeWrapper.getResult().equals("error")) {
        throw new ExchangeException("Error calling getTrades(): " + mtGoxTradeWrapper.getError());
      }
      else {
        throw new ExchangeException("Error calling getTrades(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getTrades(): " + e.getError(), e);
    }
  }
}
