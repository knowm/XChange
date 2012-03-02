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
package com.xeiam.xchange.intersango.v1.service.marketdata;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.intersango.v1.IntersangoProperties;
import com.xeiam.xchange.intersango.v1.service.marketdata.dto.IntersangoDepth;
import com.xeiam.xchange.intersango.v1.service.marketdata.dto.IntersangoTicker;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.marketdata.MarketDataService;
import com.xeiam.xchange.service.marketdata.OrderBook;
import com.xeiam.xchange.service.marketdata.Ticker;
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
public class IntersangoPublicHttpMarketDataService extends BaseExchangeService implements MarketDataService, CachedDataSession {

  /**
   * Provides logging for this class
   */
  private final Logger log = LoggerFactory.getLogger(IntersangoPublicHttpMarketDataService.class);

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBase = String.format("%s/api/%s/", apiURI, apiVersion);

  /**
   * @param exchangeSpecification The exchange specification
   */
  public IntersangoPublicHttpMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(String symbol) {

    // Request data
    IntersangoTicker intersangoTicker = HttpUtils.getForJsonObject(apiBase + symbol + "/public/ticker", IntersangoTicker.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    Ticker ticker = new Ticker();

    // TODO Provide more detail
    ticker.setLast(Integer.parseInt(intersangoTicker.getReturn().getLast_orig().getValue_int()));
    ticker.setVolume(Long.parseLong(intersangoTicker.getReturn().getVol().getValue_int()));

    return ticker;
  }

  @Override
  public OrderBook getOrderBook(String symbol) {

    // Request data
    IntersangoDepth intersangoDepth = HttpUtils.getForJsonObject(apiBase + symbol + "/public/depth?raw", IntersangoDepth.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    OrderBook depth = new OrderBook();

    return depth;
  }

  @Override
  public Trades getTrades(String symbol) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OrderBook getFullOrderBook(String symbol) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<Ticker> getHistoricalMarketData(DateTime validFrom, DateTime validTo) {
    return null;
  }

  /**
   * <p>
   * According to Mt.Gox API docs (https://en.bitcoin.it/wiki/MtGox/API), data is cached for 10 seconds.
   * </p>
   */
  @Override
  public int getRefreshRate() {
    return IntersangoProperties.REFRESH_RATE;
  }

  @Override
  public List<String> getExchangeSymbols() {
    return IntersangoProperties.MT_GOX_SYMBOLS;
  }
}
