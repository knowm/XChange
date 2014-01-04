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
package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitfinex.v1.Bitfinex;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bitfinex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitfinexPollingMarketDataService implements PollingMarketDataService {

  protected final Bitfinex bitfinex;
  private final List<CurrencyPair> supportedCurrencies = new ArrayList<CurrencyPair>();
  {
    supportedCurrencies.add(CurrencyPair.BTC_USD);
    supportedCurrencies.add(CurrencyPair.LTC_USD);
    supportedCurrencies.add(CurrencyPair.LTC_BTC);
  }

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitfinexPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    bitfinex = RestProxyFactory.createProxy(Bitfinex.class, exchangeSpecification.getSslUri());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency, Object... args) throws IOException {

    BitfinexTicker btceTicker = bitfinex.getTicker(toPairString(tradableIdentifier, currency));

    return BitfinexAdapters.adaptTicker(btceTicker, tradableIdentifier, currency);
  }

  /**
   * @param args If two integers are provided, then those count as limit bid and limit ask count
   */
  @Override
  public OrderBook getOrderBook(String tradableIdentifier, String currency, Object... args) throws IOException {

    // According to API docs, default is 50
    int limit_bids = 50;
    int limit_asks = 50;

    if (args.length == 2) {
      limit_bids = (Integer) args[0];
      limit_asks = (Integer) args[1];
    }

    BitfinexDepth btceDepth = bitfinex.getBook(toPairString(tradableIdentifier, currency), limit_bids, limit_asks);

    List<LimitOrder> asks = BitfinexAdapters.adaptOrders(btceDepth.getAsks(), tradableIdentifier, currency, "ask", "");
    List<LimitOrder> bids = BitfinexAdapters.adaptOrders(btceDepth.getBids(), tradableIdentifier, currency, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) throws IOException {

    BitfinexTrade[] trades = bitfinex.getTrades(toPairString(tradableIdentifier, currency));

    return BitfinexAdapters.adaptTrades(trades, tradableIdentifier, currency);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return supportedCurrencies;
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  private static String toPairString(String tradableIdentifier, String currency) {

    return tradableIdentifier.toLowerCase() + currency.toLowerCase();
  }
}
