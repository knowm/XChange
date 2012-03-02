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
package com.xeiam.xchange.mtgox.v1.service.marketdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.PacingViolationException;
import com.xeiam.xchange.mtgox.v1.MtGoxProperties;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.marketdata.MarketDataService;
import com.xeiam.xchange.service.marketdata.OrderBook;
import com.xeiam.xchange.service.marketdata.Ticker;
import com.xeiam.xchange.service.marketdata.Trade;
import com.xeiam.xchange.service.marketdata.Trades;
import com.xeiam.xchange.utils.HttpUtils;

/**
 * <p>
 * Implementation of the market data service for Mt Gox
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class MtGoxMarketDataService extends BaseExchangeService implements MarketDataService, CachedDataSession {

  /**
   * Provides logging for this class
   */
  private final Logger log = LoggerFactory.getLogger(MtGoxMarketDataService.class);

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
  private final String apiBase = String.format("%s/api/%s/", apiURI, apiVersion);

  /**
   * @param exchangeSpecification The exchange specification
   */
  public MtGoxMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(String symbol) {

    // check for pacing violation
    if (System.currentTimeMillis() < tickerRequestTimeStamp + getRefreshRate()) {
      tickerRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("MtGox caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    tickerRequestTimeStamp = System.currentTimeMillis();

    // Request data
    MtGoxTicker mtGoxTicker = HttpUtils.getForJsonObject(apiBase + symbol + "/public/ticker?raw", MtGoxTicker.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    Ticker ticker = new Ticker();

    ticker.setLast(mtGoxTicker.getLast_orig().getValue_int());
    ticker.setVolume(mtGoxTicker.getVol().getValue_int());

    return ticker;

  }

  @Override
  public OrderBook getOrderBook(String symbol) {

    // check for pacing violation
    if (System.currentTimeMillis() < orderBookRequestTimeStamp + getRefreshRate()) {
      orderBookRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("MtGox caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    orderBookRequestTimeStamp = System.currentTimeMillis();

    // Request data
    MtGoxDepth mtgoxDepth = HttpUtils.getForJsonObject(apiBase + symbol + "/public/depth?raw", MtGoxDepth.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    OrderBook depth = new OrderBook();
    depth.setAsks(new ArrayList(mtgoxDepth.getAsks()));
    depth.setBids(new ArrayList(mtgoxDepth.getBids()));

    return depth;
  }

  @Override
  public OrderBook getFullOrderBook(String symbol) {

    // check for pacing violation
    if (System.currentTimeMillis() < fullOrderBookRequestTimeStamp + getRefreshRate()) {
      fullOrderBookRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("MtGox caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    fullOrderBookRequestTimeStamp = System.currentTimeMillis();

    // Request data
    MtGoxDepth mtgoxFullDepth = HttpUtils.getForJsonObject(apiBase + symbol + "/public/fulldepth?raw", MtGoxDepth.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    OrderBook depth = new OrderBook();
    depth.setAsks(new ArrayList(mtgoxFullDepth.getAsks()));
    depth.setBids(new ArrayList(mtgoxFullDepth.getBids()));

    return depth;
  }

  @Override
  public Trades getTrades(String symbol) {

    // check for pacing violation
    if (System.currentTimeMillis() < tradesRequestTimeStamp + getRefreshRate()) {
      tradesRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("MtGox caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    tradesRequestTimeStamp = System.currentTimeMillis();

    // Request data
    MtGoxTrade[] mtGoxTrades = HttpUtils.getForJsonObject(apiBase + symbol + "/public/trades?raw", MtGoxTrade[].class, mapper, new HashMap<String, String>());

    Trades trades = new Trades();
    List<Trade> tradesList = new ArrayList<Trade>();
    for (int i = 0; i < mtGoxTrades.length; i++) {
      Trade trade = new Trade();
      trade.setDate(new Date(mtGoxTrades[i].getDate() * 1000L));
      trade.setAmount_int(mtGoxTrades[i].getAmount_int());
      trade.setPrice_int(mtGoxTrades[i].getPrice_int());
      trade.setPrice_currency(mtGoxTrades[i].getPrice_currency());
      trade.setTrade_type(mtGoxTrades[i].getTrade_type());
      tradesList.add(trade);
    }
    trades.setTrades(tradesList);
    return trades;
  }

  @Override
  public Collection<Ticker> getHistoricalMarketData(DateTime validFrom, DateTime validTo) {
    throw new NotAvailableFromExchangeException();
  }

  /**
   * <p>
   * According to Mt.Gox API docs (https://en.bitcoin.it/wiki/MtGox/API), data is cached for 10 seconds.
   * </p>
   */
  @Override
  public int getRefreshRate() {
    return MtGoxProperties.REFRESH_RATE;
  }

  @Override
  public List<String> getExchangeSymbols() {
    return MtGoxProperties.MT_GOX_SYMBOLS;
  }
}
