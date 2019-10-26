package org.knowm.xchange.latoken;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.latoken.dto.account.LatokenBalance;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenCurrency;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenPair;
import org.knowm.xchange.latoken.dto.marketdata.LatokenOrderbook;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTicker;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTrade;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTrades;
import org.knowm.xchange.latoken.dto.trade.LatokenOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderSide;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderStatus;
import org.knowm.xchange.latoken.dto.trade.LatokenUserTrade;
import org.knowm.xchange.latoken.dto.trade.LatokenUserTrades;

public class LatokenAdapters {

  protected static List<LatokenPair> allPairs;

  public static void setAllPairs(List<LatokenPair> allPairs) {
    LatokenAdapters.allPairs = allPairs;
  }

  public static Currency adaptCurrency(LatokenCurrency latokenCurrency) {
    return Currency.getInstance(latokenCurrency.getSymbol());
  }

  public static CurrencyPair adaptCurrencyPair(LatokenPair latokenPair) {
    Currency base = Currency.getInstance(latokenPair.getBaseCurrency());
    Currency counter = Currency.getInstance(latokenPair.getCounterCurrency());

    return new CurrencyPair(base, counter);
  }

  public static CurrencyPairMetaData adaptPairMetaData(LatokenPair latokenPair) {
    BigDecimal tradingFee = BigDecimal.valueOf(latokenPair.getTakerFee());
    BigDecimal minAmount =
        BigDecimal.valueOf(latokenPair.getMinOrderAmount())
            .setScale(latokenPair.getAmountPrecision(), RoundingMode.HALF_DOWN);
    int priceScale = latokenPair.getPricePrecision();
    return new CurrencyPairMetaData(tradingFee, minAmount, null, priceScale, null);
  }

  public static CurrencyPair adaptCurrencyPair(String latokenSymbol) {
    if (allPairs == null) {
      throw new ExchangeException(
          "allPairs is not set! Make sure to trigger Exchange's remote init method");
    }
    Optional<LatokenPair> oPair =
        allPairs.stream().filter(pair -> pair.getSymbol().equals(latokenSymbol)).findAny();
    return oPair.isPresent() ? adaptCurrencyPair(oPair.get()) : null;
  }

  public static Balance adaptBalance(LatokenBalance latokenBalance) {
    return new Balance(
        Currency.getInstance(latokenBalance.getSymbol()),
        BigDecimal.valueOf(latokenBalance.getAmount()),
        BigDecimal.valueOf(latokenBalance.getAvailable()),
        BigDecimal.valueOf(latokenBalance.getFrozen()),
        BigDecimal.ZERO, // Borrowed
        BigDecimal.ZERO, // Loaned
        BigDecimal.ZERO, // Withdrawing
        BigDecimal.valueOf(latokenBalance.getPending()) // Depositing
        );
  }

  public static Ticker adaptTicker(LatokenTicker latokenTicker) {
    return new Ticker.Builder()
        .open(BigDecimal.valueOf(latokenTicker.getOpen()))
        .last(BigDecimal.valueOf(latokenTicker.getClose()))
        .low(BigDecimal.valueOf(latokenTicker.getLow()))
        .high(BigDecimal.valueOf(latokenTicker.getHigh()))
        .volume(BigDecimal.valueOf(latokenTicker.getVolume()))
        .build();
  }

  public static OrderBook adaptOrderBook(LatokenOrderbook latokenOrderbook) {
    CurrencyPair pair = adaptCurrencyPair(latokenOrderbook.getSymbol());
    List<LimitOrder> asks =
        latokenOrderbook.getAsks().stream()
            .map(
                level ->
                    new LimitOrder(
                        OrderType.ASK,
                        BigDecimal.valueOf(level.getAmount()),
                        pair,
                        null,
                        null,
                        BigDecimal.valueOf(level.getPrice())))
            .collect(Collectors.toList());
    List<LimitOrder> bids =
        latokenOrderbook.getBids().stream()
            .map(
                level ->
                    new LimitOrder(
                        OrderType.BID,
                        BigDecimal.valueOf(level.getAmount()),
                        pair,
                        null,
                        null,
                        BigDecimal.valueOf(level.getPrice())))
            .collect(Collectors.toList());

    return new OrderBook(null, asks, bids);
  }

  public static Trades adaptTrades(LatokenTrades latokenTrades) {
    CurrencyPair pair = adaptCurrencyPair(latokenTrades.getSymbol());
    List<Trade> trades =
        latokenTrades.getTrades().stream()
            .map(latokenTrade -> adaptTrade(latokenTrade, pair))
            .collect(Collectors.toList());

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptTrade(LatokenTrade latokenTrade, CurrencyPair pair) {
    return new Trade(
        adaptOrderType(latokenTrade.getSide()),
        BigDecimal.valueOf(latokenTrade.getAmount()),
        pair,
        BigDecimal.valueOf(latokenTrade.getPrice()),
        latokenTrade.getTimestamp(),
        null);
  }

  public static LimitOrder adaptOrder(LatokenOrder order) {
    OrderType type = adaptOrderType(order.getSide());
    CurrencyPair currencyPair = adaptCurrencyPair(order.getSymbol());
    OrderStatus orderStatus = adaptOrderStatus(order.getOrderStatus());

    return new LimitOrder.Builder(type, currencyPair)
        .originalAmount(BigDecimal.valueOf(order.getAmount()))
        .id(order.getOrderId())
        .timestamp(order.getTimeCreated())
        .limitPrice(BigDecimal.valueOf(order.getPrice()))
        .cumulativeAmount(BigDecimal.valueOf(order.getExecutedAmount()))
        .fee(BigDecimal.ZERO)
        .orderStatus(orderStatus)
        .build();
  }

  public static OpenOrders adaptOpenOrders(List<LatokenOrder> latokenOpenOrders) {
    List<LimitOrder> openOrders =
        latokenOpenOrders.stream()
            .map(latokenOrder -> LatokenAdapters.adaptOrder(latokenOrder))
            .collect(Collectors.toList());
    return new OpenOrders(openOrders);
  }

  public static OrderType adaptOrderType(LatokenOrderSide side) {
    switch (side) {
      case buy:
        return OrderType.BID;
      case sell:
        return OrderType.ASK;
      default:
        throw new RuntimeException("Not supported order side: " + side);
    }
  }

  public static OrderStatus adaptOrderStatus(LatokenOrderStatus latokenOrderStatus) {
    switch (latokenOrderStatus) {
      case active:
    	// Not exactly accurate as Active includes both New and partiallyFilled orders.
        return OrderStatus.NEW; 
      case partiallyFilled:
        return OrderStatus.PARTIALLY_FILLED;
      case filled:
        return OrderStatus.FILLED;
      case cancelled:
        return OrderStatus.CANCELED;

      default:
        return OrderStatus.UNKNOWN;
    }
  }

  public static UserTrades adaptUserTrades(LatokenUserTrades latokenUserTrades) {

    CurrencyPair pair = adaptCurrencyPair(latokenUserTrades.getSymbol());
    List<UserTrade> trades =
        latokenUserTrades.getTrades().stream()
            .map(latokenUserTrade -> adaptUserTrade(latokenUserTrade, pair))
            .collect(Collectors.toList());

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptUserTrade(LatokenUserTrade latokenUserTrade, CurrencyPair pair) {
    return new UserTrade(
        adaptOrderType(latokenUserTrade.getSide()),
        BigDecimal.valueOf(latokenUserTrade.getAmount()),
        pair,
        BigDecimal.valueOf(latokenUserTrade.getPrice()),
        latokenUserTrade.getTime(),
        latokenUserTrade.getId(),
        latokenUserTrade.getOrderId(),
        BigDecimal.valueOf(latokenUserTrade.getFee()),
        pair.counter); // Fee is always in counter currency
  }

  // --------------- Convert to Latoken convention --------------------------

  public static String toSymbol(CurrencyPair pair) {
    return pair.base.getCurrencyCode() + pair.counter.getCurrencyCode();
  }

  public static String toSymbol(Currency currency) {
    return currency.getSymbol();
  }

  public static LatokenOrderSide toOrderSide(OrderType type) {
    switch (type) {
      case ASK:
        return LatokenOrderSide.sell;
      case BID:
        return LatokenOrderSide.buy;
      default:
        throw new RuntimeException("Not supported order type: " + type);
    }
  }

  public static LatokenOrderStatus toLatokenOrderStatus(OrderStatus status) {
    switch (status) {
      case PENDING_NEW:
      case NEW:
      case PENDING_CANCEL:
      case PENDING_REPLACE:
        return LatokenOrderStatus.active;
      case PARTIALLY_FILLED:
        return LatokenOrderStatus.partiallyFilled;
      case FILLED:
        return LatokenOrderStatus.filled;
      case CANCELED:
      case STOPPED:
      case EXPIRED:
      case REJECTED:
      case REPLACED:
        return LatokenOrderStatus.cancelled;
      default:
        throw new RuntimeException("Not supported order status: " + status);
    }
  }
}
