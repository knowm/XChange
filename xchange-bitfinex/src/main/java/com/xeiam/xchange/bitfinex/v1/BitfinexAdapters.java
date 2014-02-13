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
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexLevel;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexBalancesResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

public final class BitfinexAdapters {

  @SuppressWarnings("unused")
  public static final Logger log = LoggerFactory.getLogger(BitfinexAdapters.class);

  private BitfinexAdapters() {

  }

  public static List<LimitOrder> adaptOrders(BitfinexLevel[] orders, String tradableIdentifier, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BitfinexLevel order : orders) {
      // Bid orderbook is reversed order. Insert at index 0 instead of appending
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(0, adaptOrder(order.getAmount(), order.getPrice(), tradableIdentifier, currency, orderType, id));
      }
      else {
        limitOrders.add(adaptOrder(order.getAmount(), order.getPrice(), tradableIdentifier, currency, orderType, id));
      }
    }

    return limitOrders;
  }

  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String tradableIdentifier, String currency, String orderTypeString, String id) {

    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigMoney limitPrice = MoneyUtils.parseMoney(currency, price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, id, null, limitPrice);
  }

  public static Trade adaptTrade(BitfinexTrade trade, String tradableIdentifier, String currency) {

    OrderType orderType = null;
    BigDecimal amount = trade.getAmount();
    BigMoney price = MoneyUtils.parseMoney(currency, trade.getPrice());
    Date date = DateUtils.fromMillisUtc((long) (trade.getTimestamp() * 1000L));
    final String tradeId = String.valueOf(trade.getTimestamp());
    return new Trade(orderType, amount, tradableIdentifier, currency, price, date, tradeId, null);
  }

  public static Trades adaptTrades(BitfinexTrade[] trades, String tradableIdentifier, String currency) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BitfinexTrade trade : trades) {
      tradesList.add(0, adaptTrade(trade, tradableIdentifier, currency));
    }
    return new Trades(tradesList);
  }

  public static Ticker adaptTicker(BitfinexTicker bitfinexTicker, String tradableIdentifier, String currency) {

    BigMoney last = MoneyUtils.parseMoney(currency, bitfinexTicker.getLast_price());
    BigMoney bid = MoneyUtils.parseMoney(currency, bitfinexTicker.getBid());
    BigMoney ask = MoneyUtils.parseMoney(currency, bitfinexTicker.getAsk());
    BigMoney high = MoneyUtils.parseMoney(currency, bitfinexTicker.getAsk());
    BigMoney low = MoneyUtils.parseMoney(currency, bitfinexTicker.getBid());
    BigDecimal volume = BigDecimal.ZERO;

    Date timestamp = DateUtils.fromMillisUtc((long) (bitfinexTicker.getTimestamp() * 1000L));

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp)
        .build();
  }

  public static AccountInfo adaptAccountInfo(BitfinexBalancesResponse[] response) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    for (BitfinexBalancesResponse balance : response) {
      if (balance.getCurrency().equals("usd") || balance.getCurrency().equals("btc")) {
        wallets.add(Wallet.createInstance(balance.getCurrency().toUpperCase(), balance.getAmount(), balance.getType()));
      }
    }

    return new AccountInfo(null, wallets);
  }

  public static OpenOrders adaptOrders(BitfinexOrderStatusResponse[] activeOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(activeOrders.length);

    for (BitfinexOrderStatusResponse order : activeOrders) {
      OrderType orderType = order.getSide().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
      String tradableIdentifier = order.getSymbol().substring(0, 3).toUpperCase();
      String transactionCurrency = order.getSymbol().substring(3).toUpperCase();

      limitOrders.add(new LimitOrder(orderType, order.getRemainingAmount(), tradableIdentifier, transactionCurrency, String.valueOf(order.getId()), new Date((long) order.getTimestamp()), BigMoney.of(
          CurrencyUnit.of(transactionCurrency), order.getPrice())));
    }

    return new OpenOrders(limitOrders);
  }

  public static Trades adaptTradeHistory(BitfinexTradeResponse[] trades, String symbol) {

    List<Trade> pastTrades = new ArrayList<Trade>(trades.length);

    String tradableIdentifier = symbol.substring(0, 3).toUpperCase();
    String transactionCurrency = symbol.substring(3).toUpperCase();

    for (BitfinexTradeResponse trade : trades) {
      OrderType orderType = trade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;

      String id = String.valueOf(trade.hashCode());
      pastTrades.add(new Trade(orderType, trade.getAmount(), tradableIdentifier, transactionCurrency, BigMoney.of(CurrencyUnit.USD, trade.getPrice()), new Date((long) (trade.getTimestamp() * 1000L)),
          id, null));
    }

    return new Trades(pastTrades);
  }
}
