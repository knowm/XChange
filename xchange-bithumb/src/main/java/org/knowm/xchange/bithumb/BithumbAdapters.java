package org.knowm.xchange.bithumb;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bithumb.dto.account.BithumbAccount;
import org.knowm.xchange.bithumb.dto.account.BithumbBalance;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.account.BithumbOrderDetail;
import org.knowm.xchange.bithumb.dto.marketdata.*;
import org.knowm.xchange.bithumb.dto.trade.BithumbUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;

public final class BithumbAdapters {
  public static final String SEARCH_BUY = "1";
  public static final String SEARCH_SELL = "2";

  /** private Constructor */
  private BithumbAdapters() {}

  public static OrderBook adaptOrderBook(BithumbOrderbook orderbook) {

    final CurrencyPair currencyPair =
        new CurrencyPair(orderbook.getOrderCurrency(), orderbook.getPaymentCurrency());

    return new OrderBook(
        new Date(orderbook.getTimestamp()),
        createOrder(currencyPair, orderbook.getAsks(), Order.OrderType.ASK),
        createOrder(currencyPair, orderbook.getBids(), Order.OrderType.BID));
  }

  private static List<LimitOrder> createOrder(
      CurrencyPair currencyPair, List<BithumbOrderbookEntry> orders, Order.OrderType orderType) {
    return orders.stream()
        .map(order -> createOrder(currencyPair, orderType, order.getQuantity(), order.getPrice()))
        .collect(Collectors.toList());
  }

  private static LimitOrder createOrder(
      CurrencyPair currencyPair,
      Order.OrderType orderType,
      BigDecimal originalAmount,
      BigDecimal limitPrice) {
    return new LimitOrder(orderType, originalAmount, currencyPair, "", null, limitPrice);
  }

  public static AccountInfo adaptAccountInfo(BithumbAccount account, BithumbBalance balance) {

    List<Balance> balances = new ArrayList<>();
    balances.add(
        new Balance(
            Currency.KRW, balance.getTotalKrw(), balance.getAvailableKrw(), balance.getInUseKrw()));

    for (String currency : balance.getCurrencies()) {
      final Balance xchangeBalance =
          new Balance(
              Currency.getInstance(currency),
              balance.getTotal(currency),
              balance.getAvailable(currency),
              balance.getFrozen(currency));
      balances.add(xchangeBalance);
    }
    return new AccountInfo(null, account.getTradeFee(), Wallet.Builder.from(balances).build());
  }

  public static Ticker adaptTicker(BithumbTicker bithumbTicker, CurrencyPair currencyPair) {
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .ask(bithumbTicker.getSellPrice())
        .bid(bithumbTicker.getBuyPrice())
        .high(bithumbTicker.getMaxPrice())
        .low(bithumbTicker.getMinPrice())
        .last(bithumbTicker.getClosingPrice())
        .open(bithumbTicker.getOpeningPrice())
        .vwap(bithumbTicker.getAveragePrice())
        .volume(bithumbTicker.getUnitsTraded())
        .timestamp(new Date(bithumbTicker.getDate()))
        .build();
  }

  public static List<Ticker> adaptTickers(BithumbTickersReturn bithumbTickers) {
    return bithumbTickers.getTickers().entrySet().stream()
        .map(
            tickerEntry -> {
              final CurrencyPair currencyPair =
                  new CurrencyPair(tickerEntry.getKey(), Currency.KRW.getCurrencyCode());
              return adaptTicker(tickerEntry.getValue(), currencyPair);
            })
        .collect(Collectors.toList());
  }

  public static Trades adaptTransactions(
      List<BithumbTransactionHistory> bithumbTrades, CurrencyPair currencyPair) {
    final List<Trade> trades =
        bithumbTrades.stream()
            .map(trade -> adaptTransactionHistory(trade, currencyPair))
            .collect(Collectors.toList());
    return new Trades(trades);
  }

  public static Trade adaptTransactionHistory(
      BithumbTransactionHistory trade, CurrencyPair currencyPair) {

    return new Trade.Builder()
        .currencyPair(currencyPair)
        .originalAmount(trade.getUnitsTraded())
        .price(trade.getPrice())
        .type(adaptOrderType(trade.getType()))
        .timestamp(trade.getTimestamp())
        .build();
  }

  private static Order.OrderType adaptOrderType(OrderType orderType) {
    return orderType == OrderType.bid ? Order.OrderType.BID : Order.OrderType.ASK;
  }

  public static OpenOrders adaptOrders(List<BithumbOrder> bithumbOrders) {
    final List<LimitOrder> orders =
        bithumbOrders.stream().map(BithumbAdapters::adaptOrder).collect(Collectors.toList());
    return new OpenOrders(orders);
  }

  public static LimitOrder adaptOrder(BithumbOrder order) {
    final CurrencyPair currencyPair =
        adaptCurrencyPair(order.getOrderCurrency(), order.getPaymentCurrency());
    final Order.OrderType orderType = adaptOrderType(order.getType());

    Order.OrderStatus status = Order.OrderStatus.UNKNOWN;
    if (order.getUnitsRemaining().compareTo(order.getUnits()) == 0) {
      status = Order.OrderStatus.NEW;
    } else if (order.getUnitsRemaining().compareTo(BigDecimal.ZERO) == 0) {
      status = Order.OrderStatus.FILLED;
    } else {
      status = Order.OrderStatus.PARTIALLY_FILLED;
    }

    return new LimitOrder.Builder(orderType, currencyPair)
        .id(String.valueOf(order.getOrderId()))
        .limitPrice(order.getPrice())
        .originalAmount(order.getUnits())
        .remainingAmount(order.getUnitsRemaining())
        .orderStatus(status)
        .timestamp(new Date(order.getOrderDate() / 1000L))
        .build();
  }

  public static UserTrades adaptUserTrades(
      List<BithumbUserTransaction> transactions, CurrencyPair currencyPair) {
    final List<UserTrade> userTrades =
        transactions.stream()
            .filter(BithumbUserTransaction::isBuyOrSell)
            .map(transaction -> adaptUserTrade(transaction, currencyPair))
            .collect(Collectors.toList());
    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }

  private static UserTrade adaptUserTrade(
      BithumbUserTransaction bithumbTransaction, CurrencyPair currencyPair) {

    return new UserTrade.Builder()
        .currencyPair(currencyPair)
        .originalAmount(bithumbTransaction.getUnits())
        .type(adaptTransactionSearch(bithumbTransaction.getSearch()))
        .feeAmount(bithumbTransaction.getFee())
        .feeCurrency(currencyPair.counter)
        .price(bithumbTransaction.getPrice())
        .timestamp(new Date(bithumbTransaction.getTransferDate() / 1000L))
        .build();
  }

  public static Order adaptOrderDetail(BithumbOrderDetail order, String id) {
    final CurrencyPair currencyPair =
        adaptCurrencyPair(order.getOrderCurrency(), order.getPaymentCurrency());
    final Order.OrderType orderType = adaptOrderType(order.getType());
    Order.OrderStatus status = adaptStatus(order.getOrderStatus());

    BigDecimal fees =
        order.getContract().stream()
            .map(BithumbOrderDetail.Contract::getFee)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal cumulative =
        order.getContract().stream()
            .map(BithumbOrderDetail.Contract::getUnits)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal averagePrice =
        cumulative.compareTo(BigDecimal.ZERO) != 0
            ? order.getContract().stream()
                .map(contract -> contract.getUnits().multiply(contract.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(cumulative, MathContext.DECIMAL32)
            : null;

    if (order.getOrderPrice() == null) {
      return new MarketOrder.Builder(orderType, currencyPair)
          .id(id)
          .orderStatus(status)
          .originalAmount(order.getOrderQty())
          .cumulativeAmount(cumulative)
          .averagePrice(averagePrice)
          .fee(fees)
          .build();
    } else {
      return new LimitOrder.Builder(orderType, currencyPair)
          .id(id)
          .limitPrice(order.getOrderPrice())
          .orderStatus(status)
          .originalAmount(order.getOrderQty())
          .cumulativeAmount(cumulative)
          .averagePrice(averagePrice)
          .fee(fees)
          .build();
    }
  }

  private static Order.OrderType adaptTransactionSearch(String search) {
    switch (search) {
      case SEARCH_BUY:
        return Order.OrderType.BID;
      case SEARCH_SELL:
        return Order.OrderType.ASK;
      default:
        return null;
    }
  }

  public enum OrderType {
    bid,
    ask
  }

  private static Order.OrderStatus adaptStatus(String orderStatus) {
    switch (orderStatus) {
      case "Pending":
        return Order.OrderStatus.NEW;
      case "Completed":
        return Order.OrderStatus.FILLED;
      case "P":
        return Order.OrderStatus.PARTIALLY_FILLED;
      case "Cancel":
        return Order.OrderStatus.CANCELED;
      default:
        return Order.OrderStatus.UNKNOWN;
    }
  }

  public static CurrencyPair adaptCurrencyPair(String orderCurrency, String paymentCurrency) {
    return new CurrencyPair(orderCurrency, paymentCurrency);
  }
}
