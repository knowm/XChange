/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.poloniex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexDepth;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexLevel;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexTicker;

/**
 * @author Zach Holmes
 */

public class PoloniexAdapters {

  public static Ticker adaptPoloniexTicker(PoloniexTicker poloniexTicker, CurrencyPair currencyPair) {

    PoloniexMarketData marketData = poloniexTicker.getPoloniexMarketData();

    BigDecimal last = marketData.getLast();
    BigDecimal bid = marketData.getHighestBid();
    BigDecimal ask = marketData.getLowestAsk();
    BigDecimal high = null;
    BigDecimal low = null;
    BigDecimal volume = marketData.getBaseVolume();

    Date timestamp = new Date();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();

  }

  public static OrderBook adaptPoloniexDepth(PoloniexDepth depth, CurrencyPair currencyPair) throws IOException {

    List<LimitOrder> asks = adaptPoloniexPublicOrders(depth.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptPoloniexPublicOrders(depth.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(new Date(), asks, bids);
  }

  public static List<LimitOrder> adaptPoloniexPublicOrders(List<List<BigDecimal>> rawLevels, OrderType orderType, CurrencyPair currencyPair) throws IOException {

    List<PoloniexLevel> levels = new ArrayList<PoloniexLevel>();

    for (List<BigDecimal> rawLevel : rawLevels) {
      levels.add(adaptRawPoloniexLevel(rawLevel));
    }

    List<LimitOrder> orders = new ArrayList<LimitOrder>();

    for (PoloniexLevel level : levels) {

      LimitOrder limitOrder = new LimitOrder.Builder(orderType, currencyPair).setTradableAmount(level.getAmount()).setLimitPrice(level.getLimit()).build();
      orders.add(limitOrder);
    }
    return orders;
  }

  public static PoloniexLevel adaptRawPoloniexLevel(List<BigDecimal> level) {

    PoloniexLevel poloniexLevel = new PoloniexLevel(level.get(1), level.get(0));
    return poloniexLevel;
  }

  public static Trades adaptPoloniexPublicTrades(PoloniexPublicTrade[] poloniexPublicTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();

    for (PoloniexPublicTrade poloniexTrade : poloniexPublicTrades) {
      trades.add(adaptPoloniexPublicTrade(poloniexTrade, currencyPair));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptPoloniexPublicTrade(PoloniexPublicTrade poloniexTrade, CurrencyPair currencyPair) {

    OrderType type = poloniexTrade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
    Date timestamp = PoloniexUtils.stringToDate(poloniexTrade.getDate());

    Trade trade = new Trade(type, poloniexTrade.getAmount(), currencyPair, poloniexTrade.getRate(), timestamp, poloniexTrade.getTradeID(), poloniexTrade.getTradeID());
    return trade;
  }

  public static List<Wallet> adaptPoloniexBalances(HashMap<String, BigDecimal> poloniexBalances) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    for (String currency : poloniexBalances.keySet()) {
      wallets.add(new Wallet(currency, poloniexBalances.get(currency)));
    }

    return wallets;
  }
}
