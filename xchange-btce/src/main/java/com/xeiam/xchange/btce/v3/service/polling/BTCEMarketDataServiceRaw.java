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
package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCE;
import com.xeiam.xchange.btce.v3.BTCEUtils;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.Assert;

/**
 * @author Matija Mazi
 * @author ObsessiveOrange
 * <p>
 * Implementation of the market data service for BTCE
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BTCEMarketDataServiceRaw extends BTCEBaseService {

  protected final BTCE btce;

  // private static final int PARTIAL_SIZE = 150;
  private static final int FULL_SIZE = 2000;

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {
	super(exchangeSpecification);
    this.btce = RestProxyFactory.createProxy(BTCE.class, exchangeSpecification.getSslUri());
  }

  public BTCETickerWrapper getBTCETicker(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);
    return btce.getTicker(tradableIdentifier.toLowerCase(), currency.toLowerCase(), 1);
  }

  /**
   * Get market depth from exchange
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG). First currency of the pair
   * @param currency The currency of interest, null if irrelevant. Second currency of the pair
   * @param args Optional arguments. Exchange-specific. This implementation assumes:
   *          Integer value from 1 to 2000 -> get corresponding number of items
   * @return a BTCEDepthWrapper object containing a map of BTCEDepth objects - the depth at different prices.
   * @throws IOException
   */
  public BTCEDepthWrapper getBTCEOrderBook(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);
    BTCEDepthWrapper btceDepthWrapper = null;

    if (args.length > 0) {
      Object arg = args[0];
      if (!(arg instanceof Integer) || ((Integer) arg < 1) || ((Integer) arg > FULL_SIZE)) {
        throw new ExchangeException("Orderbook size argument must be an Integer in the range: (1, 2000)!");
      }
      else {
        btceDepthWrapper = btce.getDepth(tradableIdentifier.toLowerCase(), currency.toLowerCase(), (Integer) arg, 1);
      }
    }
    else { // default to full orderbook
      btceDepthWrapper = btce.getDepth(tradableIdentifier.toLowerCase(), currency.toLowerCase(), FULL_SIZE, 1);
    }

    return btceDepthWrapper;
  }

  /**
   * Get recent trades from exchange
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @param args Optional arguments. This implementation assumes
   *          args[0] is integer value limiting number of trade items to get.
   *          -1 or missing -> use default 2000 max fetch value
   *          int from 1 to 2000 -> use API v.3 to get corresponding number of trades
   * @return Array of BTCETrade objects
   * @throws IOException
   */
  public BTCETrade[] getBTCETrades(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);

    int numberOfItems = -1;
    try {
      numberOfItems = (Integer) args[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }
    BTCETrade[] btceTrades = null;

    if (numberOfItems == -1) {
      btceTrades = btce.getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase(), 2000, 1).getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase());
    }
    else {
      btceTrades = btce.getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase(), numberOfItems, 1).getTrades(tradableIdentifier.toLowerCase(), currency.toLowerCase());
    }
    return btceTrades;

  }

  /**
   * Verify that both currencies can make valid pair
   * 
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) throws IOException {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(BTCEUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);
  }

  public List<CurrencyPair> getExchangeSymbols() {

    return BTCEUtils.CURRENCY_PAIRS;
  }

  public BTCEExchangeInfo getBTCEExchangeInfo() throws IOException {

    return btce.getInfo();
  }

}
