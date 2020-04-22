package org.knowm.xchange.therock;

import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.utils.DateUtils.fromISODateString;

import java.math.BigDecimal;
import java.util.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.therock.dto.account.TheRockBalance;
import org.knowm.xchange.therock.dto.marketdata.TheRockBid;
import org.knowm.xchange.therock.dto.marketdata.TheRockOrderBook;
import org.knowm.xchange.therock.dto.marketdata.TheRockTrade;
import org.knowm.xchange.therock.dto.marketdata.TheRockTrade.Side;
import org.knowm.xchange.therock.dto.marketdata.TheRockTrades;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
import org.knowm.xchange.therock.dto.trade.TheRockOrders;
import org.knowm.xchange.therock.dto.trade.TheRockUserTrade;
import org.knowm.xchange.therock.dto.trade.TheRockUserTrades;

public final class TheRockAdapters {

  private TheRockAdapters() {}

  public static TheRockOrder.Side adaptSide(OrderType type) {
    return type == BID ? TheRockOrder.Side.buy : TheRockOrder.Side.sell;
  }

  public static AccountInfo adaptAccountInfo(List<TheRockBalance> trBalances, String userName) {
    ArrayList<Balance> balances = new ArrayList<>(trBalances.size());
    for (TheRockBalance blc : trBalances) {
      Currency currency = Currency.getInstance(blc.getCurrency());
      balances.add(new Balance(currency, blc.getBalance(), blc.getTradingBalance()));
    }

    return new AccountInfo(userName, Wallet.Builder.from(balances).id("spot").build());
  }

  public static OrderBook adaptOrderBook(TheRockOrderBook theRockOrderBook) {
    final List<LimitOrder> asks = new ArrayList<>();
    final List<LimitOrder> bids = new ArrayList<>();
    for (TheRockBid theRockBid : theRockOrderBook.getAsks()) {
      asks.add(
          adaptBid(
              theRockOrderBook.getCurrencyPair(), ASK, theRockBid, theRockOrderBook.getDate()));
    }
    for (TheRockBid theRockBid : theRockOrderBook.getBids()) {
      bids.add(
          adaptBid(
              theRockOrderBook.getCurrencyPair(), BID, theRockBid, theRockOrderBook.getDate()));
    }
    return new OrderBook(theRockOrderBook.getDate(), asks, bids);
  }

  private static LimitOrder adaptBid(
      CurrencyPair currencyPair, Order.OrderType orderType, TheRockBid theRockBid, Date timestamp) {
    return new LimitOrder.Builder(orderType, currencyPair)
        .limitPrice(theRockBid.getPrice())
        .originalAmount(theRockBid.getAmount())
        .timestamp(timestamp)
        .build();
  }

  public static Trades adaptTrades(TheRockTrades trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>(trades.getCount());
    long lastTradeId = 0;
    for (int i = 0; i < trades.getCount(); i++) {
      TheRockTrade trade = trades.getTrades()[i];
      if (trade.getSide() != Side.buy && trade.getSide() != Side.sell) {
        continue; // process buys and sells only
      }
      long tradeId = trade.getId();
      if (tradeId > lastTradeId) lastTradeId = tradeId;
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, Trades.TradeSortType.SortByID);
  }

  public static Trade adaptTrade(TheRockTrade trade, CurrencyPair currencyPair) {
    final String tradeId = String.valueOf(trade.getId());
    return new Trade.Builder()
        .type(trade.getSide() == Side.sell ? OrderType.ASK : BID)
        .originalAmount(trade.getAmount())
        .currencyPair(currencyPair)
        .price(trade.getPrice())
        .timestamp(trade.getDate())
        .id(tradeId)
        .build();
  }

  public static UserTrade adaptUserTrade(TheRockUserTrade trade, CurrencyPair currencyPair) {
    final String tradeId = String.valueOf(trade.getId());
    // return new UserTrade(trade.getSide() == Side.sell ? OrderType.ASK : BID, trade.getAmount(),
    // currencyPair, trade.getPrice(), trade.getDate(), tradeId);
    return new UserTrade.Builder()
        .id(tradeId)
        .originalAmount(trade.getAmount())
        .currencyPair(currencyPair)
        .price(trade.getPrice())
        .timestamp(trade.getDate())
        .orderId(String.valueOf(trade.getOrderId()))
        .type(trade.getSide() == Side.buy ? OrderType.BID : OrderType.ASK)
        .feeAmount(trade.getFeeAmount())
        .feeCurrency(
            trade.getFeeCurrency() == null ? null : Currency.getInstance(trade.getFeeCurrency()))
        .build();
  }

  public static UserTrades adaptUserTrades(TheRockUserTrades trades, CurrencyPair currencyPair) {

    List<UserTrade> tradesList = new ArrayList<>(trades.getCount());
    long lastTradeId = 0;
    for (int i = 0; i < trades.getCount(); i++) {
      TheRockUserTrade trade = trades.getTrades()[i];
      long tradeId = trade.getId();
      if (tradeId > lastTradeId) lastTradeId = tradeId;
      tradesList.add(adaptUserTrade(trade, currencyPair));
    }
    return new UserTrades(tradesList, lastTradeId, Trades.TradeSortType.SortByID);
  }

  public static LimitOrder adaptOrder(TheRockOrder order) {
    Date timestamp;
    try {
      timestamp = order.getDate() == null ? null : fromISODateString(order.getDate());
    } catch (Exception e) {
      timestamp = null;
    }
    BigDecimal amount = order.getAmount();
    BigDecimal unfilled = order.getAmountUnfilled();
    BigDecimal cumulative = (unfilled != null && amount != null) ? amount.subtract(unfilled) : null;

    return new LimitOrder(
        adaptOrderType(order.getSide()),
        order.getAmount(),
        order.getFundId().pair,
        Long.toString(order.getId()),
        timestamp,
        order.getPrice(), // limitPrice
        order.getPrice(), // averagePrice
        cumulative,
        null,
        adaptOrderStatus(order));
  }

  public static OrderType adaptOrderType(TheRockOrder.Side orderSide) {
    return orderSide.equals(TheRockOrder.Side.buy) ? OrderType.BID : OrderType.ASK;
  }

  public static OpenOrders adaptOrders(TheRockOrders theRockOrders) {
    List<LimitOrder> orders = new ArrayList<>(theRockOrders.getOrders().length);

    for (TheRockOrder theRockOrder : theRockOrders.getOrders()) {
      orders.add(adaptOrder(theRockOrder));
    }

    return new OpenOrders(orders);
  }

  /**
   * The status from the {@link TheRock} object converted to xchange status By the API documentation
   * available order states are the follow: (active|conditional|executed|deleted)
   */
  public static org.knowm.xchange.dto.Order.OrderStatus adaptOrderStatus(TheRockOrder order) {
    if ("active".equalsIgnoreCase(order.getStatus())) {
      return org.knowm.xchange.dto.Order.OrderStatus.NEW;
    } else if ("conditional".equalsIgnoreCase(order.getStatus())) {
      return org.knowm.xchange.dto.Order.OrderStatus.NEW;
    } else if ("executed".equalsIgnoreCase(order.getStatus())) {
      return org.knowm.xchange.dto.Order.OrderStatus.FILLED;
    } else if ("deleted".equalsIgnoreCase(order.getStatus())) {
      return org.knowm.xchange.dto.Order.OrderStatus.CANCELED;
    } else return org.knowm.xchange.dto.Order.OrderStatus.UNKNOWN;
  }

  /*
   * public static LimitOrder adaptOrder(TheRockOrder o) { return new LimitOrder(adaptOrderType(o.getSide()), o.getAmount(), o.getFundId().pair,
   * Long.toString(o.getId()), null, o.getPrice()); } public static Order.OrderType adaptOrderType(TheRockOrder.Side orderSide) { return
   * orderSide.equals(TheRockOrder.Side.buy) ? Order.OrderType.BID : Order.OrderType.ASK; } public static OrderBook adaptOrderBook(TheRockOrderBook
   * therockOrderBook) { List<LimitOrder> asks = new ArrayList<LimitOrder>(); List<LimitOrder> bids = new ArrayList<LimitOrder>(); for
   * (TheRockOrderBook.Entry obe : therockOrderBook.getData()) { if (TheRockOrder.Type.Buy.equals(obe.getType())) { bids.add(new
   * LimitOrder(Order.OrderType.BID, obe.getQuantity(), obe.getCurrencyPair(), null, obe.getCreated(), obe.getPrice())); } else { asks.add(new
   * LimitOrder(Order.OrderType.ASK, obe.getQuantity(), obe.getCurrencyPair(), null, obe.getCreated(), obe.getPrice())); } } Collections.sort(bids,
   * BID_COMPARATOR); Collections.sort(asks, ASK_COMPARATOR); return new OrderBook(new Date(), asks, bids); } public static UserTrades
   * adaptTradeHistory(TheRockUserTrade[] therockUserTrades) { List<UserTrade> trades = new ArrayList<UserTrade>(); long lastTradeId = 0; for
   * (TheRockUserTrade therockUserTrade : therockUserTrades) { lastTradeId = Math.max(lastTradeId, therockUserTrade.getTradeId());
   * trades.add(adaptTrade(therockUserTrade)); } return new UserTrades(trades, lastTradeId, TradeSortType.SortByID); } public static UserTrade
   * adaptTrade(TheRockUserTrade therockUserTrade) { CurrencyPair currencyPair = therockUserTrade.getCurrencyPair(); return new UserTrade(
   * adaptOrderType(therockUserTrade.getType()), therockUserTrade.getQuantity(), currencyPair, therockUserTrade.getPrice().abs(),
   * therockUserTrade.getExecuted(), String.valueOf(therockUserTrade.getTradeId()), String.valueOf(therockUserTrade.getOrderId()),
   * therockUserTrade.getFee(), therockUserTrade.getType() == TheRockOrder.Type.Buy ? currencyPair.counter.getCurrencyCode() :
   * currencyPair.base.getCurrencyCode()); }
   */
}
