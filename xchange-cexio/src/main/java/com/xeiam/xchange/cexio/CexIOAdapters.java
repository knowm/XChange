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
package com.xeiam.xchange.cexio;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.cexio.dto.account.CexIOBalanceInfo;
import com.xeiam.xchange.cexio.dto.marketdata.CexIODepth;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTicker;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTrade;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class CexIOAdapters {

  /**
   * Adapts a CexIOTrade to a Trade Object
   * 
   * @param trade CexIO trade object
   * @param tradableIdentifier First currency in the pair
   * @param currency Second currency in the pair
   * @return The XChange Trade
   */
  public static Trade adaptTrade(CexIOTrade trade, CurrencyPair currencyPair) {

    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getDate() * 1000L);
    // Cex.IO API does not return trade type
    return new Trade(null, amount, currencyPair, price, date, String.valueOf(trade.getTid()));
  }

  /**
   * Adapts a CexIOTrade[] to a Trades Object
   * 
   * @param cexioTrades The CexIO trade data returned by API
   * @param tradableIdentifier First currency of the pair
   * @param currency Second currency of the pair
   * @return The trades
   */
  public static Trades adaptTrades(CexIOTrade[] cexioTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (CexIOTrade trade : cexioTrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(0, adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  /**
   * Adapts a CexIOTicker to a Ticker Object
   * 
   * @param ticker The exchange specific ticker
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(CexIOTicker ticker, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getLast();
    BigDecimal bid = ticker.getBid();
    BigDecimal ask = ticker.getAsk();
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal volume = ticker.getVolume();
    Date timestamp = new Date(ticker.getTimestamp() * 1000L);

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();
  }

  /**
   * Adapts Cex.IO Depth to OrderBook Object
   * 
   * @param depth Cex.IO order book
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(CexIODepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, depth.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, depth.getBids());
    Date date = new Date(depth.getTimestamp() * 1000);
    return new OrderBook(date, asks, bids);
  }

  /**
   * Adapts CexIOBalanceInfo to AccountInfo
   * 
   * @param balance CexIOBalanceInfo balance
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(CexIOBalanceInfo balance, String userName) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    // Adapt to XChange DTOs
    if (balance.getBalanceBTC() != null) {
      wallets.add(new Wallet(Currencies.BTC, balance.getBalanceBTC().getAvailable(), "available"));
      wallets.add(new Wallet(Currencies.BTC, balance.getBalanceBTC().getOrders(), "orders"));
    }
    if (balance.getBalanceLTC() != null) {
      wallets.add(new Wallet(Currencies.LTC, balance.getBalanceLTC().getAvailable(), "available"));
      wallets.add(new Wallet(Currencies.LTC, balance.getBalanceLTC().getOrders(), "orders"));
    }
    if (balance.getBalanceNMC() != null) {
      wallets.add(new Wallet(Currencies.NMC, balance.getBalanceNMC().getAvailable(), "available"));
      wallets.add(new Wallet(Currencies.NMC, balance.getBalanceNMC().getOrders(), "orders"));
    }
    if (balance.getBalanceIXC() != null) {
      wallets.add(new Wallet(Currencies.IXC, balance.getBalanceIXC().getAvailable(), "available"));
    }
    if (balance.getBalanceDVC() != null) {
      wallets.add(new Wallet(Currencies.DVC, balance.getBalanceDVC().getAvailable(), "available"));
    }
    if (balance.getBalanceGHS() != null) {
      wallets.add(new Wallet(Currencies.GHs, balance.getBalanceGHS().getAvailable(), "available"));
      wallets.add(new Wallet(Currencies.GHs, balance.getBalanceGHS().getOrders(), "orders"));
    }
    return new AccountInfo(userName, null, wallets);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> o : orders) {
      checkArgument(o.size() == 2, "Expected a pair (price, amount) but got {0} elements.", o.size());
      limitOrders.add(createOrder(currencyPair, o, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static OpenOrders adaptOpenOrders(List<CexIOOrder> cexIOOrderList) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (CexIOOrder cexIOOrder : cexIOOrderList) {
      Order.OrderType orderType = cexIOOrder.getType() == CexIOOrder.Type.buy ? Order.OrderType.BID : Order.OrderType.ASK;
      String id = Integer.toString(cexIOOrder.getId());
      limitOrders.add(new LimitOrder(orderType, cexIOOrder.getPending(), new CurrencyPair(cexIOOrder.getTradableIdentifier(), cexIOOrder.getTransactionCurrency()), id, DateUtils
          .fromMillisUtc(cexIOOrder.getTime()), cexIOOrder.getPrice()));
    }

    return new OpenOrders(limitOrders);

  }

}
