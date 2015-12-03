package com.xeiam.xchange.independentreserve;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.independentreserve.dto.account.IndependentReserveAccount;
import com.xeiam.xchange.independentreserve.dto.account.IndependentReserveBalance;
import com.xeiam.xchange.independentreserve.dto.marketdata.IndependentReserveOrderBook;
import com.xeiam.xchange.independentreserve.dto.marketdata.OrderBookOrder;
import com.xeiam.xchange.independentreserve.dto.trade.IndependentReserveOpenOrder;
import com.xeiam.xchange.independentreserve.dto.trade.IndependentReserveOpenOrdersResponse;
import com.xeiam.xchange.independentreserve.dto.trade.IndependentReserveTrade;
import com.xeiam.xchange.independentreserve.dto.trade.IndependentReserveTradeHistoryResponse;

/**
 * Author: Kamil Zbikowski Date: 4/10/15
 */
public class IndependentReserveAdapters {

  private IndependentReserveAdapters() {
  }

  public static OrderBook adaptOrderBook(IndependentReserveOrderBook independentReserveOrderBook) {
    List<LimitOrder> bids = adaptOrders(independentReserveOrderBook.getBuyOrders(), Order.OrderType.BID,
        new CurrencyPair(independentReserveOrderBook.getPrimaryCurrencyCode(), independentReserveOrderBook.getSecondaryCurrencyCode()));
    List<LimitOrder> asks = adaptOrders(independentReserveOrderBook.getSellOrders(), Order.OrderType.ASK,
        new CurrencyPair(independentReserveOrderBook.getPrimaryCurrencyCode(), independentReserveOrderBook.getSecondaryCurrencyCode()));
    Date timestamp = new Date(independentReserveOrderBook.getCreatedTimestampUtc());

    return new OrderBook(timestamp, asks, bids);
  }

  private static List<LimitOrder> adaptOrders(List<OrderBookOrder> buyOrders, Order.OrderType type, CurrencyPair currencyPair) {
    final List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (OrderBookOrder obo : buyOrders) {
      LimitOrder limitOrder = new LimitOrder(type, obo.getVolume(), currencyPair, null, null, obo.getPrice());
      orders.add(limitOrder);
    }
    return orders;
  }

  public static Wallet adaptWallet(IndependentReserveBalance independentReserveBalance) {
    List<Balance> balances = new ArrayList<Balance>();

    for (IndependentReserveAccount balanceAccount : independentReserveBalance.getIndependentReserveAccounts()) {
      Currency currency = Currency.getInstance(balanceAccount.getCurrencyCode().toUpperCase());
      balances.add(new Balance(currency.getCommonlyUsedCurrency(), balanceAccount.getTotalBalance()));
    }
    return new Wallet(balances);
  }

  public static OpenOrders adaptOpenOrders(IndependentReserveOpenOrdersResponse independentReserveOrders) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    List<IndependentReserveOpenOrder> independentReserveOrdersList = independentReserveOrders.getIndependentReserveOrders();
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
      LimitOrder limitOrder = new LimitOrder(type, order.getOutstanding(), CurrencyPair.BTC_USD, order.getOrderGuid(), order.getCreatedTimestampUtc(),
          order.getPrice());
      limitOrders.add(limitOrder);
    }
    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradeHistory(IndependentReserveTradeHistoryResponse independentReserveTradeHistoryResponse) {
    List<UserTrade> userTrades = new ArrayList<UserTrade>();
    for (IndependentReserveTrade trade : independentReserveTradeHistoryResponse.getIndependentReserveTrades()) {
      Order.OrderType type;
      String orderType = trade.getOrderType();
      if (orderType.equals("LimitOffer") || orderType.equals("MarketOffer")) {
        type = Order.OrderType.ASK;
      } else if (orderType.equals("LimitBid") || orderType.equals("MarketBid")) {
        type = Order.OrderType.BID;
      } else {
        throw new IllegalStateException("Unknown order found in Independent Reserve");
      }

      CurrencyPair currencyPair = CurrencyPair.BTC_USD;

      if (!trade.getPrimaryCurrencyCode().equals("Xbt") || !trade.getSecondaryCurrencyCode().equals("Usd")) {
        throw new IllegalArgumentException("IndependentReserveTradeHistoryRequest - unknown value of currency code. Base was: "
            + trade.getPrimaryCurrencyCode() + " counter was " + trade.getSecondaryCurrencyCode());
      }

      UserTrade ut = new UserTrade(type, trade.getVolumeTraded(), currencyPair, trade.getPrice(), trade.getTradeTimestampUtc(), trade.getTradeGuid(),
          trade.getOrderGuid(), null, (Currency)null);

      userTrades.add(ut);
    }
    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }
}
