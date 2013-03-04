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
package com.xeiam.xchange.bitcoin24.service.marketdata.polling;

import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitcoin24.Bitcoin24;
import com.xeiam.xchange.bitcoin24.Bitcoin24Adapters;
import com.xeiam.xchange.bitcoin24.Bitcoin24Utils;
import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Depth;
import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Ticker;
import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Trade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the market data service for Bitcoin24
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class Bitcoin24PollingMarketDataService implements PollingMarketDataService {

  private final Bitcoin24 bitcoin24;

  /**
   * @param exchangeSpecification The exchange specification
   */
  public Bitcoin24PollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    bitcoin24 = RestProxyFactory.createProxy(Bitcoin24.class, exchangeSpecification.getUri());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    Bitcoin24Ticker bitcoin24Ticker = bitcoin24.getTicker(currency.toUpperCase());

    // Adapt to XChange DTOs
    return Bitcoin24Adapters.adaptTicker(bitcoin24Ticker, tradableIdentifier, currency);
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    Bitcoin24Depth btceDepth = bitcoin24.getFullDepth(currency.toUpperCase());

    // Adapt to XChange DTOs
    List<LimitOrder> asks = Bitcoin24Adapters.adaptOrders(btceDepth.getAsks(), tradableIdentifier, currency, "ask", "");
    List<LimitOrder> bids = Bitcoin24Adapters.adaptOrders(btceDepth.getBids(), tradableIdentifier, currency, "bid", "");

    return new OrderBook(asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    Bitcoin24Trade[] BTCETrades = bitcoin24.getTrades(currency.toUpperCase());

    return Bitcoin24Adapters.adaptTrades(BTCETrades, tradableIdentifier, currency);
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
    Assert.isTrue(Bitcoin24Utils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return Bitcoin24Utils.CURRENCY_PAIRS;
  }

}
