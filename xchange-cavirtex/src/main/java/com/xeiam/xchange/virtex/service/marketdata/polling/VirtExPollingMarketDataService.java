/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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

import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;
import com.xeiam.xchange.virtex.VirtEx;
import com.xeiam.xchange.virtex.VirtExAdapters;
import com.xeiam.xchange.virtex.VirtExUtils;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTrade;

/**
 * <p>
 * Implementation of the market data service for VirtEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VirtExPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private final VirtEx virtEx;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification
   */
  public VirtExPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.virtEx = RestProxyFactory.createProxy(VirtEx.class, exchangeSpecification.getUri());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    // Request data
    VirtExTicker virtExTicker = virtEx.getTicker(currency);

    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTicker(virtExTicker, currency, tradableIdentifier);
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    // Request data
    VirtExDepth virtExDepth = virtEx.getFullDepth(currency);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VirtExAdapters.adaptOrders(virtExDepth.getAsks(), currency, "ask", "");
    List<LimitOrder> bids = VirtExAdapters.adaptOrders(virtExDepth.getBids(), currency, "bid", "");

    return new OrderBook(asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    // Request data
    VirtExTrade[] virtExTrades = virtEx.getTrades(currency);

    // Adapt to XChange DTOs
    return VirtExAdapters.adaptTrades(virtExTrades, currency, tradableIdentifier);
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
  public List<CurrencyPair> getExchangeSymbols() {

    return VirtExUtils.CURRENCY_PAIRS;
  }

}
