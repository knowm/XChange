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
package com.xeiam.xchange.kraken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.xeiam.xchange.currency.Currencies;
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
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicOrder;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrade;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderDescription;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResponse;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;

public class KrakenAdapters {

  public static OrderBook adaptOrderBook(KrakenDepth krakenDepth, CurrencyPair currencyPair) {

    List<LimitOrder> bids = KrakenAdapters.adaptOrders(krakenDepth.getBids(), currencyPair, OrderType.BID);
    List<LimitOrder> asks = KrakenAdapters.adaptOrders(krakenDepth.getAsks(), currencyPair, OrderType.ASK);
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> adaptOrders(List<KrakenPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.size());
    for (KrakenPublicOrder order : orders) {
      limitOrders.add(adaptOrder(order, orderType, currencyPair));
    }

    return limitOrders;
  }

  public static LimitOrder adaptOrder(KrakenPublicOrder order, OrderType orderType, CurrencyPair currencyPair) {

    Date timeStamp = new Date(order.getTimestamp() * 1000);
    BigDecimal volume = order.getVolume();

    return new LimitOrder(orderType, volume, currencyPair, "", timeStamp, order.getPrice());
  }

  public static Ticker adaptTicker(KrakenTicker krakenTicker, CurrencyPair currencyPair) {

    TickerBuilder builder = new TickerBuilder();
    builder.withAsk(krakenTicker.getAsk().getPrice());
    builder.withBid(krakenTicker.getBid().getPrice());
    builder.withLast(krakenTicker.getClose().getPrice());
    builder.withHigh(krakenTicker.get24HourHigh());
    builder.withLow(krakenTicker.get24HourLow());
    builder.withVolume(krakenTicker.get24HourVolume());
    builder.withCurrencyPair(currencyPair);
    return builder.build();
  }

  public static Trades adaptTrades(List<KrakenPublicTrade> krakenTrades, CurrencyPair currencyPair, long last) {

    List<Trade> trades = new ArrayList<Trade>();
    for (KrakenPublicTrade krakenTrade : krakenTrades) {
      trades.add(adaptTrade(krakenTrade, currencyPair));
    }

    return new Trades(trades, last, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptTrade(KrakenPublicTrade krakenPublicTrade, CurrencyPair currencyPair) {

    OrderType type = adaptOrderType(krakenPublicTrade.getType());
    BigDecimal tradableAmount = krakenPublicTrade.getVolume();
    Date timestamp = new Date((long) (krakenPublicTrade.getTime() * 1000L));

    return new Trade(type, tradableAmount, currencyPair, krakenPublicTrade.getPrice(), timestamp, "0");
  }

  public static AccountInfo adaptBalance(Map<String, BigDecimal> krakenBalance, String username) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    for (Entry<String, BigDecimal> balancePair : krakenBalance.entrySet()) {
      String currency = adaptCurrency(balancePair.getKey());
      Wallet wallet = new Wallet(currency, balancePair.getValue());
      wallets.add(wallet);
    }
    return new AccountInfo(username, wallets);
  }

  public static Set<CurrencyPair> adaptCurrencyPairs(Collection<String> krakenCurrencyPairs) {

    Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();
    for (String krakenCurrencyPair : krakenCurrencyPairs) {
      currencyPairs.add(adaptCurrencyPair(krakenCurrencyPair));
    }
    return currencyPairs;
  }

  public static String adaptCurrency(String krakenCurrencyCode) {

    String currencyCode = (krakenCurrencyCode.length() == 4) ? krakenCurrencyCode.substring(1) : krakenCurrencyCode;
    return (currencyCode.equals("XBT")) ? Currencies.BTC : currencyCode;
  }

  public static CurrencyPair adaptCurrencyPair(String krakenCurrencyPair) {

    String firstCurrency = adaptCurrency(krakenCurrencyPair.substring(0, 4));
    String secondCurrency = adaptCurrency(krakenCurrencyPair.substring(4));

    return new CurrencyPair(firstCurrency, secondCurrency);
  }

  public static OpenOrders adaptOpenOrders(Map<String, KrakenOrder> krakenOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (Entry<String, KrakenOrder> krakenOrderEntry : krakenOrders.entrySet()) {
      KrakenOrder krakenOrder = krakenOrderEntry.getValue();
      KrakenOrderDescription orderDescription = krakenOrder.getOrderDescription();

      if (!"limit".equals(orderDescription.getOrderType().toString())) {
        // how to handle stop-loss, take-profit, stop-loss-limit, and so on orders?
        // ignore anything but a plain limit order for now
        continue;
      }

      limitOrders.add(adaptLimitOrder(krakenOrder, krakenOrderEntry.getKey()));
    }
    return new OpenOrders(limitOrders);

  }

  public static LimitOrder adaptLimitOrder(KrakenOrder krakenOrder, String id) {

    KrakenOrderDescription orderDescription = krakenOrder.getOrderDescription();
    OrderType type = adaptOrderType(orderDescription.getType());
    BigDecimal tradableAmount = krakenOrder.getVolume().subtract(krakenOrder.getVolumeExecuted());
    String tradableIdentifier = adaptCurrency(orderDescription.getAssetPair().substring(0, 3));
    String transactionCurrency = adaptCurrency(orderDescription.getAssetPair().substring(3));
    Date timestamp = new Date((long) (krakenOrder.getOpenTimestamp() * 1000L));

    return new LimitOrder(type, tradableAmount, new CurrencyPair(tradableIdentifier, transactionCurrency), id, timestamp, orderDescription.getPrice());
  }

  public static Trades adaptTradesHistory(Map<String, KrakenTrade> krakenTrades) {

    List<Trade> trades = new ArrayList<Trade>();
    for (Entry<String, KrakenTrade> krakenTradeEntry : krakenTrades.entrySet()) {
      trades.add(adaptTrade(krakenTradeEntry.getValue(), krakenTradeEntry.getKey()));
    }

    return new Trades(trades, TradeSortType.SortByID);
  }

  public static Trade adaptTrade(KrakenTrade krakenTrade, String tradeId) {

    OrderType orderType = adaptOrderType(krakenTrade.getType());
    BigDecimal tradableAmount = krakenTrade.getVolume();
    String krakenAssetPair = krakenTrade.getAssetPair();
    String tradableIdentifier = adaptCurrency(krakenAssetPair.substring(0, 4));
    String transactionCurrency = adaptCurrency(krakenAssetPair.substring(4));
    Date timestamp = new Date((long) (krakenTrade.getUnixTimestamp() * 1000L));
    BigDecimal averagePrice = krakenTrade.getAverageClosePrice();
    BigDecimal price = (averagePrice == null) ? krakenTrade.getPrice() : averagePrice;

    return new Trade(orderType, tradableAmount, new CurrencyPair(tradableIdentifier, transactionCurrency), price, timestamp, tradeId, krakenTrade.getOrderTxId());
  }

  public static OrderType adaptOrderType(KrakenType krakenType) {

    return krakenType.equals(KrakenType.BUY) ? OrderType.BID : OrderType.ASK;
  }

  public static String adaptOrderId(KrakenOrderResponse orderResponse) {

    List<String> orderIds = orderResponse.getTransactionIds();
    return (orderIds == null || orderIds.isEmpty()) ? "" : orderIds.get(0);
  }
}
