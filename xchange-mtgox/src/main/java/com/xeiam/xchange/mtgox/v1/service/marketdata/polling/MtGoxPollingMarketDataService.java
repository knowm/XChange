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
package com.xeiam.xchange.mtgox.v1.service.marketdata.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.MtGoxV1;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxDepth;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTrade;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the market data service for Mt Gox V1
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 * <p>
 * 
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
public class MtGoxPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private final MtGoxV1 mtGoxV1;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public MtGoxPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.mtGoxV1 = RestProxyFactory.createProxy(MtGoxV1.class, exchangeSpecification.getSslUri());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    // Request data
    MtGoxTicker mtGoxTicker = mtGoxV1.getTicker(tradableIdentifier, currency);

    // Adapt to XChange DTOs
    return MtGoxAdapters.adaptTicker(mtGoxTicker);
  }

  /**
   * Get market depth from exchange
   *
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG). First currency of the pair
   * @param currency The currency of interest, null if irrelevant. Second currency of the pair
   * @param args Optional arguments. Exchange-specific. This implementation assumes:
   *             absent or OrderBookType.PARTIAL -> get partial OrderBook
   *             OrderBookType.FULL -> get full OrderBook
   * @return The OrderBook
   * @throws IOException
   */
  @Override
  public OrderBook getOrderBook(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);

    // Request data
    MtGoxDepth mtgoxDepth = null;
    if (args.length > 0) {
      if (args[0] instanceof OrderBookType) {
        if (args[0] == OrderBookType.FULL) {
          mtgoxDepth = mtGoxV1.getFullDepth(tradableIdentifier, currency);
        } else {
          mtgoxDepth = mtGoxV1.getDepth(tradableIdentifier, currency);
        }
      } else {
        throw new IllegalArgumentException();
      }
    } else {
      mtgoxDepth = mtGoxV1.getDepth(tradableIdentifier, currency);
    }

    // Adapt to XChange DTOs
    List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtgoxDepth.getAsks(), currency, "ask", "");
    List<LimitOrder> bids = MtGoxAdapters.adaptOrders(mtgoxDepth.getBids(), currency, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) {

    verify(tradableIdentifier, currency);
    MtGoxTrade[] mtGoxTrades = null;
    if (args.length > 0) {
      Long sinceTimeStamp = (Long) args[0];
      // Request data
      mtGoxTrades = mtGoxV1.getTrades(tradableIdentifier, currency, "y", sinceTimeStamp);
    }
    else {
      // Request data
      mtGoxTrades = mtGoxV1.getTrades(tradableIdentifier, currency);
    }

    return MtGoxAdapters.adaptTrades(mtGoxTrades);
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Verify
   * 
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param currency The transaction currency (e.g. USD in BTC/USD)
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(MtGoxUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return MtGoxUtils.CURRENCY_PAIRS;
  }
}
