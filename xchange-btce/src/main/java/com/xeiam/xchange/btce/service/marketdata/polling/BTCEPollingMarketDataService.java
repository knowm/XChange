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
package com.xeiam.xchange.btce.service.marketdata.polling;

import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btce.BTCEAdapters;
import com.xeiam.xchange.btce.BTCEUtils;
import com.xeiam.xchange.btce.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.dto.marketdata.BTCETicker;
import com.xeiam.xchange.btce.dto.marketdata.BTCETrade;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the market data service for BTCE
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BTCEPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBase = String.format("%s/api/2/", exchangeSpecification.getUri());

  /**
   * @param exchangeSpecification The exchange specification
   */
  public BTCEPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    String url = apiBase + tradableIdentifier + "_" + currency + "/ticker";

    // Request data
    BTCETicker BTCETicker = httpTemplate.getForJsonObject(url.toLowerCase(), BTCETicker.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    return BTCEAdapters.adaptTicker(BTCETicker);
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    String url = apiBase + tradableIdentifier + "_" + currency + "/depth";

    // Request data
    BTCEDepth BTCEDepth = httpTemplate.getForJsonObject(url.toLowerCase(), BTCEDepth.class, mapper, new HashMap<String, String>());

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCEAdapters.adaptOrders(BTCEDepth.getAsks(), currency, "ask", "");
    List<LimitOrder> bids = BTCEAdapters.adaptOrders(BTCEDepth.getBids(), currency, "bid", "");

    return new OrderBook(asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    String url = apiBase + tradableIdentifier + "_" + currency + "/trades";

    // Request data
    BTCETrade[] BTCETrades = httpTemplate.getForJsonObject(url.toLowerCase(), BTCETrade[].class, mapper, new HashMap<String, String>());

    return BTCEAdapters.adaptTrades(BTCETrades);
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
    Assert.isTrue(BTCEUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return BTCEUtils.CURRENCY_PAIRS;
  }

}
