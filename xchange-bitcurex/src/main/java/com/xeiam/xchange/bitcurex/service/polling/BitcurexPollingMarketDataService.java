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
package com.xeiam.xchange.bitcurex.service.polling;

import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitcurex.Bitcurex;
import com.xeiam.xchange.bitcurex.BitcurexAdapters;
import com.xeiam.xchange.bitcurex.BitcurexUtils;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the market data service for Bitcurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcurexPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private Bitcurex bitcurex;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitcurexPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, "https://" + currency + ".bitcurex.com");
    // Request data
    BitcurexTicker bitcurexTicker = bitcurex.getTicker(currency);

    // Adapt to XChange DTOs
    return BitcurexAdapters.adaptTicker(bitcurexTicker, currency, tradableIdentifier);
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, "https://" + currency + ".bitcurex.com");
    // Request data
    BitcurexDepth bitcurexDepth = bitcurex.getFullDepth(currency);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BitcurexAdapters.adaptOrders(bitcurexDepth.getAsks(), currency, "ask", "");
    List<LimitOrder> bids = BitcurexAdapters.adaptOrders(bitcurexDepth.getBids(), currency, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) {

    verify(tradableIdentifier, currency);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, "https://" + currency + ".bitcurex.com");
    // Request data
    BitcurexTrade[] bitcurexTrades = bitcurex.getTrades(currency);

    // Adapt to XChange DTOs
    return BitcurexAdapters.adaptTrades(bitcurexTrades, currency, tradableIdentifier);
  }

  /**
   * Verify
   * 
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(BitcurexUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return BitcurexUtils.CURRENCY_PAIRS;
  }

}
