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
package com.xeiam.xchange.bter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.bter.dto.account.BTERFunds;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERPublicOrder;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory.BTERPublicTrade;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrder;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrders;
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
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bter DTOs to XChange DTOs
 */
public final class BTERAdapters {

  /**
   * private Constructor
   */
  private BTERAdapters() {

  }

  public static CurrencyPair adaptCurrencyPair(String pair) {

    final String[] currencies = pair.toUpperCase().split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, BTERTicker bterTicker) {

    BigDecimal ask = bterTicker.getSell();
    BigDecimal bid = bterTicker.getBuy();
    BigDecimal last = bterTicker.getLast();
    BigDecimal low = bterTicker.getLow();
    BigDecimal high = bterTicker.getHigh();
    BigDecimal volume = bterTicker.getTradeCurrencyVolume();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withAsk(ask).withBid(bid).withLast(last).withLow(low).withHigh(high).withVolume(volume).build();
  }

  public static LimitOrder adaptOrder(BTERPublicOrder order, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public static List<LimitOrder> adaptOrders(List<BTERPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BTERPublicOrder bterOrder : orders) {
      limitOrders.add(adaptOrder(bterOrder, currencyPair, orderType));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(BTERDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = BTERAdapters.adaptOrders(depth.getAsks(), currencyPair, OrderType.ASK);
    List<LimitOrder> bids = BTERAdapters.adaptOrders(depth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static LimitOrder adaptOrder(BTEROpenOrder order, Collection<CurrencyPair> currencyPairs) {

    CurrencyPair possibleCurrencyPair = new CurrencyPair(order.getBuyCurrency(), order.getSellCurrency());
    if (!currencyPairs.contains(possibleCurrencyPair)) {
      BigDecimal price = order.getBuyAmount().divide(order.getSellAmount(), 8, RoundingMode.HALF_EVEN);
      return new LimitOrder(OrderType.ASK, order.getSellAmount(), new CurrencyPair(order.getSellCurrency(), order.getBuyCurrency()), order.getId(), null, price);
    }
    else {
      BigDecimal price = order.getSellAmount().divide(order.getBuyAmount(), 8, RoundingMode.HALF_EVEN);
      return new LimitOrder(OrderType.BID, order.getBuyAmount(), possibleCurrencyPair, order.getId(), null, price);
    }
  }

  public static OpenOrders adaptOpenOrders(BTEROpenOrders openOrders, Collection<CurrencyPair> currencyPairs) {

    List<LimitOrder> adaptedOrders = new ArrayList<LimitOrder>();
    for (BTEROpenOrder openOrder : openOrders.getOrders()) {
      adaptedOrders.add(adaptOrder(openOrder, currencyPairs));
    }

    return new OpenOrders(adaptedOrders);
  }

  public static OrderType adaptOrderType(BTEROrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(BTEROrderType.BUY)) ? OrderType.BID : OrderType.ASK;
  }

  public static Trade adaptTrade(BTERPublicTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = DateUtils.fromMillisUtc(trade.getDate() * 1000);

    return new Trade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, trade.getTradeId(), null);
  }

  public static Trades adaptTrades(BTERTradeHistory tradeHistory, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>();
    for (BTERPublicTrade trade : tradeHistory.getTrades()) {
      Trade adaptedTrade = adaptTrade(trade, currencyPair);
      tradeList.add(adaptedTrade);
    }

    return new Trades(tradeList, TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptAccountInfo(BTERFunds bterAccountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    for (Entry<String, BigDecimal> funds : bterAccountInfo.getAvailableFunds().entrySet()) {
      String currency = funds.getKey().toUpperCase();
      BigDecimal amount = funds.getValue();
      wallets.add(new Wallet(currency, amount));
    }

    return new AccountInfo("", wallets);
  }

}
