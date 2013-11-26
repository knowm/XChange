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
package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.kraken.Kraken;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.KrakenUtils;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPairsResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepthResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTickerResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTradesResult;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

public class KrakenPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private static final long PARTIAL_ORDERBOOK_SIZE = 200L;
  private final Kraken kraken;

  public KrakenPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    kraken = RestProxyFactory.createProxy(Kraken.class, exchangeSpecification.getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    KrakenAssetPairsResult krakenAssetPairs = null;
    try {
      krakenAssetPairs = kraken.getAssetPairs();
    } catch (IOException e) {
      throw new ExchangeException("Network error fetching exchange symbols!!!");
    }
    if (krakenAssetPairs.getError().length > 0) {
      throw new ExchangeException(krakenAssetPairs.getError().toString());
    }
    return KrakenAdapters.adaptCurrencyPairs(krakenAssetPairs.getResult().keySet());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) throws IOException {

    verify(tradableIdentifier, currency);

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenTickerResult krakenTickerResult = kraken.getTicker(krakenCurrencyPair);
    if (krakenTickerResult.getError().length > 0) {
      throw new ExchangeException(Arrays.toString(krakenTickerResult.getError()));
    }
    KrakenTicker krakenTicker = krakenTickerResult.getResult().get(krakenCurrencyPair);

    return KrakenAdapters.adaptTicker(krakenTicker, currency, tradableIdentifier);
  }

  /**
   * Get market depth from exchange
   *
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG). First currency of the pair
   * @param currency The currency of interest, null if irrelevant. Second currency of the pair
   * @param args Optional arguments. Exchange-specific. This implementation assumes:
   *             absent or OrderBookType.PARTIAL -> get partial OrderBook
   *             OrderBookType.FULL -> get full OrderBook
   *             Long positive value -> get corresponding number of items
   * @return
   * @throws IOException
   */
  @Override
  public OrderBook getOrderBook(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);

    Long numberOfItems = PARTIAL_ORDERBOOK_SIZE;
    if (args.length > 0) {
      if (args[0] instanceof OrderBookType) {
        if (args[0] == OrderBookType.FULL) {
          numberOfItems = null;
        }
      } else if (args[0] instanceof Long) {
        numberOfItems = (Long) args[0];
      } else {
        throw new IllegalArgumentException();
      }
    }

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenDepthResult krakenDepthReturn = kraken.getDepth(krakenCurrencyPair, numberOfItems);
    if (krakenDepthReturn.getError().length > 0) {
      throw new ExchangeException(Arrays.toString(krakenDepthReturn.getError()));
    }
    KrakenDepth krakenDepth = krakenDepthReturn.getResult().get(krakenCurrencyPair);
    List<LimitOrder> bids = KrakenAdapters.adaptOrders(krakenDepth.getBids(), currency, tradableIdentifier, "bids");
    List<LimitOrder> asks = KrakenAdapters.adaptOrders(krakenDepth.getAsks(), currency, tradableIdentifier, "asks");
    Comparator<LimitOrder> dateComparator = new Comparator<LimitOrder>() {

      @Override
      public int compare(LimitOrder o1, LimitOrder o2) {

        return o1.getTimestamp().compareTo(o2.getTimestamp());
      }
    };
    bids.addAll(asks);
    Date timeStamp = Collections.max(bids, dateComparator).getTimestamp();

    return new OrderBook(timeStamp, asks, bids);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency, Object... args) throws IOException {

    verify(tradableIdentifier, currency);

    String currencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenTradesResult krakenTrades = kraken.getTrades(currencyPair);
    if (krakenTrades.getError().length > 0) {
      throw new ExchangeException(krakenTrades.getError().toString());
    }
    Trades trades = KrakenAdapters.adaptTrades(krakenTrades.getResult().getTradesPerCurrencyPair(currencyPair), currency, tradableIdentifier, krakenTrades.getResult().getLast());
    return trades;
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
    Assert.isTrue(KrakenUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);
  }
}
