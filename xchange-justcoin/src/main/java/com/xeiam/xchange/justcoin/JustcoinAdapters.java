/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.justcoin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinOrder;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinTrade;

/**
 * jamespedwards42
 */
public final class JustcoinAdapters {

  private JustcoinAdapters() {

  }

  public static List<LimitOrder> adaptOrders(final List<List<BigDecimal>> justcoinOrders, final CurrencyPair currencyPair, final OrderType orderType) {

    final List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (final List<BigDecimal> justcoinOrder : justcoinOrders) {
      limitOrders.add(adaptOrder(justcoinOrder.get(1), justcoinOrder.get(0), currencyPair, orderType));
    }

    return limitOrders;
  }

  public static LimitOrder adaptOrder(final BigDecimal amount, final BigDecimal price, final CurrencyPair currencyPair, final OrderType orderType) {

    return new LimitOrder(orderType, amount, currencyPair, null, null, price);
  }

  public static Ticker adaptTicker(final JustcoinTicker[] justcoinTickers, final CurrencyPair currencyPair) {

    for (final JustcoinTicker justcointTicker : justcoinTickers) {
      if (justcointTicker.getId().equals(JustcoinUtils.getApiMarket(currencyPair.baseSymbol, currencyPair.counterSymbol))) {
        return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(justcointTicker.getLast()).withBid(justcointTicker.getBid()).withAsk(justcointTicker.getAsk()).withHigh(
            justcointTicker.getHigh()).withLow(justcointTicker.getLow()).withVolume(justcointTicker.getVolume()).build();
      }
    }

    return null;
  }

  public static AccountInfo adaptAccountInfo(final String username, final JustcoinBalance[] justcoinBalances) {

    final List<Wallet> wallets = new ArrayList<Wallet>();
    for (final JustcoinBalance balanceForCurrency : justcoinBalances) {
      wallets.add(adaptWallet(balanceForCurrency));
    }

    return new AccountInfo(username, wallets);
  }

  public static Wallet adaptWallet(final JustcoinBalance justcoinBalance) {

    final String currency = justcoinBalance.getCurrency();
    final BigDecimal balance = justcoinBalance.getBalance();
    return new Wallet(currency, balance);
  }

  public static OpenOrders adaptOpenOrders(final JustcoinOrder[] justoinOrders) {

    final List<LimitOrder> openOrders = new ArrayList<LimitOrder>();
    for (final JustcoinOrder justcoinOrder : justoinOrders) {
      openOrders.add(adaptLimitOrder(justcoinOrder));
    }

    return new OpenOrders(openOrders);
  }

  public static OrderBook adaptOrderBook(final CurrencyPair currencyPair, final JustcoinDepth justcoinDepth) {

    final List<LimitOrder> asks = JustcoinAdapters.adaptOrders(justcoinDepth.getAsks(), currencyPair, OrderType.ASK);
    final List<LimitOrder> bids = JustcoinAdapters.adaptOrders(justcoinDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static CurrencyPair adaptCurrencyPair(final String justcoinMarket) {

    return new CurrencyPair(justcoinMarket.substring(0, 3), justcoinMarket.substring(3));
  }

  public static LimitOrder adaptLimitOrder(final JustcoinOrder justcoinOrder) {

    return new LimitOrder(OrderType.valueOf(justcoinOrder.getType().toUpperCase()), justcoinOrder.getAmount(), adaptCurrencyPair(justcoinOrder.getMarket()), justcoinOrder.getId(), justcoinOrder
        .getCreatedAt(), justcoinOrder.getPrice());
  }

  public static Trades adaptTrades(final JustcoinTrade[] justoinOrders) {

    final List<Trade> openOrders = new ArrayList<Trade>();
    for (final JustcoinTrade justcoinOrder : justoinOrders) {
      openOrders.add(adaptTrade(justcoinOrder));
    }

    return new Trades(openOrders, TradeSortType.SortByID);
  }

  public static Trade adaptTrade(final JustcoinTrade justcoinTrade) {

    return new Trade(OrderType.valueOf(justcoinTrade.getType().toUpperCase()), justcoinTrade.getAmount(), adaptCurrencyPair(justcoinTrade.getMarket()), justcoinTrade.getAveragePrice(), justcoinTrade
        .getCreatedAt(), justcoinTrade.getId());
  }
}
