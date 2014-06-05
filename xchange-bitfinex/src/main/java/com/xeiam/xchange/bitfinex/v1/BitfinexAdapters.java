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
package com.xeiam.xchange.bitfinex.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexLevel;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

public final class BitfinexAdapters {

  public static final Logger log = LoggerFactory.getLogger(BitfinexAdapters.class);

  private BitfinexAdapters() {

  }

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<String> bitfinexSymbol) {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    for (String symbol : bitfinexSymbol) {
      currencyPairs.add(adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }

  public static CurrencyPair adaptCurrencyPair(String bitfinexSymbol) {

    String tradableIdentifier = bitfinexSymbol.substring(0, 3).toUpperCase();
    String transactionCurrency = bitfinexSymbol.substring(3).toUpperCase();
    return new CurrencyPair(tradableIdentifier, transactionCurrency);
  }

  public static List<LimitOrder> adaptOrders(BitfinexLevel[] orders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);

    for (BitfinexLevel order : orders) {
      // Bid orderbook is reversed order. Insert at index 0 instead of appending
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(0, adaptOrder(order.getAmount(), order.getPrice(), currencyPair, orderType, id));
      }
      else {
        limitOrders.add(adaptOrder(order.getAmount(), order.getPrice(), currencyPair, orderType, id));
      }
    }

    return limitOrders;
  }

  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, String id) {

    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);
  }

  public static Trade adaptTrade(BitfinexTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = trade.getType().equals("buy") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getTimestamp() * 1000L); // Bitfinex uses Unix timestamps
    final String tradeId = String.valueOf(trade.getTradeId());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(BitfinexTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>(trades.length);
    for (BitfinexTrade trade : trades) {
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(BitfinexTicker bitfinexTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitfinexTicker.getLast_price();
    BigDecimal bid = bitfinexTicker.getBid();
    BigDecimal ask = bitfinexTicker.getAsk();
    BigDecimal high = bitfinexTicker.getHigh();
    BigDecimal low = bitfinexTicker.getLow();
    BigDecimal volume = bitfinexTicker.getVolume();

    Date timestamp = DateUtils.fromMillisUtc((long) (bitfinexTicker.getTimestamp() * 1000L));

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();
  }

  public static AccountInfo adaptAccountInfo(BitfinexBalancesResponse[] response) {

    List<Wallet> wallets = new ArrayList<Wallet>(response.length);

    for (BitfinexBalancesResponse balance : response) {
      wallets.add(new Wallet(balance.getCurrency().toUpperCase(), balance.getAmount(), balance.getType()));
    }

    return new AccountInfo(null, wallets);
  }

  public static OpenOrders adaptOrders(BitfinexOrderStatusResponse[] activeOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(activeOrders.length);

    for (BitfinexOrderStatusResponse order : activeOrders) {
      OrderType orderType = order.getSide().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = adaptCurrencyPair(order.getSymbol());
      limitOrders.add(new LimitOrder(orderType, order.getRemainingAmount(), currencyPair, String.valueOf(order.getId()), new Date((long) order.getTimestamp()), order.getPrice()));
    }

    return new OpenOrders(limitOrders);
  }

  public static Trades adaptTradeHistory(BitfinexTradeResponse[] trades, String symbol) {

    List<Trade> pastTrades = new ArrayList<Trade>(trades.length);
    CurrencyPair currencyPair = adaptCurrencyPair(symbol);

    for (BitfinexTradeResponse trade : trades) {
      OrderType orderType = trade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;

      String id = String.valueOf(trade.hashCode());

      pastTrades.add(new Trade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), new Date((long) (trade.getTimestamp() * 1000L)), id));
    }

    return new Trades(pastTrades, TradeSortType.SortByTimestamp);
  }
}
