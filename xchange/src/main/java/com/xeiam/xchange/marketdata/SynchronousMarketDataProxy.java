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
package com.xeiam.xchange.marketdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeProxy;
import com.xeiam.xchange.exceptions.NotConnectedException;
import com.xeiam.xchange.exceptions.SymbolNotAvailableException;
import com.xeiam.xchange.interfaces.CachedDataSession;

/**
 * An Exchange Proxy for getting exchange market data info synchronously
 */
public abstract class SynchronousMarketDataProxy extends ExchangeProxy {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(SynchronousMarketDataProxy.class);

  private Tick cachedTick = null;
  private long lastTickUpdateTime = 0L;

  /**
   * @param symbol
   * @return
   */
  public Tick getTick(String symbol) {

    Tick tick = null;

    // check if connected
    if (!isConnected) {
      throw new NotConnectedException("Not Connected to Exchange!");
    }

    // check symbol
    if (!getExchangeSymbols().contains(symbol)) {
      throw new SymbolNotAvailableException("Symbol: " + symbol + " not available from Exchange!");
    }

    if (this instanceof CachedDataSession) {
      // return cached value if faster than refresh rate
      int refreshRate = ((CachedDataSession) this).getRefreshRate();
      if (System.currentTimeMillis() > lastTickUpdateTime + refreshRate * 1000) {
        log.debug("CachedExchangeProxy, fetching new data");
        lastTickUpdateTime = System.currentTimeMillis();
        tick = getExchangeTick(symbol);
      } else {
        log.debug("CachedExchangeProxy, returning cached result");
        tick = cachedTick;
      }
    } else {
      tick = getExchangeTick(symbol);
    }

    cachedTick = tick;
    return tick;
  }

  public int getLast(String symbol) {
    return getTick(symbol).getLast();
  }

  public long getVolume(String symbol) {
    return getTick(symbol).getVolume();
  }

  public Depth getDepth() {
    // TODO do it.
    return null;
  }

  public Trades getTrades() {
    // TODO do it.
    return null;
  }

  public FullDepth getFullDepth() {
    // TODO do it.
    return null;
  }

  public CancelledTrades getCancelledTrades() {
    // TODO do it.
    return null;
  }

  public abstract Tick getExchangeTick(String symbol);

  public abstract Tick getExchangeDepth(String symbol);

  public abstract Tick getExchangeTrades(String symbol);

  public abstract Tick getExchangeFullDepth(String symbol);

  public abstract Tick getExchangeCancelledTrades(String symbol);

}
