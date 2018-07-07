package org.knowm.xchange.coingi;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import org.knowm.xchange.coingi.dto.account.CoingiBalance;
import org.knowm.xchange.coingi.dto.account.CoingiBalances;
import org.knowm.xchange.coingi.dto.account.CoingiUserTransaction;
import org.knowm.xchange.coingi.dto.marketdata.CoingiOrderBook;
import org.knowm.xchange.coingi.dto.marketdata.CoingiOrderGroup;
import org.knowm.xchange.coingi.dto.trade.CoingiOrder;
import org.knowm.xchange.coingi.dto.trade.CoingiOrdersList;
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
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.utils.DateUtils;

/** Various adapters for converting from Coingi DTOs to XChange DTOs */
public final class CoingiAdapters {
  /** private Constructor */
  private CoingiAdapters() {}

  /**
   * Adapts a CoingiBalances to an AccountInfo
   *
   * @param coingiBalances The Coingi balance
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(CoingiBalances coingiBalances, String userName) {
    List<Balance> balances = new ArrayList<>();
    for (CoingiBalance coingiBalance : coingiBalances.getList()) {
      BigDecimal total =
          coingiBalance
              .getAvailable()
              .add(coingiBalance.getBlocked())
              .add(coingiBalance.getWithdrawing())
              .add(coingiBalance.getDeposited());
      Balance xchangeBalance =
          new Balance(
              Currency.getInstance(coingiBalance.getCurrency().getName().toUpperCase()),
              total, // total = available + frozen - borrowed + loaned + withdrawing + depositing
              coingiBalance.getAvailable(), // available
              coingiBalance.getBlocked(),
              BigDecimal.ZERO, // borrowed is always 0
              BigDecimal.ZERO, // loaned is always 0
              coingiBalance.getWithdrawing(),
              coingiBalance.getDeposited());

      balances.add(xchangeBalance);
    }

    return new AccountInfo(userName, new Wallet(balances));
  }

  public static OrderBook adaptOrderBook(CoingiOrderBook coingiOrderBook) {
    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();

    for (CoingiOrderGroup ask : coingiOrderBook.getAsks()) {
      List<BigDecimal> priceAndAmount = Arrays.asList(ask.getPrice(), ask.getBaseAmount());
      LimitOrder askLimit =
          createOrder(
              adaptCurrency(ask.getCurrencyPair()), priceAndAmount, adaptOrderType(ask.getType()));
      askLimit.setOrderStatus(adaptOrderStatus(0));
      asks.add(askLimit);
    }

    for (CoingiOrderGroup bid : coingiOrderBook.getBids()) {
      List<BigDecimal> priceAndAmount = Arrays.asList(bid.getPrice(), bid.getBaseAmount());
      LimitOrder bidLimit =
          createOrder(
              adaptCurrency(bid.getCurrencyPair()), priceAndAmount, adaptOrderType(bid.getType()));
      bidLimit.setOrderStatus(adaptOrderStatus(0));
      bids.add(bidLimit);
    }

    return new OrderBook(null, asks, bids);
  }

  public static OrderType adaptOrderType(int type) {
    return (type == 0) ? OrderType.BID : OrderType.ASK;
  }

  public static CurrencyPair adaptCurrency(Map<String, String> currencyPair) {
    String baseCurrency = currencyPair.get("base");
    String counterCurrency = currencyPair.get("counter");
    return new CurrencyPair(baseCurrency, counterCurrency);
  }

  public static String adaptCurrency(CurrencyPair pair) {
    return pair.toString().replace('/', '-').toLowerCase();
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, List<List<BigDecimal>> orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(
          ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }

    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, OrderType orderType) {
    return new LimitOrder(
        orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {
    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static OpenOrders adaptOpenOrders(CoingiOrdersList orders) {
    List<LimitOrder> list = new ArrayList<>();
    for (CoingiOrder order : orders) {
      LimitOrder limitOrder =
          new LimitOrder(
              order.getType() == 0 ? OrderType.BID : OrderType.ASK,
              order.getOriginalBaseAmount(),
              adaptCurrency(order.getCurrencyPair()),
              order.getId(),
              new Date(order.getTimestamp()),
              order.getPrice());
      limitOrder.setOrderStatus(adaptOrderStatus(order.getStatus()));
      list.add(limitOrder);
    }

    return new OpenOrders(list);
  }

  /**
   * Adapts a Transaction to a Trade Object
   *
   * @param tx The Coingi transaction
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange Trade
   */
  public static Trade adaptTrade(
      CoingiUserTransaction tx, CurrencyPair currencyPair, int timeScale) {

    OrderType orderType = tx.getType() == 0 ? OrderType.BID : OrderType.ASK;
    final String tradeId = tx.getId();
    Date date =
        DateUtils.fromMillisUtc(
            tx.getTimestamp()
                * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new Trade(orderType, tx.getBaseAmount(), currencyPair, tx.getPrice(), date, tradeId);
  }

  public static Trade adaptTrade(
      org.knowm.xchange.coingi.dto.marketdata.CoingiTransaction tx,
      CurrencyPair currencyPair,
      int timeScale) {
    OrderType orderType = tx.getType() == 0 ? OrderType.BID : OrderType.ASK;
    final String tradeId = tx.getId();
    Date date = new Date(tx.getTimestamp());
    return new Trade(orderType, tx.getAmount(), currencyPair, tx.getPrice(), date, tradeId);
  }

  public static UserTrades adaptTradeHistory(CoingiOrdersList ordersList) {
    List<UserTrade> trades = new ArrayList<>();
    for (CoingiOrder o : ordersList.getList()) {
      final OrderType orderType = o.getType() == 0 ? OrderType.BID : OrderType.ASK;

      final CurrencyPair pair =
          new CurrencyPair(
              o.getCurrencyPair().get("base").toUpperCase(),
              o.getCurrencyPair().get("counter").toUpperCase());

      UserTrade trade =
          new UserTrade(
              orderType,
              o.getOriginalBaseAmount(),
              pair,
              o.getPrice(),
              new Date(o.getTimestamp()),
              o.getId(),
              o.getId(),
              BigDecimal.valueOf(0),
              null);

      trades.add(trade);
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static Trades adaptTrades(
      List<org.knowm.xchange.coingi.dto.marketdata.CoingiTransaction> coingiTransactions,
      CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>();
    long lastTradeId = System.currentTimeMillis() / 1000;

    for (int i = 0; i < coingiTransactions.size(); ++i) {
      org.knowm.xchange.coingi.dto.marketdata.CoingiTransaction tx = coingiTransactions.get(i);
      trades.add(adaptTrade(tx, currencyPair, 1000));
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static Order.OrderStatus adaptOrderStatus(int status) {
    if (status == 0) return Order.OrderStatus.PENDING_NEW;

    if (status == 1) return Order.OrderStatus.PARTIALLY_FILLED;

    if (status == 2) return Order.OrderStatus.FILLED;

    if (status == 3) return Order.OrderStatus.CANCELED;

    if (status == 4) return Order.OrderStatus.PENDING_CANCEL;

    throw new NotYetImplementedForExchangeException();
  }
}
