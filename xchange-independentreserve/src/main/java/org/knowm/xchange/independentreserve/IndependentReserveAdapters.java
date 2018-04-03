package org.knowm.xchange.independentreserve;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
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
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTrade;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTradeHistoryResponse;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class IndependentReserveAdapters {

  private IndependentReserveAdapters() {}

  public static OrderBook adaptOrderBook(IndependentReserveOrderBook independentReserveOrderBook) {

    // reverse mapping Xbt (Independent Reserve) to BTC (XChange)
    String base = independentReserveOrderBook.getPrimaryCurrencyCode();

    if (base.equals("Xbt")) {
      base = "BTC";
    }

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
          new LimitOrder(type, obo.getVolume(), currencyPair, null, null, obo.getPrice());
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
    return new Wallet(balances);
  }

  public static OpenOrders adaptOpenOrders(
      IndependentReserveOpenOrdersResponse independentReserveOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<IndependentReserveOpenOrder> independentReserveOrdersList =
        independentReserveOrders.getIndependentReserveOrders();
    for (IndependentReserveOpenOrder order : independentReserveOrdersList) {
      String orderType = order.getOrderType();
      Order.OrderType type;

      if (orderType.equals("LimitOffer")) {
        type = Order.OrderType.ASK;
      } else if (orderType.equals("LimitBid")) {
        type = Order.OrderType.BID;
      } else {
        throw new IllegalStateException("Unknown order found in Independent Reserve");
      }

      // getting valid order currency pair
      String primaryAlias = order.getPrimaryCurrencyCode();
      if (primaryAlias.equals("Xbt")) {
        primaryAlias = "BTC";
      }

      Currency primary = Currency.getInstanceNoCreate(primaryAlias);
      Currency secondary = Currency.getInstanceNoCreate(order.getSecondaryCurrencyCode());
      CurrencyPair currencyPair = new CurrencyPair(primary, secondary);

      LimitOrder limitOrder =
          new LimitOrder(
              type,
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
      Order.OrderType type;
      String orderType = trade.getOrderType();
      if (orderType.equals("LimitOffer") || orderType.equals("MarketOffer")) {
        type = Order.OrderType.ASK;
      } else if (orderType.equals("LimitBid") || orderType.equals("MarketBid")) {
        type = Order.OrderType.BID;
      } else {
        throw new IllegalStateException("Unknown order found in Independent Reserve");
      }

      String primaryAlias = trade.getPrimaryCurrencyCode();
      if (primaryAlias.equals("Xbt")) {
        primaryAlias = "BTC";
      }

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
          new UserTrade(
              type,
              trade.getVolumeTraded(),
              currencyPair,
              trade.getPrice(),
              trade.getTradeTimestamp(),
              trade.getTradeGuid(),
              trade.getOrderGuid(),
              null,
              (Currency) null);

      userTrades.add(ut);
    }
    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }
}
