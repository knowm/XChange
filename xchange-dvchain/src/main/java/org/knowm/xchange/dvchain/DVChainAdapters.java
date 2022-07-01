package org.knowm.xchange.dvchain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainLevel;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketData;
import org.knowm.xchange.dvchain.dto.trade.DVChainTrade;

public class DVChainAdapters {
  private DVChainAdapters() {}

  public static LimitOrder adaptOrder(
      DVChainLevel level, Order.OrderType type, CurrencyPair currencyPair) {
    BigDecimal price = null;
    if (type == Order.OrderType.BID) {
      price = level.getBuyPrice();
    } else {
      price = level.getSellPrice();
    }
    return new LimitOrder(type, level.getMaxQuantity(), currencyPair, "-1", null, price);
  }

  public static OrderBook adaptOrderBook(
      DVChainMarketData marketData, Long time, CurrencyPair currencyPair) {
    Date timeStamp = Date.from(Instant.ofEpochMilli(time));
    List<LimitOrder> asks =
        marketData.getLevels().stream()
            .map(level -> adaptOrder(level, Order.OrderType.ASK, currencyPair))
            .collect(Collectors.toList());
    List<LimitOrder> bids =
        marketData.getLevels().stream()
            .map(level -> adaptOrder(level, Order.OrderType.BID, currencyPair))
            .collect(Collectors.toList());
    return new OrderBook(timeStamp, asks, bids);
  }

  public static UserTrades adaptTradeHistory(List<DVChainTrade> trades) {

    List<UserTrade> pastTrades = new ArrayList<>(trades.size());
    for (DVChainTrade trade : trades) {
      Order.OrderType orderType =
          trade.getSide().equalsIgnoreCase("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
      Date timestamp = Date.from(trade.getCreatedAt());
      CurrencyPair currencyPair = new CurrencyPair(trade.getAsset(), "USD");
      final BigDecimal fee = null;
      pastTrades.add(
          new UserTrade.Builder()
              .type(orderType)
              .originalAmount(trade.getQuantity())
              .currencyPair(currencyPair)
              .price(trade.getPrice())
              .timestamp(timestamp)
              .id(trade.getId())
              .orderId(trade.getId())
              .feeAmount(fee)
              .feeCurrency(Currency.USD)
              .build());
    }
    return new UserTrades(pastTrades, Trades.TradeSortType.SortByTimestamp);
  }

  public static Trade adaptTrade(DVChainTrade trade, CurrencyPair currencyPair) {

    Order.OrderType orderType =
        trade.getSide().equals("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
    BigDecimal amount = trade.getQuantity();
    BigDecimal price = trade.getPrice();
    Date date = Date.from(trade.getCreatedAt());
    final String tradeId = trade.getId();
    return new Trade.Builder()
        .type(orderType)
        .originalAmount(amount)
        .currencyPair(currencyPair)
        .price(price)
        .timestamp(date)
        .id(tradeId)
        .build();
  }

  public static Trades adaptTrades(List<DVChainTrade> trades) {

    List<Trade> tradesList = new ArrayList<>(trades.size());
    long lastTradeId = 0;
    for (DVChainTrade trade : trades) {
      CurrencyPair currencyPair = new CurrencyPair(trade.getAsset(), "USD");
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, Trades.TradeSortType.SortByID);
  }

  public static OpenOrders adaptOpenOrders(List<DVChainTrade> orders) {
    List<LimitOrder> openOrders = new ArrayList<>(orders.size());
    for (DVChainTrade order : orders) {
      CurrencyPair currencyPair = new CurrencyPair(order.getAsset(), "USD");
      Order.OrderType orderType =
          order.getSide().equals("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
      LimitOrder limitOrder =
          new LimitOrder(
              orderType,
              order.getQuantity(),
              order.getQuantity(),
              currencyPair,
              order.getId(),
              Date.from(order.getCreatedAt()),
              order.getLimitPrice());
      openOrders.add(limitOrder);
    }
    return new OpenOrders(openOrders);
  }
}
