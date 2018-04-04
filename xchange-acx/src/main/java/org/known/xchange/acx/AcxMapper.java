package org.known.xchange.acx;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.known.xchange.acx.dto.AcxTrade;
import org.known.xchange.acx.dto.account.AcxAccount;
import org.known.xchange.acx.dto.account.AcxAccountInfo;
import org.known.xchange.acx.dto.marketdata.AcxMarket;
import org.known.xchange.acx.dto.marketdata.AcxOrder;
import org.known.xchange.acx.dto.marketdata.AcxOrderBook;
import org.known.xchange.acx.dto.marketdata.AcxTicker;

public class AcxMapper {
  public Ticker mapTicker(CurrencyPair currencyPair, AcxMarket tickerData) {
    AcxTicker ticker = tickerData.ticker;
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .timestamp(new Date(tickerData.at * 1000))
        .ask(ticker.sell)
        .bid(ticker.buy)
        .open(ticker.open)
        .low(ticker.low)
        .high(ticker.high)
        .last(ticker.last)
        .volume(ticker.vol)
        .build();
  }

  public OrderBook mapOrderBook(CurrencyPair currencyPair, AcxOrderBook orderBook) {
    return new OrderBook(
        null, mapOrders(currencyPair, orderBook.asks), mapOrders(currencyPair, orderBook.bids));
  }

  public List<LimitOrder> mapOrders(CurrencyPair currencyPair, List<AcxOrder> orders) {
    return orders.stream().map(o -> mapOrder(currencyPair, o)).collect(Collectors.toList());
  }

  private LimitOrder mapOrder(CurrencyPair currencyPair, AcxOrder order) {
    OrderType type = mapOrderType(order);
    return new LimitOrder.Builder(type, currencyPair)
        .id(order.id)
        .limitPrice(order.price)
        .averagePrice(order.avgPrice)
        .timestamp(order.createdAt)
        .originalAmount(order.volume)
        .remainingAmount(order.remainingVolume)
        .cumulativeAmount(order.executedVolume)
        .orderStatus(mapOrderStatus(order.state))
        .build();
  }

  private OrderType mapOrderType(AcxOrder order) {
    switch (order.side) {
      case "sell":
        return OrderType.ASK;
      case "buy":
        return OrderType.BID;
    }
    return null;
  }

  private OrderStatus mapOrderStatus(String state) {
    switch (state) {
      case "wait":
        return OrderStatus.PENDING_NEW;
      case "done":
        return OrderStatus.FILLED;
      case "cancel":
        return OrderStatus.CANCELED;
    }
    return null;
  }

  public Trades mapTrades(CurrencyPair currencyPair, List<AcxTrade> trades) {
    return new Trades(
        trades.stream().map(t -> mapTrade(currencyPair, t)).collect(Collectors.toList()),
        TradeSortType.SortByTimestamp);
  }

  private Trade mapTrade(CurrencyPair currencyPair, AcxTrade trade) {
    return new Trade.Builder()
        .currencyPair(currencyPair)
        .id(trade.id)
        .price(trade.price)
        .originalAmount(trade.volume)
        .timestamp(trade.createdAt)
        .type(mapTradeType(trade.side))
        .build();
  }

  private OrderType mapTradeType(String side) {
    if ("sell".equals(side)) {
      return OrderType.ASK;
    } else if ("buy".equals(side)) {
      return OrderType.BID;
    }
    return null;
  }

  public AccountInfo mapAccountInfo(AcxAccountInfo accountInfo) {
    return new AccountInfo(
        accountInfo.name,
        new Wallet(
            accountInfo.accounts.stream().map(this::mapBalance).collect(Collectors.toList())));
  }

  private Balance mapBalance(AcxAccount acc) {
    return new Balance(
        Currency.getInstance(acc.currency), acc.balance.add(acc.locked), acc.balance, acc.locked);
  }

  public String getOrderType(OrderType type) {
    switch (type) {
      case BID:
        return "buy";
      case ASK:
        return "sell";
    }
    throw new IllegalArgumentException("Unknown order type: " + type);
  }
}
