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
package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.hitbtc.Hitbtc;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades;

/**
 * @author kpysniak
 */
public abstract class HitbtcMarketDataServiceRaw extends HitbtcBasePollingService<Hitbtc> {

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected HitbtcMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Hitbtc.class, exchangeSpecification);
  }

  public HitbtcTicker getHitbtcTicker(CurrencyPair currencyPair) throws IOException {

    return hitbtc.getHitbtcTicker(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

  public HitbtcOrderBook getHitbtcOrderBook(CurrencyPair currencyPair) throws IOException {

    return hitbtc.getOrderBook(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

  public HitbtcTrades getHitbtcTrades(CurrencyPair currencyPair, long from, HitbtcTrades.HitbtcTradesSortOrder sortBy, long startIndex, long maxResults) throws IOException {

    return hitbtc.getTrades(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString(), from + "", sortBy.toString(), startIndex + "", maxResults + "", "object");
  }

  public HitbtcSymbols getHitbtcSymbols() throws IOException {

    return hitbtc.getSymbols();
  }

}
