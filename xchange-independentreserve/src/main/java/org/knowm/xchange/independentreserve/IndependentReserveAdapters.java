package org.knowm.xchange.independentreserve;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveAccount;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBalance;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveOrderBook;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveTicker;
import org.knowm.xchange.independentreserve.dto.marketdata.OrderBookOrder;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOpenOrder;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOpenOrdersResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOrderDetailsResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTrade;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTradeHistoryResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTransaction;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class IndependentReserveAdapters {

  private IndependentReserveAdapters() {}

  private static Order.OrderType adapeOrderType(String orderType) {
    switch (orderType) {
      case "LimitOffer":
      case "MarketOffer":
        return Order.OrderType.ASK;
      case "LimitBid":
      case "MarketBid":
        return Order.OrderType.BID;
      default:
        throw new IllegalStateException(
            "Unknown order found in Independent Reserve : " + orderType);
    }
  }

  private static Order.OrderStatus adaptOrderStatus(String orderStatus) {
    switch (orderStatus) {
      case "Open":
        return Order.OrderStatus.NEW;
      case "PartiallyFilled":
        return Order.OrderStatus.PARTIALLY_FILLED;
      case "Filled":
        return Order.OrderStatus.FILLED;
      case "PartiallyFilledAndCancelled":
        return Order.OrderStatus.PARTIALLY_CANCELED;
      case "Cancelled":
        return Order.OrderStatus.CANCELED;
      case "PartiallyFilledAndExpired":
        return Order.OrderStatus.EXPIRED;
      case "Expired":
        return Order.OrderStatus.EXPIRED;
      default:
        throw new IllegalStateException(
            "Unknown status found in Independent Reserve : " + orderStatus);
    }
  }

  public static OrderBook adaptOrderBook(IndependentReserveOrderBook independentReserveOrderBook) {

    String base = independentReserveOrderBook.getPrimaryCurrencyCode();

    CurrencyPair currencyPair =
        new CurrencyPair(base, independentReserveOrderBook.getSecondaryCurrencyCode());

    List<LimitOrder> bids =
        adaptOrders(independentReserveOrderBook.getBuyOrders(), Order.OrderType.BID, currencyPair);
    List<LimitOrder> asks =
        adaptOrders(independentReserveOrderBook.getSellOrders(), Order.OrderType.ASK, currencyPair);
    Date timestamp = independentReserveOrderBook.getCreatedTimestamp();

    return new OrderBook(timestamp, asks, bids);
  }

  /**
   * Adapts a IndependentReserveTicker to a Ticker Object
   *
   * @param independentReserveTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(
      IndependentReserveTicker independentReserveTicker, CurrencyPair currencyPair) {

    BigDecimal last = independentReserveTicker.getLast();
    BigDecimal bid = independentReserveTicker.getBid();
    BigDecimal ask = independentReserveTicker.getAsk();
    BigDecimal high = independentReserveTicker.getHigh();
    BigDecimal low = independentReserveTicker.getLow();
    BigDecimal vwap = independentReserveTicker.getVwap();
    BigDecimal volume = independentReserveTicker.getVolume();
    Date timestamp = independentReserveTicker.getTimestamp();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .vwap(vwap)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  private static List<LimitOrder> adaptOrders(
      List<OrderBookOrder> buyOrders, Order.OrderType type, CurrencyPair currencyPair) {
    final List<LimitOrder> orders = new ArrayList<>();
    for (OrderBookOrder obo : buyOrders) {
      LimitOrder limitOrder =
          new LimitOrder(type, obo.getVolume(), currencyPair, obo.getGuid(), null, obo.getPrice());
      orders.add(limitOrder);
    }
    return orders;
  }

  public static Wallet adaptWallet(IndependentReserveBalance independentReserveBalance) {
    List<Balance> balances = new ArrayList<>();
    for (IndependentReserveAccount balanceAccount :
        independentReserveBalance.getIndependentReserveAccounts()) {
      Currency currency = Currency.getInstance(balanceAccount.getCurrencyCode().toUpperCase());
      balances.add(
          new Balance(
              currency.getCommonlyUsedCurrency(),
              balanceAccount.getTotalBalance(),
              balanceAccount.getAvailableBalance()));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static OpenOrders adaptOpenOrders(
      IndependentReserveOpenOrdersResponse independentReserveOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<IndependentReserveOpenOrder> independentReserveOrdersList =
        independentReserveOrders.getIndependentReserveOrders();
    for (IndependentReserveOpenOrder order : independentReserveOrdersList) {

      // getting valid order currency pair
      String primaryAlias = order.getPrimaryCurrencyCode();

      Currency primary = Currency.getInstanceNoCreate(primaryAlias);
      Currency secondary = Currency.getInstanceNoCreate(order.getSecondaryCurrencyCode());
      CurrencyPair currencyPair = new CurrencyPair(primary, secondary);

      LimitOrder limitOrder =
          new LimitOrder(
              adapeOrderType(order.getOrderType()),
              order.getOutstanding(),
              currencyPair,
              order.getOrderGuid(),
              order.getCreatedTimestamp(),
              order.getPrice());
      limitOrders.add(limitOrder);
    }
    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradeHistory(
      IndependentReserveTradeHistoryResponse independentReserveTradeHistoryResponse) {
    List<UserTrade> userTrades = new ArrayList<>();
    for (IndependentReserveTrade trade :
        independentReserveTradeHistoryResponse.getIndependentReserveTrades()) {

      String primaryAlias = trade.getPrimaryCurrencyCode();

      Currency primary = Currency.getInstanceNoCreate(primaryAlias);
      Currency secondary = Currency.getInstanceNoCreate(trade.getSecondaryCurrencyCode());

      if (primary == null || secondary == null) {
        throw new IllegalArgumentException(
            "IndependentReserveTradeHistoryRequest - unknown value of currency code. Base was: "
                + trade.getPrimaryCurrencyCode()
                + " counter was "
                + trade.getSecondaryCurrencyCode());
      }

      CurrencyPair currencyPair = new CurrencyPair(primary, secondary);

      UserTrade ut =
          new UserTrade.Builder()
              .type(adapeOrderType(trade.getOrderType()))
              .originalAmount(trade.getVolumeTraded())
              .currencyPair(currencyPair)
              .price(trade.getPrice())
              .timestamp(trade.getTradeTimestamp())
              .id(trade.getTradeGuid())
              .orderId(trade.getOrderGuid())
              .build();

      userTrades.add(ut);
    }
    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }

  public static Order adaptOrderDetails(IndependentReserveOrderDetailsResponse details) {
    if (details.getOrderType().startsWith("Market")) {
      return new MarketOrder(
          adapeOrderType(details.getOrderType()),
          details.getVolumeOrdered(),
          new CurrencyPair(details.getPrimaryCurrencyCode(), details.getSecondaryCurrencyCode()),
          details.getOrderGuid(),
          details.getCreatedTimestamp(),
          details.getAvgPrice(),
          details.getVolumeFilled(),
          null,
          adaptOrderStatus(details.getStatus()));
    } else if (details.getOrderType().startsWith("Limit")) {
      return new LimitOrder(
          adapeOrderType(details.getOrderType()),
          details.getVolumeOrdered(),
          new CurrencyPair(details.getPrimaryCurrencyCode(), details.getSecondaryCurrencyCode()),
          details.getOrderGuid(),
          details.getCreatedTimestamp(),
          details.getAvgPrice(),
          details.getAvgPrice(),
          details.getVolumeFilled(),
          null,
          adaptOrderStatus(details.getStatus()));
    }
    throw new IllegalStateException(
        "Unknown order type found in Independent Reserve : " + details.getOrderType());
  }

  public static FundingRecord.Status adaptTransactionStatusToFundingRecordStatus(String status) {
    switch (status) {
      case "Open":
      case "PartiallyFilled":
      case "Unconfirmed":
        return Status.PROCESSING;
      case "Filled":
      case "Confirmed":
        return Status.COMPLETE;
      case "Rejected":
        return Status.FAILED;
      default:
        return null;
    }
  }

  public static FundingRecord.Type adaptTransactionTypeToFundingRecordType(
      IndependentReserveTransaction.Type transctionType) {
    switch (transctionType) {
      case Withdrawal:
        return FundingRecord.Type.WITHDRAWAL;
      case Deposit:
        return FundingRecord.Type.DEPOSIT;
      default:
        return null;
    }
  }

  public static FundingRecord adaptTransaction(IndependentReserveTransaction transaction) {
    BigDecimal amount = null;
    if (transaction.getDebit() != null) {
      amount = transaction.getDebit();
    } else if (transaction.getCredit() != null) {
      amount = transaction.getCredit();
    }
    return new FundingRecord(
        null,
        transaction.getCreatedTimestamp(),
        new Currency(transaction.getCurrencyCode()),
        amount,
        null,
        transaction.getBitcoinTransactionId(),
        adaptTransactionTypeToFundingRecordType(transaction.getType()),
        adaptTransactionStatusToFundingRecordStatus(transaction.getStatus()),
        transaction.getBalance(),
        null,
        transaction.getComment());
  }
}
