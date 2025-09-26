package org.knowm.xchange.tradeogre;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalance;
import org.knowm.xchange.tradeogre.dto.marketdata.TradeOgreOrderBook;
import org.knowm.xchange.tradeogre.dto.marketdata.TradeOgreTicker;
import org.knowm.xchange.tradeogre.dto.trade.TradeOgreOrder;
import org.knowm.xchange.tradeogre.dto.trade.TradeOgreTrade;

public class TradeOgreAdapters {

  public static String adaptCurrencyPair(CurrencyPair currencyPair) {
    return currencyPair.getBase().toString() + "-" + currencyPair.getCounter().toString();
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
        .volume(
            tradeOgreTicker.getVolume() != null
                ? new BigDecimal(tradeOgreTicker.getVolume())
                : null)
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
    return orders == null
        ? new ArrayList<>()
        : orders.entrySet().stream()
            .map(
                entry ->
                    new LimitOrder.Builder(orderType, currencyPair)
                        .limitPrice(entry.getKey())
                        .originalAmount(entry.getValue())
                        .build())
            .collect(Collectors.toList());
  }

  private static Order.OrderType getType(TradeOgreOrder tradeOgreOrder) {
    if ("buy".equals(tradeOgreOrder.getType())) {
      return Order.OrderType.BID;
    }
    if ("sell".equals(tradeOgreOrder.getType())) {
      return Order.OrderType.ASK;
    }
    return null;
  }

  public static OpenOrders adaptOpenOrders(Collection<TradeOgreOrder> tradeOgreOrders) {
    List<LimitOrder> orders =
        tradeOgreOrders.stream()
            .map(
                tradeOgreOrder ->
                    new LimitOrder.Builder(
                            getType(tradeOgreOrder),
                            adaptTradeOgreCurrencyPair(tradeOgreOrder.getMarket()))
                        .limitPrice(tradeOgreOrder.getPrice())
                        .originalAmount(tradeOgreOrder.getQuantity())
                        .timestamp(new Date(tradeOgreOrder.getDate()))
                        .id(tradeOgreOrder.getUuid())
                        .build())
            .collect(Collectors.toList());
    return new OpenOrders(orders);
  }

  public static UserTrades adaptTradeHistory(List<TradeOgreTrade> tradeHistory) {
    List<UserTrade> trades =
        tradeHistory.stream()
            .map(
                tradeOgreTrade ->
                    UserTrade.builder()
                        //
                        // .currencyPair(adaptTradeOgreCurrencyPair(tradeOgreTrade.getMarket()))
                        .originalAmount(tradeOgreTrade.getQuantity())
                        .price(tradeOgreTrade.getPrice())
                        //                        .feeAmount(tradeOgreTrade.getFee())
                        .feeCurrency(Currency.BTC)
                        //                        .id(tradeOgreTrade.getUuid())
                        .type(
                            tradeOgreTrade.getType().equals("buy")
                                ? Order.OrderType.BID
                                : Order.OrderType.ASK)
                        .timestamp(new Date(tradeOgreTrade.getDate()))
                        .build())
            .collect(Collectors.toList());
    return new UserTrades(trades, Trades.TradeSortType.SortByID);
  }
}
