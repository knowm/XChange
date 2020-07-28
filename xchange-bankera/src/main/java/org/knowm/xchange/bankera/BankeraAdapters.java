package org.knowm.xchange.bankera;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.bankera.dto.BankeraException;
import org.knowm.xchange.bankera.dto.account.BankeraUserInfo;
import org.knowm.xchange.bankera.dto.account.BankeraWallet;
import org.knowm.xchange.bankera.dto.marketdata.*;
import org.knowm.xchange.bankera.dto.trade.BankeraOpenOrders;
import org.knowm.xchange.bankera.dto.trade.BankeraOrder;
import org.knowm.xchange.bankera.dto.trade.BankeraUserTrades;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public final class BankeraAdapters {

  private static final String ORDER_SIDE_BUY = "buy";

  private BankeraAdapters() {}

  public static AccountInfo adaptAccountInfo(BankeraUserInfo userInfo) {
    return new AccountInfo(
        String.valueOf(userInfo.getUser().getId()), adaptWallet(userInfo.getUser().getWallets()));
  }

  public static Wallet adaptWallet(List<BankeraWallet> wallets) {
    List<Balance> balances =
        wallets.stream()
            .map(
                w ->
                    new Balance.Builder()
                        .total(new BigDecimal(w.getTotal()))
                        .available(new BigDecimal(w.getBalance()))
                        .frozen(new BigDecimal(w.getReserved()))
                        .currency(new Currency(w.getCurrency()))
                        .build())
            .collect(Collectors.toList());
    return Wallet.Builder.from(balances).build();
  }

  public static ExchangeException adaptError(BankeraException exception) {
    return exception.getHttpStatusCode() == 403
        ? new ExchangeSecurityException()
        : new ExchangeException(exception.getError(), exception);
  }

  /**
   * Adapts Bankera BankeraTickerResponse to a Ticker
   *
   * @param ticker Specific ticker
   * @param currencyPair BankeraCurrency pair (e.g. ETH/BTC)
   * @return Ticker
   */
  public static Ticker adaptTicker(BankeraTickerResponse ticker, CurrencyPair currencyPair) {

    BigDecimal high = new BigDecimal(ticker.getTicker().getHigh());
    BigDecimal low = new BigDecimal(ticker.getTicker().getLow());
    BigDecimal bid = new BigDecimal(ticker.getTicker().getBid());
    BigDecimal ask = new BigDecimal(ticker.getTicker().getAsk());
    BigDecimal last = new BigDecimal(ticker.getTicker().getLast());
    BigDecimal volume = new BigDecimal(ticker.getTicker().getVolume());
    Date timestamp = new Date(ticker.getTicker().getTimestamp());

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .high(high)
        .low(low)
        .bid(bid)
        .ask(ask)
        .last(last)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public static OrderBook adaptOrderBook(BankeraOrderBook orderbook, CurrencyPair currencyPair) {

    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, orderbook.getBids());
    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, orderbook.getAsks());

    return new OrderBook(null, asks, bids);
  }

  public static Trades adaptTrades(
      BankeraTradesResponse tradesResponse, CurrencyPair currencyPair) {

    List<BankeraTrade> bankeraTrades = tradesResponse.getTrades();
    List<Trade> tradesList = new ArrayList<>();

    bankeraTrades.forEach(
        bankeraTrade -> {
          BigDecimal amount = new BigDecimal(bankeraTrade.getAmount());
          BigDecimal price = new BigDecimal(bankeraTrade.getPrice());
          Date date = new Date(Long.parseLong(bankeraTrade.getTime()));
          OrderType type =
              bankeraTrade.getSide().equalsIgnoreCase(ORDER_SIDE_BUY)
                  ? OrderType.BID
                  : OrderType.ASK;
          tradesList.add(
              new Trade.Builder()
                  .type(type)
                  .originalAmount(amount)
                  .currencyPair(currencyPair)
                  .price(price)
                  .timestamp(date)
                  .build());
        });
    return new Trades(tradesList, 0L, Trades.TradeSortType.SortByTimestamp);
  }

  private static List<LimitOrder> createOrders(
      CurrencyPair currencyPair,
      OrderType orderType,
      List<BankeraOrderBook.OrderBookOrder> ordersList) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    if (ordersList == null) return limitOrders;

    ordersList.forEach(
        order -> {
          limitOrders.add(
              new LimitOrder(
                  orderType,
                  new BigDecimal(order.getAmount()),
                  currencyPair,
                  String.valueOf(order.getId()),
                  null,
                  new BigDecimal(order.getPrice())));
        });

    return limitOrders;
  }

  public static List<LimitOrder> adaptOpenOrders(BankeraOpenOrders openOrders) {

    List<LimitOrder> orderList = new ArrayList<>();

    openOrders
        .getOpenOrders()
        .forEach(
            bankeraOrder -> {
              String[] currencies = bankeraOrder.getMarket().split("-");
              CurrencyPair pair = new CurrencyPair(currencies[0], currencies[1]);
              orderList.add(
                  new LimitOrder(
                      bankeraOrder.getSide().equalsIgnoreCase("buy")
                          ? OrderType.BID
                          : OrderType.ASK,
                      new BigDecimal(bankeraOrder.getAmount()),
                      new BigDecimal(bankeraOrder.getRemainingAmount()),
                      pair,
                      String.valueOf(bankeraOrder.getId()),
                      new Date(Long.valueOf(bankeraOrder.getCreatedAt())),
                      new BigDecimal(bankeraOrder.getPrice())));
            });

    return orderList;
  }

  public static List<UserTrade> adaptUserTrades(BankeraUserTrades userTrades) {
    List<UserTrade> tradeList = new ArrayList<>();

    userTrades
        .getTrades()
        .forEach(
            trade -> {
              String[] currencies = trade.getMarket().split("-");
              CurrencyPair pair = new CurrencyPair(currencies[0], currencies[1]);
              Currency feeCurrency = new Currency(currencies[1]);
              tradeList.add(
                  new UserTrade.Builder()
                      .type(trade.getSide().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK)
                      .originalAmount(new BigDecimal(trade.getAmount()))
                      .currencyPair(pair)
                      .price(new BigDecimal(trade.getPrice()))
                      .timestamp(new Date(Long.parseLong(trade.getCompletedAt())))
                      .id(String.valueOf(trade.getId()))
                      .orderId(String.valueOf(trade.getOrderId()))
                      .feeAmount(new BigDecimal(trade.getFeeAmount()))
                      .feeCurrency(feeCurrency)
                      .build());
            });

    return tradeList;
  }

  public static Order adaptOrder(BankeraOrder bankeraOrder) {
    String[] currencies = bankeraOrder.getMarket().split("-");
    CurrencyPair pair = new CurrencyPair(currencies[0], currencies[1]);
    DecimalFormat format = new DecimalFormat();
    format.setParseBigDecimal(true);
    return new LimitOrder(
        bankeraOrder.getSide().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK,
        new BigDecimal(bankeraOrder.getAmount()),
        pair,
        String.valueOf(bankeraOrder.getId()),
        new Date(Long.parseLong(bankeraOrder.getCreatedAt())),
        new BigDecimal(bankeraOrder.getPrice()),
        new BigDecimal(bankeraOrder.getPrice()),
        new BigDecimal(bankeraOrder.getExecutedAmount()),
        bankeraOrder.getTotalFee(),
        adaptOrderStatus(bankeraOrder.getStatus()));
  }

  private static Order.OrderStatus adaptOrderStatus(String status) {
    switch (status.toLowerCase()) {
      case "open":
        return Order.OrderStatus.NEW;
      case "completed":
        return Order.OrderStatus.FILLED;
      case "cancelled":
        return Order.OrderStatus.CANCELED;
      case "rejected":
        return Order.OrderStatus.REJECTED;
      case "pending cancel":
        return Order.OrderStatus.PENDING_CANCEL;
      default:
        return Order.OrderStatus.UNKNOWN;
    }
  }
}
