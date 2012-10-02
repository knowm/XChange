/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.virtex.service.marketdata.polling;

import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.PacingViolationException;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.virtex.VirtExAdapters;
import com.xeiam.xchange.virtex.VirtExUtils;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTrade;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the market data service for Mt Gox
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VirtExPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService, CachedDataSession {

  /**
   * time stamps used to pace API calls
   */
  private long tickerRequestTimeStamp = 0L;
  private long orderBookRequestTimeStamp = 0L;
  private long fullOrderBookRequestTimeStamp = 0L;
  private long tradesRequestTimeStamp = 0L;

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBase = String.format("%s/api/", exchangeSpecification.getUri(), exchangeSpecification.getVersion());

  /**
   * @param exchangeSpecification The exchange specification
   */
  public VirtExPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    // check for pacing violation
    if (System.currentTimeMillis() < tickerRequestTimeStamp + getRefreshRate()) {
      tickerRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("VirtEx caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    tickerRequestTimeStamp = System.currentTimeMillis();

    // Request data
   VirtExTicker VirtExTicker = httpTemplate.getForJsonObject(apiBase + currency + "/ticker.json", VirtExTicker.class, mapper, new HashMap<String, String>());
     //VirtExTicker VirtExTicker = httpTemplate.getForJsonObject("https://www.cavirtex.com/api/CAD/ticker.json", VirtExTicker.class, mapper, new HashMap<String, String>());

    
    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTicker(VirtExTicker);
  }

  @Override
  public OrderBook getOrderBook(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    // Check for pacing violation
    if (System.currentTimeMillis() < orderBookRequestTimeStamp + getRefreshRate()) {
      orderBookRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("VirtEx caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    orderBookRequestTimeStamp = System.currentTimeMillis();

    // Request data
    VirtExDepth VirtExDepth = httpTemplate.getForJsonObject(apiBase + currency + "/orderbook.json", VirtExDepth.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    //List<LimitOrder> asks = VirtExAdapters.adaptOrders(VirtExDepth.getAsks(), currency, "ask", "");
    //List<LimitOrder> bids = VirtExAdapters.adaptOrders(VirtExDepth.getBids(), currency, "bid", "");
    
    List<LimitOrder> asks = null;
    List<LimitOrder> bids = null;

    return new OrderBook(asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);
    // Check for pacing violation
    if (System.currentTimeMillis() < tradesRequestTimeStamp + getRefreshRate()) {
      tradesRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("VirtEx caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    tradesRequestTimeStamp = System.currentTimeMillis();

    // Request data
    VirtExTrade[] VirtExTrades = httpTemplate.getForJsonObject(apiBase + currency + "/trades.json", VirtExTrade[].class, mapper, new HashMap<String, String>());

    return VirtExAdapters.adaptTrades(VirtExTrades);
  }

  /**
   * Verify
   * 
   * @param tradableIdentifier
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(VirtExUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

  @Override
  public int getRefreshRate() {

    return VirtExUtils.REFRESH_RATE;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return VirtExUtils.CURRENCY_PAIRS;
  }

@Override
public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {
	// TODO Auto-generated method stub
	return null;
}

}
