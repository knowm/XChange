package org.knowm.xchange.latoken;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
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

  public static Currency adaptCurrency(LatokenCurrency latokenCurrency) {
    return Currency.getInstance(latokenCurrency.getSymbol());
  }

  public static CurrencyPair adaptCurrencyPair(LatokenPair latokenPair) {
    Currency base = Currency.getInstance(latokenPair.getBaseCurrency());
    Currency counter = Currency.getInstance(latokenPair.getCounterCurrency());
    return new CurrencyPair(base, counter);
  }

  public static CurrencyPairMetaData adaptPairMetaData(LatokenPair latokenPair) {
    BigDecimal tradingFee = latokenPair.getTakerFee();
    BigDecimal minAmount =
        latokenPair
            .getMinOrderAmount()
            .setScale(latokenPair.getAmountPrecision(), RoundingMode.HALF_DOWN);
    int priceScale = latokenPair.getPricePrecision();
    return new CurrencyPairMetaData(tradingFee, minAmount, null, priceScale, null);
  }

  public static CurrencyPair adaptCurrencyPair(Exchange exchange, String latokenSymbol) {
    Object pairs = exchange.getExchangeSpecification().getExchangeSpecificParametersItem("pairs");
    List<LatokenPair> allPairs = (List<LatokenPair>) pairs;
    if (allPairs == null) {
      throw new ExchangeException("'pairs' parameter must be set on exchange specifications");
    }
    Optional<LatokenPair> oPair =
        allPairs.stream().filter(pair -> pair.getSymbol().equals(latokenSymbol)).findAny();
    return oPair.isPresent() ? adaptCurrencyPair(oPair.get()) : null;
  }

  public static Balance adaptBalance(LatokenBalance latokenBalance) {
    return new Balance(
        Currency.getInstance(latokenBalance.getSymbol()),
        latokenBalance.getAmount(),
        latokenBalance.getAvailable(),
        latokenBalance.getFrozen(),
        BigDecimal.ZERO, // Borrowed
        BigDecimal.ZERO, // Loaned
        BigDecimal.ZERO, // Withdrawing
        latokenBalance.getPending() // Depositing
        );
  }

  public static Ticker adaptTicker(LatokenTicker latokenTicker) {
    return new Ticker.Builder()
        .open(latokenTicker.getOpen())
        .last(latokenTicker.getClose())
        .low(latokenTicker.getLow())
        .high(latokenTicker.getHigh())
        .volume(latokenTicker.getVolume())
        .build();
  }

  public static OrderBook adaptOrderBook(Exchange exchange, LatokenOrderbook latokenOrderbook) {
    CurrencyPair pair = adaptCurrencyPair(exchange, latokenOrderbook.getSymbol());
    List<LimitOrder> asks =
        latokenOrderbook.getAsks().stream()
            .map(
                level ->
                    new LimitOrder.Builder(OrderType.ASK, pair)
                        .originalAmount(level.getAmount())
                        .limitPrice(level.getPrice())
                        .build())
            .collect(Collectors.toList());
    List<LimitOrder> bids =
        latokenOrderbook.getBids().stream()
            .map(
                level ->
                    new LimitOrder.Builder(OrderType.BID, pair)
                        .originalAmount(level.getAmount())
                        .limitPrice(level.getPrice())
                        .build())
            .collect(Collectors.toList());

    return new OrderBook(null, asks, bids);
  }

  public static Trades adaptTrades(Exchange exchange, LatokenTrades latokenTrades) {
    CurrencyPair pair = adaptCurrencyPair(exchange, latokenTrades.getSymbol());
    List<Trade> trades =
        latokenTrades.getTrades().stream()
            .map(latokenTrade -> adaptTrade(latokenTrade, pair))
            .collect(Collectors.toList());

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptTrade(LatokenTrade latokenTrade, CurrencyPair pair) {
    return new Trade.Builder()
        .type(adaptOrderType(latokenTrade.getSide()))
        .currencyPair(pair)
        .originalAmount(latokenTrade.getAmount())
        .price(latokenTrade.getPrice())
        .timestamp(latokenTrade.getTimestamp())
        .build();
  }

  public static LimitOrder adaptOrder(Exchange exchange, LatokenOrder order) {
    OrderType type = adaptOrderType(order.getSide());
    CurrencyPair currencyPair = adaptCurrencyPair(exchange, order.getSymbol());
    OrderStatus orderStatus = adaptOrderStatus(order.getOrderStatus());

    return new LimitOrder.Builder(type, currencyPair)
        .originalAmount(order.getAmount())
        .id(order.getOrderId())
        .timestamp(order.getTimeCreated())
        .limitPrice(order.getPrice())
        .cumulativeAmount(order.getExecutedAmount())
        .fee(BigDecimal.ZERO)
        .orderStatus(orderStatus)
        .build();
  }

  public static OpenOrders adaptOpenOrders(
      Exchange exchange, List<LatokenOrder> latokenOpenOrders) {
    List<LimitOrder> openOrders =
        latokenOpenOrders.stream()
            .map(latokenOrder -> LatokenAdapters.adaptOrder(exchange, latokenOrder))
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

  public static UserTrades adaptUserTrades(Exchange exchange, LatokenUserTrades latokenUserTrades) {

    CurrencyPair pair = adaptCurrencyPair(exchange, latokenUserTrades.getSymbol());
    List<UserTrade> trades =
        latokenUserTrades.getTrades().stream()
            .map(latokenUserTrade -> adaptUserTrade(latokenUserTrade, pair))
            .collect(Collectors.toList());

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptUserTrade(LatokenUserTrade latokenUserTrade, CurrencyPair pair) {
    return new UserTrade.Builder()
        .type(adaptOrderType(latokenUserTrade.getSide()))
        .originalAmount(latokenUserTrade.getAmount())
        .currencyPair(pair)
        .price(latokenUserTrade.getPrice())
        .timestamp(latokenUserTrade.getTime())
        .id(latokenUserTrade.getId())
        .orderId(latokenUserTrade.getOrderId())
        .feeAmount(latokenUserTrade.getFee())
        .feeCurrency(pair.counter) // Fee is always in counter currency
        .build();
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
