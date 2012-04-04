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
package com.xeiam.xchange.imcex.v1.service.marketdata;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.SymbolPair;
import com.xeiam.xchange.imcex.v1.ImcexProperties;
import com.xeiam.xchange.imcex.v1.service.marketdata.dto.ImcexDepth;
import com.xeiam.xchange.imcex.v1.service.marketdata.dto.ImcexTicker;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.marketdata.MarketDataService;
import com.xeiam.xchange.service.marketdata.OrderBook;
import com.xeiam.xchange.service.marketdata.Ticker;
import com.xeiam.xchange.service.marketdata.Trades;
import com.xeiam.xchange.utils.MoneyUtils;
import org.joda.money.BigMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Implementation of the market data service for Imcex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class ImcexPublicHttpMarketDataService extends BaseExchangeService implements MarketDataService, CachedDataSession {

  /**
   * Provides logging for this class
   */
  private final Logger log = LoggerFactory.getLogger(ImcexPublicHttpMarketDataService.class);

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBase;

  /**
   * @param exchangeSpecification The exchange specification
   */
  public ImcexPublicHttpMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    this.apiBase = String.format("%s/api/%s/", exchangeSpecification.getUri(), exchangeSpecification.getVersion());
  }

  @Override
  public Ticker getTicker(SymbolPair symbolPair) {

    // Request data
    ImcexTicker imcexTicker = httpTemplate.getForJsonObject(apiBase + symbolPair.baseSymbol + symbolPair.counterSymbol + "/public/ticker", ImcexTicker.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    // TODO This assumes BTC and Satoshis and needs correction
    long satoshis = Long.parseLong(imcexTicker.getReturn().getLast_orig().getValue_int());
    BigMoney last = MoneyUtils.fromSatoshi(satoshis);
    long volume = Long.parseLong(imcexTicker.getReturn().getVol().getValue_int());
    Ticker ticker = new Ticker(last, symbolPair, volume);

    return ticker;
  }

  @Override
  public OrderBook getOrderBook(SymbolPair symbolPair) {

    // Request data
    ImcexDepth imcexDepth = httpTemplate.getForJsonObject(apiBase + symbolPair.baseSymbol + symbolPair.counterSymbol + "/public/depth?raw", ImcexDepth.class, mapper, new HashMap<String, String>());

    return null;
  }

  @Override
  public Trades getTrades(SymbolPair symbolPair) {
    return null;
  }

  @Override
  public OrderBook getFullOrderBook(SymbolPair symbolPair) {
    return null;
  }

  @Override
  public int getRefreshRate() {
    return ImcexProperties.REFRESH_RATE;
  }

  @Override
  public List<SymbolPair> getExchangeSymbols() {
    return ImcexProperties.SYMBOL_PAIRS;
  }

}
