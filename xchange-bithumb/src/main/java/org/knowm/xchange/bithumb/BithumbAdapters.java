package org.knowm.xchange.bithumb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bithumb.dto.account.BithumbAccount;
import org.knowm.xchange.bithumb.dto.account.BithumbBalance;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.account.BithumbTransaction;
import org.knowm.xchange.bithumb.dto.marketdata.*;
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
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public final class BithumbAdapters {

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
      BigDecimal limitPric) {
    return new LimitOrder(orderType, originalAmount, currencyPair, "", null, limitPric);
  }

  public static AccountInfo adaptAccountInfo(BithumbAccount account, BithumbBalance balance) {

    List<Balance> balances = new ArrayList<>();
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

  public static Trades adaptTrades(
      List<BithumbTransactionHistory> bithumbTrades, CurrencyPair currencyPair) {
    final List<Trade> trades =
        bithumbTrades.stream()
            .map(trade -> adaptTrade(trade, currencyPair))
            .collect(Collectors.toList());
    return new Trades(trades);
  }

  public static Trade adaptTrade(BithumbTransactionHistory trade, CurrencyPair currencyPair) {

    return new Trade.Builder()
        .currencyPair(currencyPair)
        .id(String.valueOf(trade.getContNo()))
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
        new CurrencyPair(order.getOrderCurrency(), order.getPaymentCurrency());
    final Order.OrderType orderType = adaptOrderType(order.getType());

    return new LimitOrder.Builder(orderType, currencyPair)
        .id(String.valueOf(order.getOrderId()))
        .limitPrice(order.getPrice())
        .originalAmount(order.getUnits())
        .remainingAmount(order.getUnitsRemaining())
        .orderStatus(
            StringUtils.equals(order.getStatus(), "placed")
                ? Order.OrderStatus.NEW
                : Order.OrderStatus.UNKNOWN)
        .timestamp(new Date(order.getOrderDate() / 1000L))
        .build();
  }

  public static UserTrades adaptUserTrades(
      List<BithumbTransaction> transactions, CurrencyPair currencyPair) {
    final List<UserTrade> userTrades =
        transactions.stream()
            .filter(BithumbTransaction::isBuyOrSell)
            .map(transaction -> adaptUserTrade(transaction, currencyPair))
            .collect(Collectors.toList());
    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }

  private static UserTrade adaptUserTrade(
      BithumbTransaction bithumbTransaction, CurrencyPair currencyPair) {

    final String units = StringUtils.remove(bithumbTransaction.getUnits(), ' ');
    return new UserTrade.Builder()
        .currencyPair(currencyPair)
        .originalAmount(new BigDecimal(units).abs())
        .type(adaptTransactionSearch(bithumbTransaction.getSearch()))
        .feeAmount(bithumbTransaction.getFee())
        .feeCurrency(currencyPair.counter)
        .price(bithumbTransaction.getPrice())
        .timestamp(new Date(bithumbTransaction.getTransferDate()))
        .build();
  }

  private static Order.OrderType adaptTransactionSearch(String search) {
    switch (search) {
      case BithumbTransaction.SEARCH_BUY:
        return Order.OrderType.BID;
      case BithumbTransaction.SEARCH_SELL:
        return Order.OrderType.ASK;
      default:
        return null;
    }
  }

  public enum OrderType {
    bid,
    ask
  }
}
