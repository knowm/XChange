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

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.PacingViolationException;
import com.xeiam.xchange.SymbolPair;
import com.xeiam.xchange.mtgox.v1.MtGoxProperties;
import com.xeiam.xchange.mtgox.v1.service.marketdata.dto.MtGoxDepth;
import com.xeiam.xchange.mtgox.v1.service.marketdata.dto.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.service.marketdata.dto.MtGoxTrade;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.marketdata.*;
import com.xeiam.xchange.utils.Assert;
import com.xeiam.xchange.utils.MoneyUtils;
import org.joda.money.BigMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Implementation of the market data service for Mt Gox
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class MtGoxMarketDataService extends BaseExchangeService implements MarketDataService, CachedDataSession {

  private final Logger log = LoggerFactory.getLogger(MtGoxMarketDataService.class);

  /**
   * time stamps used to pace API calls
   * TODO Consider a scheduled ExecutorService with Callable?
   */
  private long tickerRequestTimeStamp = 0L;
  private long orderBookRequestTimeStamp = 0L;
  private long fullOrderBookRequestTimeStamp = 0L;
  private long tradesRequestTimeStamp = 0L;

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBase = String.format("%s/api/%s/", exchangeSpecification.getUri(), exchangeSpecification.getVersion());

  /**
   * @param exchangeSpecification The exchange specification
   */
  public MtGoxMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(SymbolPair symbolPair) {

    // verify
    Assert.notNull(symbolPair, "symbolPair cannot be null");

    // check for pacing violation
    if (System.currentTimeMillis() < tickerRequestTimeStamp + getRefreshRate()) {
      tickerRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("MtGox caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    tickerRequestTimeStamp = System.currentTimeMillis();

    // Request data
    MtGoxTicker mtGoxTicker = httpTemplate.getForJsonObject(apiBase + symbolPair.baseSymbol + symbolPair.counterSymbol + "/public/ticker?raw", MtGoxTicker.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    BigMoney money = MoneyUtils.fromSatoshi(mtGoxTicker.getLast_orig().getValue_int());
    long volume = mtGoxTicker.getVol().getValue_int();
    Ticker ticker = new Ticker(money, symbolPair, volume);

    return ticker;
  }

  @Override
  public OrderBook getOrderBook(SymbolPair symbolPair) {

    // Verify
    Assert.notNull(symbolPair, "symbolPair cannot be null");

    // Check for pacing violation
    if (System.currentTimeMillis() < orderBookRequestTimeStamp + getRefreshRate()) {
      orderBookRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("MtGox caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    orderBookRequestTimeStamp = System.currentTimeMillis();

    // Request data
    MtGoxDepth mtgoxDepth = httpTemplate.getForJsonObject(apiBase + symbolPair.baseSymbol + symbolPair.counterSymbol + "/public/depth?raw", MtGoxDepth.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    // TODO Require an adapter here
    List<Order> asks = new ArrayList(mtgoxDepth.getAsks());
    List<Order> bids = new ArrayList(mtgoxDepth.getBids());
    OrderBook depth = new OrderBook(asks, bids);

    return depth;
  }

  @Override
  public OrderBook getFullOrderBook(SymbolPair symbolPair) {

    // Verify
    Assert.notNull(symbolPair, "symbolPair cannot be null");

    // check for pacing violation
    if (System.currentTimeMillis() < fullOrderBookRequestTimeStamp + getRefreshRate()) {
      fullOrderBookRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("MtGox caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    fullOrderBookRequestTimeStamp = System.currentTimeMillis();

    // Request data
    MtGoxDepth mtgoxFullDepth = httpTemplate.getForJsonObject(apiBase + symbolPair.baseSymbol + symbolPair.counterSymbol + "/public/fulldepth?raw", MtGoxDepth.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    // TODO Require an adapter here
    List<Order> asks = new ArrayList(mtgoxFullDepth.getAsks());
    List<Order> bids = new ArrayList(mtgoxFullDepth.getBids());
    OrderBook depth = new OrderBook(asks, bids);

    return depth;
  }

  @Override
  public Trades getTrades(SymbolPair symbolPair) {

    // Verify
    Assert.notNull(symbolPair, "symbol cannot be null");

    // Check for pacing violation
    if (System.currentTimeMillis() < tradesRequestTimeStamp + getRefreshRate()) {
      tradesRequestTimeStamp = System.currentTimeMillis();
      throw new PacingViolationException("MtGox caches market data and refreshes every " + getRefreshRate() + " seconds.");
    }
    tradesRequestTimeStamp = System.currentTimeMillis();

    // Request data
    MtGoxTrade[] mtGoxTrades = httpTemplate.getForJsonObject(apiBase + symbolPair.baseSymbol + symbolPair.counterSymbol + "/public/trades?raw", MtGoxTrade[].class, mapper, new HashMap<String, String>());

    List<Trade> tradesList = new ArrayList<Trade>();
    for (int i = 0; i < mtGoxTrades.length; i++) {
      Date date = new Date(mtGoxTrades[i].getDate() * 1000L);
      long amount_int = mtGoxTrades[i].getAmount_int();
      long price_int = mtGoxTrades[i].getPrice_int();
      // TODO use a symbol service, and throw exception? when no symbol is found?
      String price_currency = (mtGoxTrades[i].getPrice_currency());
      String trade_type = mtGoxTrades[i].getTrade_type();
      Trade trade = new Trade(date, amount_int, price_int, price_currency, trade_type);
      tradesList.add(trade);
    }
    return new Trades(tradesList);
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
  public List<SymbolPair> getExchangeSymbols() {
    return MtGoxProperties.SYMBOL_PAIRS;
  }
}
