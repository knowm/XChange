package org.knowm.xchange.tradeogre;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalance;
import org.knowm.xchange.tradeogre.dto.market.TradeOgreOrderBook;
import org.knowm.xchange.tradeogre.dto.market.TradeOgreTicker;

public class TradeOgreAdapters {

  public static String adaptCurrencyPair(CurrencyPair currencyPair) {
    return currencyPair.counter.toString() + "-" + currencyPair.base.toString();
  }

  public static CurrencyPair adaptTradeOgreCurrencyPair(String currencyPair) {
    String[] split = currencyPair.split("-");
    return new CurrencyPair(split[1], split[0]);
  }

  public static Balance adaptTradeOgreBalance(String currency, TradeOgreBalance tradeOgreBalance) {
    return new Balance.Builder()
        .currency(new Currency(currency.toUpperCase()))
        .available(tradeOgreBalance.getAvailable())
        .total(tradeOgreBalance.getBalance())
        .build();
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, TradeOgreTicker tradeOgreTicker) {
    return new Ticker.Builder()
        .quoteVolume(new BigDecimal(tradeOgreTicker.getVolume()))
        .high(new BigDecimal(tradeOgreTicker.getHigh()))
        .low(new BigDecimal(tradeOgreTicker.getLow()))
        .last(new BigDecimal(tradeOgreTicker.getPrice()))
        .ask(new BigDecimal(tradeOgreTicker.getAsk()))
        .bid(new BigDecimal(tradeOgreTicker.getBid()))
        .currencyPair(currencyPair)
        .build();
  }

  public static OrderBook adaptOrderBook(
      CurrencyPair currencyPair, TradeOgreOrderBook tradeOgreOrderBook) {
    return new OrderBook(
        new Date(System.currentTimeMillis()),
        getOrders(currencyPair, tradeOgreOrderBook, Order.OrderType.ASK),
        getOrders(currencyPair, tradeOgreOrderBook, Order.OrderType.BID),
        true);
  }

  private static List<LimitOrder> getOrders(
      CurrencyPair currencyPair, TradeOgreOrderBook tradeOgreOrderBook, Order.OrderType orderType) {
    Map<BigDecimal, BigDecimal> orders =
        Order.OrderType.BID.equals(orderType)
            ? tradeOgreOrderBook.getBuy()
            : tradeOgreOrderBook.getSell();
    return orders.entrySet().stream()
        .map(
            entry ->
                new LimitOrder.Builder(orderType, currencyPair)
                    .limitPrice(entry.getKey())
                    .originalAmount(entry.getValue())
                    .build())
        .collect(Collectors.toList());
  }
}
