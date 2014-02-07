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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicOrder;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrade;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderDescription;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;

public class KrakenAdapters {

  public static OrderBook adaptOrderBook(KrakenDepth krakenDepth, String currency, String tradableIdentifier) {

    List<LimitOrder> bids = KrakenAdapters.adaptOrders(krakenDepth.getBids(), currency, tradableIdentifier, "bids");
    List<LimitOrder> asks = KrakenAdapters.adaptOrders(krakenDepth.getAsks(), currency, tradableIdentifier, "asks");
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> adaptOrders(List<KrakenPublicOrder> orders, String currency, String tradableIdentifier, String orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.size());
    for (KrakenPublicOrder order : orders) {
      limitOrders.add(adaptOrder(order, orderType, currency, tradableIdentifier));
    }
    return limitOrders;
  }

  private static LimitOrder adaptOrder(KrakenPublicOrder order, String orderType, String currency, String tradableIdentifier) {

    OrderType type = orderType.equalsIgnoreCase("asks") ? OrderType.ASK : OrderType.BID;
    Date timeStamp = new Date(order.getTimestamp() * 1000);
    BigMoney price = BigMoney.of(CurrencyUnit.of(currency), order.getPrice());
    BigDecimal volume = order.getVolume();

    return new LimitOrder(type, volume, tradableIdentifier, currency, "", timeStamp, price);
  }

  public static Ticker adaptTicker(KrakenTicker krakenTicker, String currency, String tradableIdentifier) {

    TickerBuilder builder = new TickerBuilder();
    builder.withAsk(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.getAsk().getPrice()));
    builder.withBid(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.getBid().getPrice()));
    builder.withLast(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.getClose().getPrice()));
    builder.withHigh(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.get24HourHigh()));
    builder.withLow(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.get24HourLow()));
    builder.withVolume(krakenTicker.get24HourVolume());
    builder.withTradableIdentifier(tradableIdentifier);
    return builder.build();
  }

  public static Trades adaptTrades(List<KrakenPublicTrade> krakenTrades, String currency, String tradableIdentifier, long last) {

    List<Trade> trades = new LinkedList<Trade>();
    for (KrakenPublicTrade krakenTrade : krakenTrades) {
      OrderType type = krakenTrade.getType().equalsIgnoreCase("s") ? OrderType.ASK : OrderType.BID;
      BigDecimal tradableAmount = krakenTrade.getVolume();
      BigMoney price = BigMoney.of(CurrencyUnit.of(currency), krakenTrade.getPrice());
      Date timestamp = new Date((long) krakenTrade.getTime() * 1000L);
      
      trades.add(new Trade(type, tradableAmount, tradableIdentifier, currency, price, timestamp, "0", null));

    }
    return new Trades(trades, last);
  }

  public static AccountInfo adaptBalance(Map<String, BigDecimal> krakenBalance, String username) {

    List<Wallet> wallets = new LinkedList<Wallet>();
    for (Entry<String, BigDecimal> balancePair : krakenBalance.entrySet()) {
      String currency = KrakenUtils.getStandardCurrencyCode(balancePair.getKey());
      Wallet wallet = Wallet.createInstance(currency, balancePair.getValue());
      wallets.add(wallet);
    }
    return new AccountInfo(username, wallets);
  }

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<String> krakenCurrencyPairs) {

    List<CurrencyPair> currencyPairs = new LinkedList<CurrencyPair>();
    for (String krakenCurrencyPair : krakenCurrencyPairs) {
      String firstCurrency = krakenCurrencyPair.substring(0, 4);
      String secondCurrency = krakenCurrencyPair.substring(4);
      currencyPairs.add(new CurrencyPair(KrakenUtils.getStandardCurrencyCode(firstCurrency), KrakenUtils.getStandardCurrencyCode(secondCurrency)));
    }
    return currencyPairs;
  }

  public static OpenOrders adaptOpenOrders(Map<String, KrakenOrder> krakenOrders) {

    List<LimitOrder> limitOrders = new LinkedList<LimitOrder>();
    for (Entry<String, KrakenOrder> krakenOrder : krakenOrders.entrySet()) {
      KrakenOrderDescription orderDescription = krakenOrder.getValue().getOrderDescription();

      if (!"limit".equals(orderDescription.getOrderType().toString())) {
        // how to handle stop-loss, take-profit, stop-loss-limit, and so on orders?
        // ignore anything but a plain limit order for now
        continue;
      }

      OrderType type = orderDescription.getType().equals(KrakenType.BUY) ? OrderType.BID : OrderType.ASK;
      BigDecimal tradableAmount = krakenOrder.getValue().getVolume().subtract(krakenOrder.getValue().getVolumeExecuted());

      String tradableIdentifier = KrakenUtils.getStandardCurrencyCode(orderDescription.getAssetPair().substring(0, 3));
      String transactionCurrency = KrakenUtils.getStandardCurrencyCode(orderDescription.getAssetPair().substring(3));
      String id = krakenOrder.getKey();
      Date timestamp = new Date((long) (krakenOrder.getValue().getOpenTimestamp() * 1000L));
      BigMoney limitPrice = BigMoney.of(CurrencyUnit.of(transactionCurrency), orderDescription.getPrice());
      LimitOrder order = new LimitOrder(type, tradableAmount, tradableIdentifier, transactionCurrency, id, timestamp, limitPrice);
      limitOrders.add(order);
    }
    return new OpenOrders(limitOrders);

  }

  public static Trades adaptKrakenTrades(Map<String, KrakenTrade> krakenTrades) {

    List<Trade> trades = new ArrayList<Trade>();
    for (Entry<String, KrakenTrade> krakenTradeEntry : krakenTrades.entrySet()) {
      
      KrakenTrade krakenTrade = krakenTradeEntry.getValue();
      
      OrderType orderType = krakenTrade.getType().equals(KrakenType.BUY) ? OrderType.BID : OrderType.ASK;
      BigDecimal tradableAmount = krakenTrade.getVolume();
      String tradableIdentifier = KrakenUtils.getStandardCurrencyCode(krakenTrade.getAssetPair().substring(0, 4));
      String transactionCurrency = KrakenUtils.getStandardCurrencyCode(krakenTrade.getAssetPair().substring(4));
      Date timestamp = new Date((long) (krakenTrade.getUnixTimestamp() * 1000L));
      BigMoney price = BigMoney.of(CurrencyUnit.of(transactionCurrency), krakenTrade.getPrice());

      final String orderTxId = krakenTrade.getOrderTxId();
      Trade trade = new Trade(orderType, tradableAmount, tradableIdentifier, transactionCurrency, price, timestamp, krakenTradeEntry.getKey(), orderTxId);
      trades.add(trade);
    }
    return new Trades(trades);
  }
}
