/*
 * The MIT License
 *
 * Copyright 2015-2016 Coinmate.
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
package org.knowm.xchange.coinmate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.coinmate.dto.account.CoinmateBalance;
import org.knowm.xchange.coinmate.dto.account.CoinmateBalanceData;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBook;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBookEntry;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTicker;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTransactions;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTransactionsEntry;
import org.knowm.xchange.coinmate.dto.trade.CoinmateOpenOrders;
import org.knowm.xchange.coinmate.dto.trade.CoinmateOpenOrdersEntry;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistory;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistoryEntry;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

/**
 * @author Martin Stachon
 */
public class CoinmateAdapters {

  // the currency pairs supported by the exchange
  public static final CurrencyPair[] COINMATE_CURRENCY_PAIRS = {CurrencyPair.BTC_EUR, CurrencyPair.BTC_CZK,};

  /**
   * Adapts a CoinmateTicker to a Ticker Object
   *
   * @param coinmateTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(CoinmateTicker coinmateTicker, CurrencyPair currencyPair) {

    BigDecimal last = coinmateTicker.getData().getLast();
    BigDecimal bid = coinmateTicker.getData().getBid();
    BigDecimal ask = coinmateTicker.getData().getAsk();
    BigDecimal high = coinmateTicker.getData().getHigh();
    BigDecimal low = coinmateTicker.getData().getLow();
    BigDecimal volume = coinmateTicker.getData().getAmount();
    Date timestamp = new Date(coinmateTicker.getData().getTimestamp() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp).build();

  }

  public static List<LimitOrder> createOrders(List<CoinmateOrderBookEntry> coinmateOrders, Order.OrderType type, CurrencyPair currencyPair) {
    List<LimitOrder> orders = new ArrayList<>(coinmateOrders.size());
    for (CoinmateOrderBookEntry entry : coinmateOrders) {
      LimitOrder order = new LimitOrder(type, entry.getAmount(), currencyPair, null, null, entry.getPrice());
      orders.add(order);
    }
    return orders;
  }

  public static OrderBook adaptOrderBook(CoinmateOrderBook coinmateOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = createOrders(coinmateOrderBook.getData().getAsks(), Order.OrderType.ASK, currencyPair);
    List<LimitOrder> bids = createOrders(coinmateOrderBook.getData().getBids(), Order.OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  public static Trades adaptTrades(CoinmateTransactions coinmateTransactions) {
    List<Trade> trades = new ArrayList<>(coinmateTransactions.getData().size());

    for (CoinmateTransactionsEntry coinmateEntry : coinmateTransactions.getData()) {
      Trade trade = adaptTrade(coinmateEntry);
      trades.add(trade);
    }

    //TODO correct sort order?
    return new Trades(trades, Trades.TradeSortType.SortByID);
  }

  public static Trade adaptTrade(CoinmateTransactionsEntry coinmateEntry) {
    return new Trade(null, coinmateEntry.getAmount(), CoinmateUtils.getPair(coinmateEntry.getCurrencyPair()), coinmateEntry.getPrice(),
        new Date(coinmateEntry.getTimestamp()), coinmateEntry.getTransactionId());
  }

  public static Wallet adaptWallet(CoinmateBalance coinmateBalance) {

    CoinmateBalanceData funds = coinmateBalance.getData();
    List<Balance> balances = new ArrayList<>(funds.size());

    for (String lcCurrency : funds.keySet()) {
      Currency currency = Currency.getInstance(lcCurrency.toUpperCase());
      Balance balance = new Balance(currency, funds.get(lcCurrency).getBalance(), funds.get(lcCurrency).getAvailable(),
          funds.get(lcCurrency).getReserved());

      balances.add(balance);
    }
    return new Wallet(balances);
  }

  public static UserTrades adaptTradeHistory(CoinmateTransactionHistory coinmateTradeHistory) {
    List<UserTrade> trades = new ArrayList<>(coinmateTradeHistory.getData().size());

    for (CoinmateTransactionHistoryEntry entry : coinmateTradeHistory.getData()) {
      Order.OrderType orderType;
      String transactionType = entry.getTransactionType();
      if (transactionType.equals("BUY") || transactionType.equals("QUICK_BUY")) {
        orderType = Order.OrderType.BID;
      } else if (transactionType.equals("SELL") || transactionType.equals("QUICK_SELL")) {
        orderType = Order.OrderType.ASK;
      } else {
        // here we ignore the other types, such as withdrawal, voucher etc.
        continue;
      }

      UserTrade trade = new UserTrade(orderType, entry.getAmount(), CoinmateUtils.getPair(entry.getAmountCurrency() + "_" + entry.getPriceCurrency()),
          entry.getPrice(), new Date(entry.getTimestamp()), Long.toString(entry.getTransactionId()), Long.toString(entry.getOrderId()),
          entry.getFee(), Currency.getInstance(entry.getFeeCurrency()));
      trades.add(trade);

    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static List<LimitOrder> adaptOpenOrders(CoinmateOpenOrders coinmateOpenOrders, CurrencyPair currencyPair) throws CoinmateException {

    List<LimitOrder> ordersList = new ArrayList<>(coinmateOpenOrders.getData().size());

    for (CoinmateOpenOrdersEntry entry : coinmateOpenOrders.getData()) {

      Order.OrderType orderType;
      //TODO
      if ("BUY".equals(entry.getType())) {
        orderType = Order.OrderType.BID;
      } else if ("SELL".equals(entry.getType())) {
        orderType = Order.OrderType.ASK;
      } else {
        throw new CoinmateException("Unknown order type");
      }

      LimitOrder limitOrder = new LimitOrder(orderType, entry.getAmount(), currencyPair, Long.toString(entry.getId()), new Date(entry.getTimestamp()),
          entry.getPrice());

      ordersList.add(limitOrder);
    }

    return ordersList;
  }

  public static String adaptOrder(TradeHistoryParamsSorted.Order order) {
    switch (order) {
      case asc:
        return "ASC";
      case desc:
        return "DESC";
      default:
        throw new IllegalArgumentException();
    }
  }
}
