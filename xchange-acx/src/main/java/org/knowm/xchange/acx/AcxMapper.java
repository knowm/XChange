package org.knowm.xchange.acx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.knowm.xchange.acx.dto.AcxTrade;
import org.knowm.xchange.acx.dto.account.AcxAccount;
import org.knowm.xchange.acx.dto.account.AcxAccountInfo;
import org.knowm.xchange.acx.dto.marketdata.AcxMarket;
import org.knowm.xchange.acx.dto.marketdata.AcxOrder;
import org.knowm.xchange.acx.dto.marketdata.AcxOrderBook;
import org.knowm.xchange.acx.dto.marketdata.AcxTicker;
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
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;

public class AcxMapper {

  private Map<String, CurrencyPair> marketMap = new ConcurrentHashMap<>();

  AcxMapper(ExchangeMetaData exchangeMetaData) {
    exchangeMetaData
        .getCurrencyPairs()
        .forEach(
            (currencyPair, currencyPairMetaData) -> {
              marketMap.put(
                  currencyPair.base.toString().toLowerCase()
                      + currencyPair.counter.toString().toLowerCase(),
                  currencyPair);
            });
  }

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

  public LimitOrder mapOrder(CurrencyPair currencyPair, AcxOrder order) {
    OrderType type = mapOrderSide(order.side);
    return new LimitOrder.Builder(type, currencyPair)
        .id(order.id)
        .limitPrice(order.price)
        .averagePrice(order.avgPrice)
        .timestamp(order.createdAt)
        .originalAmount(order.volume)
        .remainingAmount(order.remainingVolume)
        .cumulativeAmount(order.executedVolume)
        .orderStatus(mapOrderStatus(order))
        .build();
  }

  private static OrderType mapOrderSide(String side) {
    switch (side) {
      case "ask":
      case "sell":
        return OrderType.ASK;
      case "bid":
      case "buy":
        return OrderType.BID;
    }
    return null;
  }

  public OrderStatus mapOrderStatus(AcxOrder order) {
    switch (order.state) {
      case "wait":
        return OrderStatus.PENDING_NEW;
      case "done":
        return OrderStatus.FILLED;
      case "cancel":
        if (order.executedVolume.compareTo(BigDecimal.ZERO) > 0) {
          return OrderStatus.PARTIALLY_CANCELED;
        } else {
          return OrderStatus.CANCELED;
        }
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
        Wallet.Builder.from(
                accountInfo.accounts.stream().map(this::mapBalance).collect(Collectors.toList()))
            .build());
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

  public UserTrade mapTrade(AcxTrade trade) {
    return new UserTrade.Builder()
        .currencyPair(mapCurrencyPair(trade.market))
        .id(trade.id)
        .orderId(trade.orderId)
        .price(trade.price)
        .originalAmount(trade.volume)
        .timestamp(trade.createdAt)
        .type(mapOrderSide(trade.side))
        .build();
  }

  public CurrencyPair mapCurrencyPair(String acxMarket) {
    if (marketMap.containsKey(acxMarket)) {
      return marketMap.get(acxMarket);
    } else {
      return new CurrencyPair(
          Currency.getInstance(acxMarket.substring(0, 3)),
          Currency.getInstance(acxMarket.substring(3, 6)));
    }
  }
}
