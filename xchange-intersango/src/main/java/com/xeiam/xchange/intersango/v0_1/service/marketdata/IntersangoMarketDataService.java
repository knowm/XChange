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
package com.xeiam.xchange.intersango.v0_1.service.marketdata;

import java.util.HashMap;
import java.util.List;

import org.joda.money.BigMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.intersango.v0_1.IntersangoProperties;
import com.xeiam.xchange.intersango.v0_1.service.marketdata.dto.IntersangoDepth;
import com.xeiam.xchange.intersango.v0_1.service.marketdata.dto.IntersangoTicker;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.marketdata.MarketDataService;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * <p>
 * Implementation of the market data service for Intersango
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class IntersangoMarketDataService extends BaseExchangeService implements MarketDataService {

  /**
   * Provides logging for this class
   */
  private final Logger log = LoggerFactory.getLogger(IntersangoMarketDataService.class);

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBase;

  /**
   * @param exchangeSpecification The exchange specification
   */
  public IntersangoMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    this.apiBase = String.format("%s/api/", exchangeSpecification.getUri());
  }

  @Override
  public Ticker getTicker(CurrencyPair symbolPair) {

    String currencyPairId = getCurrencyPairId(symbolPair);

    // Request data
    IntersangoTicker intersangoTicker = httpTemplate.getForJsonObject(apiBase + "ticker.php?currency_pair_id=" + currencyPairId, IntersangoTicker.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    long value_int = (long) (Double.parseDouble(intersangoTicker.getLast()));
    BigMoney last = MoneyUtils.fromSatoshi(value_int);
    BigMoney bid = MoneyUtils.fromSatoshi(value_int);
    BigMoney ask = MoneyUtils.fromSatoshi(value_int);
    long volume = (long) (Double.parseDouble(intersangoTicker.getVol()));

    Ticker ticker = new Ticker(last, bid, ask, symbolPair, volume);

    return ticker;
  }

  /**
   * <p>
   * Translates the symbol pair to the Intersango dictionary using the following rules:
   * </p>
   * <ul>
   * <li>1 = BTC:GBP</li>
   * <li>2 = BTC:EUR</li>
   * <li>3 = BTC:USD</li>
   * <li>4 = BTC:PLN</li>
   * </ul>
   * 
   * @param symbolPair The symbol pair
   * @return A suitable ID if possible
   */
  String getCurrencyPairId(CurrencyPair symbolPair) {

    if (!"BTC".equalsIgnoreCase(symbolPair.baseSymbol)) {
      throw new NotAvailableFromExchangeException("Symbol " + symbolPair.baseSymbol + " is not available");
    }
    if ("GBP".equalsIgnoreCase(symbolPair.counterSymbol)) {
      return "1";
    }
    if ("EUR".equalsIgnoreCase(symbolPair.counterSymbol)) {
      return "2";
    }
    if ("USD".equalsIgnoreCase(symbolPair.counterSymbol)) {
      return "3";
    }
    if ("PLN".equalsIgnoreCase(symbolPair.counterSymbol)) {
      return "4";
    }
    throw new NotAvailableFromExchangeException("Symbol pair " + symbolPair + " is not available");
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair symbolPair) {

    // Request data
    IntersangoDepth intersangoDepth = httpTemplate.getForJsonObject(apiBase + symbolPair.baseSymbol + symbolPair.counterSymbol + "/public/depth?raw", IntersangoDepth.class, mapper, new HashMap<String, String>());

    return null;
  }

  @Override
  public Trades getTrades(CurrencyPair symbolPair) {
    return null;
  }

  @Override
  public OrderBook getFullOrderBook(CurrencyPair symbolPair) {
    return null;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {
    return IntersangoProperties.SYMBOL_PAIRS;
  }
}
