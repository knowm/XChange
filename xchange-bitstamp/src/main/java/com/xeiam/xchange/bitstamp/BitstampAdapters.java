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
package com.xeiam.xchange.bitstamp;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTicker;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bitstamp DTOs to XChange DTOs
 */
public final class BitstampAdapters {

  /**
   * private Constructor
   */
  private BitstampAdapters() {

  }

  /**
   * Adapts a BitstampBalance to a AccountInfo
   * 
   * @param bitstampBalance The Bitstamp balance
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(BitstampBalance bitstampBalance, String userName) {

    // Adapt to XChange DTOs
    Wallet usdWallet = new Wallet(Currencies.USD, bitstampBalance.getUsdBalance());
    Wallet btcWallet = new Wallet(Currencies.BTC, bitstampBalance.getBtcBalance());

    return new AccountInfo(userName, bitstampBalance.getFee(), Arrays.asList(usdWallet, btcWallet));
  }

  /**
   * Adapts a com.xeiam.xchange.bitstamp.api.model.OrderBook to a OrderBook Object
   * 
   * @param currencyPair (e.g. BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrders(BitstampOrderBook bitstampOrderBook, CurrencyPair currencyPair, int timeScale) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, bitstampOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, bitstampOrderBook.getBids());
    Date date = new Date(bitstampOrderBook.getTimestamp() * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new OrderBook(date, asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
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

  /**
   * Adapts a Transaction[] to a Trades Object
   * 
   * @param transactions The Bitstamp transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitstampTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    for (BitstampTransaction tx : transactions) {
      final String tradeId = String.valueOf(tx.getTid());
      trades.add(new Trade(null, tx.getAmount(), currencyPair, tx.getPrice(), DateUtils.fromMillisUtc(tx.getDate() * 1000L), tradeId));
    }

    return new Trades(trades, TradeSortType.SortByID);
  }

  /**
   * Adapts a Transaction to a Trade Object
   * 
   * @param transactions The Bitstamp transaction
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BitstampTransaction tx, CurrencyPair currencyPair, int timeScale) {

    final String tradeId = String.valueOf(tx.getTid());
    Date date = DateUtils.fromMillisUtc(tx.getDate() * timeScale);// polled order books provide a timestamp in seconds, stream in ms
    return new Trade(null, tx.getAmount(), currencyPair, tx.getPrice(), date, tradeId);
  }

  /**
   * Adapts a BitstampTicker to a Ticker Object
   * 
   * @param bitstampTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitstampTicker bitstampTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitstampTicker.getLast();
    BigDecimal bid = bitstampTicker.getBid();
    BigDecimal ask = bitstampTicker.getAsk();
    BigDecimal high = bitstampTicker.getHigh();
    BigDecimal low = bitstampTicker.getLow();
    BigDecimal volume = bitstampTicker.getVolume();
    Date timestamp = new Date(bitstampTicker.getTimestamp() * 1000L);

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();

  }

  /**
   * Adapt the user's trades
   * 
   * @param bitstampUserTransactions
   * @return
   */
  public static Trades adaptTradeHistory(BitstampUserTransaction[] bitstampUserTransactions) {

    List<Trade> trades = new ArrayList<Trade>();
    for (BitstampUserTransaction bitstampUserTransaction : bitstampUserTransactions) {
      if (bitstampUserTransaction.getType().equals(BitstampUserTransaction.TransactionType.trade)) { // skip account deposits and withdrawals.
        OrderType orderType = bitstampUserTransaction.getUsd().doubleValue() > 0.0 ? OrderType.ASK : OrderType.BID;
        BigDecimal tradableAmount = bitstampUserTransaction.getBtc();
        BigDecimal price = bitstampUserTransaction.getPrice();
        Date timestamp = BitstampUtils.parseDate(bitstampUserTransaction.getDatetime());
        final String tradeId = String.valueOf(bitstampUserTransaction.getId());
        final String orderId = String.valueOf(bitstampUserTransaction.getOrderId());

        Trade trade = new Trade(orderType, tradableAmount, CurrencyPair.BTC_USD, price, timestamp, tradeId, orderId);
        trades.add(trade);
      }
    }

    return new Trades(trades, TradeSortType.SortByID);
  }
}
