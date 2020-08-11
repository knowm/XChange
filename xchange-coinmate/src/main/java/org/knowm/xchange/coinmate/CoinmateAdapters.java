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
import org.knowm.xchange.coinmate.dto.trade.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

/** @author Martin Stachon */
public class CoinmateAdapters {

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

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public static List<LimitOrder> createOrders(
      List<CoinmateOrderBookEntry> coinmateOrders,
      Order.OrderType type,
      CurrencyPair currencyPair) {
    List<LimitOrder> orders = new ArrayList<>(coinmateOrders.size());
    for (CoinmateOrderBookEntry entry : coinmateOrders) {
      LimitOrder order =
          new LimitOrder(type, entry.getAmount(), currencyPair, null, null, entry.getPrice());
      orders.add(order);
    }
    return orders;
  }

  public static OrderBook adaptOrderBook(
      CoinmateOrderBook coinmateOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks =
        createOrders(coinmateOrderBook.getData().getAsks(), Order.OrderType.ASK, currencyPair);
    List<LimitOrder> bids =
        createOrders(coinmateOrderBook.getData().getBids(), Order.OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  public static Trades adaptTrades(CoinmateTransactions coinmateTransactions) {
    List<Trade> trades = new ArrayList<>(coinmateTransactions.getData().size());

    for (CoinmateTransactionsEntry coinmateEntry : coinmateTransactions.getData()) {
      Trade trade = adaptTrade(coinmateEntry);
      trades.add(trade);
    }

    // TODO correct sort order?
    return new Trades(trades, Trades.TradeSortType.SortByID);
  }

  public static Trade adaptTrade(CoinmateTransactionsEntry coinmateEntry) {
    return new Trade.Builder()
        .originalAmount(coinmateEntry.getAmount())
        .currencyPair(CoinmateUtils.getPair(coinmateEntry.getCurrencyPair()))
        .price(coinmateEntry.getPrice())
        .timestamp(new Date(coinmateEntry.getTimestamp()))
        .id(coinmateEntry.getTransactionId())
        .build();
  }

  public static Wallet adaptWallet(CoinmateBalance coinmateBalance) {

    CoinmateBalanceData funds = coinmateBalance.getData();
    List<Balance> balances = new ArrayList<>(funds.size());
    for (String lcCurrency : funds.keySet()) {
      Currency currency = Currency.getInstance(lcCurrency.toUpperCase());
      Balance balance =
          new Balance(
              currency,
              funds.get(lcCurrency).getBalance(),
              funds.get(lcCurrency).getAvailable(),
              funds.get(lcCurrency).getReserved());

      balances.add(balance);
    }
    return Wallet.Builder.from(balances).build();
  }

  public static UserTrades adaptTransactionHistory(
      CoinmateTransactionHistory coinmateTradeHistory) {
    List<UserTrade> trades = new ArrayList<>(coinmateTradeHistory.getData().size());

    for (CoinmateTransactionHistoryEntry entry : coinmateTradeHistory.getData()) {
      Order.OrderType orderType;
      String transactionType = entry.getTransactionType();
      switch (transactionType) {
        case "BUY":
        case "QUICK_BUY":
          orderType = Order.OrderType.BID;
          break;
        case "SELL":
        case "QUICK_SELL":
          orderType = Order.OrderType.ASK;
          break;
        default:
          // here we ignore the other types, such as withdrawal, voucher etc.
          continue;
      }

      UserTrade trade =
          new UserTrade.Builder()
              .type(orderType)
              .originalAmount(entry.getAmount())
              .currencyPair(
                  CoinmateUtils.getPair(entry.getAmountCurrency() + "_" + entry.getPriceCurrency()))
              .price(entry.getPrice())
              .timestamp(new Date(entry.getTimestamp()))
              .id(Long.toString(entry.getTransactionId()))
              .orderId(Long.toString(entry.getOrderId()))
              .feeAmount(entry.getFee())
              .feeCurrency(Currency.getInstance(entry.getFeeCurrency()))
              .build();
      trades.add(trade);
    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static UserTrades adaptTradeHistory(CoinmateTradeHistory coinmateTradeHistory) {
    List<UserTrade> trades = new ArrayList<>(coinmateTradeHistory.getData().size());

    for (CoinmateTradeHistoryEntry entry : coinmateTradeHistory.getData()) {
      Order.OrderType orderType;
      String transactionType = entry.getType();
      switch (transactionType) {
        case "BUY":
        case "QUICK_BUY":
          orderType = Order.OrderType.BID;
          break;
        case "SELL":
        case "QUICK_SELL":
          orderType = Order.OrderType.ASK;
          break;
        default:
          // here we ignore the other types, such as withdrawal, voucher etc.
          continue;
      }

      UserTrade trade =
          new UserTrade.Builder()
              .type(orderType)
              .originalAmount(entry.getAmount())
              .currencyPair(CoinmateUtils.getPair(entry.getCurrencyPair()))
              .price(entry.getPrice())
              .timestamp(new Date(entry.getCreatedTimestamp()))
              .id(Long.toString(entry.getTransactionId()))
              .orderId(Long.toString(entry.getOrderId()))
              .feeAmount(entry.getFee())
              .feeCurrency(CoinmateUtils.getPair(entry.getCurrencyPair()).counter)
              .build();
      trades.add(trade);
    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static List<FundingRecord> adaptFundingHistory(
      CoinmateTransactionHistory coinmateTradeHistory) {
    List<FundingRecord> fundings = new ArrayList<>();

    for (CoinmateTransactionHistoryEntry entry : coinmateTradeHistory.getData()) {
      FundingRecord.Type type;
      FundingRecord.Status status;

      switch (entry.getTransactionType()) {
        case "WITHDRAWAL":
        case "CREATE_VOUCHER":
          type = FundingRecord.Type.WITHDRAWAL;
          break;
        case "DEPOSIT":
        case "USED_VOUCHER":
        case "NEW_USER_REWARD":
        case "REFERRAL":
          type = FundingRecord.Type.DEPOSIT;
          break;
        default:
          // here we ignore the other types which are trading
          continue;
      }

      switch (entry.getStatus().toUpperCase()) {
        case "OK":
        case "COMPLETED":
          status = FundingRecord.Status.COMPLETE;
          break;
        case "NEW":
        case "SENT":
        case "CREATED":
        case "WAITING":
        case "PENDING":
          status = FundingRecord.Status.PROCESSING;
          break;
        default:
          status = FundingRecord.Status.FAILED;
      }

      String transactionId = Long.toString(entry.getTransactionId());

      String description = entry.getDescription();

      String feeCurrency = entry.getFeeCurrency();
      String externalId = null;
      if (entry.getTransactionType().equals("DEPOSIT")
          && description.startsWith(feeCurrency + ": ")) {
        externalId =
            description.replace(
                feeCurrency + ": ", ""); // the transaction hash is in the description
      }

      FundingRecord funding =
          new FundingRecord(
              null,
              new Date(entry.getTimestamp()),
              Currency.getInstance(entry.getAmountCurrency()),
              entry.getAmount(),
              transactionId,
              externalId,
              type,
              status,
              null,
              entry.getFee(),
              description);

      fundings.add(funding);
    }

    return fundings;
  }

  public static List<LimitOrder> adaptOpenOrders(CoinmateOpenOrders coinmateOpenOrders)
      throws CoinmateException {

    List<LimitOrder> ordersList = new ArrayList<>(coinmateOpenOrders.getData().size());

    for (CoinmateOpenOrdersEntry entry : coinmateOpenOrders.getData()) {

      Order.OrderType orderType;
      // TODO
      if ("BUY".equals(entry.getType())) {
        orderType = Order.OrderType.BID;
      } else if ("SELL".equals(entry.getType())) {
        orderType = Order.OrderType.ASK;
      } else {
        throw new CoinmateException("Unknown order type");
      }

      LimitOrder limitOrder =
          new LimitOrder(
              orderType,
              entry.getAmount(),
              CoinmateUtils.getPair(entry.getCurrencyPair()),
              Long.toString(entry.getId()),
              new Date(entry.getTimestamp()),
              entry.getPrice());

      ordersList.add(limitOrder);
    }

    return ordersList;
  }

  public static String adaptSortOrder(TradeHistoryParamsSorted.Order order) {
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
