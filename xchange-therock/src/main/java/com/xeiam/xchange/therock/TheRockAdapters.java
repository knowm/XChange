package com.xeiam.xchange.therock;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.therock.dto.account.TheRockBalance;
import com.xeiam.xchange.therock.dto.trade.TheRockOrder;

public final class TheRockAdapters {

  private TheRockAdapters() {
  }

  public static TheRockOrder.Side adaptSide(Order.OrderType type) {
    return type == BID ? TheRockOrder.Side.buy : TheRockOrder.Side.sell;
  }

  public static AccountInfo adaptAccountInfo(List<TheRockBalance> trBalances, String userName) {

    ArrayList<Balance> balances = new ArrayList<Balance>(trBalances.size());
    for (TheRockBalance blc : trBalances) {
      Currency currency = Currency.getInstance(blc.getCurrency());
      balances.add(new Balance(currency, blc.getBalance(), blc.getTradingBalance()));
    }
    return new AccountInfo(userName, new Wallet(balances));
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
   * therockUserTrade.getFee(), therockUserTrade.getType() == TheRockOrder.Type.Buy ? currencyPair.counter.getCurrencyCode() : currencyPair.base.getCurrencyCode()); }
   */
}
