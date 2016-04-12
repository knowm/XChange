package org.knowm.xchange.loyalbit;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.loyalbit.dto.account.LoyalbitBalance;
import org.knowm.xchange.loyalbit.dto.marketdata.LoyalbitOrderBook;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitOrder;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitUserTransaction;

public final class LoyalbitAdapters {

  public static final Comparator<LimitOrder> ASK_COMPARATOR = new Comparator<LimitOrder>() {
    @Override
    public int compare(LimitOrder o1, LimitOrder o2) {
      return o1.getLimitPrice().compareTo(o2.getLimitPrice());
    }
  };
  public static final Comparator<LimitOrder> BID_COMPARATOR = new Comparator<LimitOrder>() {
    @Override
    public int compare(LimitOrder o1, LimitOrder o2) {
      return o2.getLimitPrice().compareTo(o1.getLimitPrice());
    }
  };

  private LoyalbitAdapters() {
  }

  public static AccountInfo adaptAccountInfo(LoyalbitBalance loyalbitBalance, String userName) {

    Balance usdBalance = new Balance.Builder().currency(Currency.USD).available(loyalbitBalance.getAvailableUsd())
        .frozen(loyalbitBalance.getReservedOrderUsd()).withdrawing(loyalbitBalance.getReservedWithdrawUsd()).build();
    Balance btcBalance = new Balance.Builder().currency(Currency.BTC).available(loyalbitBalance.getAvailableBtc())
        .frozen(loyalbitBalance.getReservedOrderBtc()).withdrawing(loyalbitBalance.getReservedWithdrawBtc()).build();

    return new AccountInfo(userName, loyalbitBalance.getFee(), new Wallet(usdBalance, btcBalance));
  }

  public static OrderBook adaptOrderBook(LoyalbitOrderBook loyalbitOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, loyalbitOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, loyalbitOrderBook.getBids());
    return new OrderBook(new Date(), asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    Collections.sort(limitOrders, orderType.equals(Order.OrderType.ASK) ? ASK_COMPARATOR : BID_COMPARATOR);
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {
    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static UserTrades adaptTradeHistory(LoyalbitUserTransaction[] loyalbitUserTransactions) {
    List<UserTrade> trades = new ArrayList<UserTrade>();
    long lastTradeId = 0;
    for (LoyalbitUserTransaction loyalbitUserTransaction : loyalbitUserTransactions) {
      Order.OrderType orderType = adaptOrderType(loyalbitUserTransaction.getType());
      BigDecimal tradableAmount = loyalbitUserTransaction.getAmount();
      BigDecimal price = loyalbitUserTransaction.getPrice().abs();
      Date timestamp = loyalbitUserTransaction.getMicrotime();
      long transactionId = loyalbitUserTransaction.getId();
      if (transactionId > lastTradeId) {
        lastTradeId = transactionId;
      }
      final String tradeId = String.valueOf(transactionId);
      final String orderId = String.valueOf(loyalbitUserTransaction.getOrderId());
      final BigDecimal feeAmount = loyalbitUserTransaction.getFeeUSD();
      final CurrencyPair currencyPair = CurrencyPair.BTC_USD;

      UserTrade trade = new UserTrade(orderType, tradableAmount, currencyPair, price, timestamp, tradeId, orderId, feeAmount,
          Currency.getInstance(currencyPair.counter.getCurrencyCode()));
      trades.add(trade);
    }

    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static Order.OrderType adaptOrderType(LoyalbitOrder.Type orderType) {
    return orderType.equals(LoyalbitOrder.Type.bid) ? Order.OrderType.BID : Order.OrderType.ASK;
  }

  public static LimitOrder adaptOrder(LoyalbitOrder o) {
    return new LimitOrder(adaptOrderType(o.getType()), o.getAmount(), CurrencyPair.BTC_USD, Long.toString(o.getId()), o.getMicrotime(), o.getPrice());
  }
}
