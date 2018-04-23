package org.knowm.xchange.liqui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Balance.Builder;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.liqui.dto.LiquiTradeType;
import org.knowm.xchange.liqui.dto.account.LiquiAccountInfo;
import org.knowm.xchange.liqui.dto.marketdata.LiquiDepth;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPairInfo;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicAsk;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicBid;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicTrade;
import org.knowm.xchange.liqui.dto.marketdata.LiquiTicker;
import org.knowm.xchange.liqui.dto.trade.LiquiOrderInfo;
import org.knowm.xchange.liqui.dto.trade.LiquiTrade;
import org.knowm.xchange.liqui.dto.trade.LiquiUserTrade;

public class LiquiAdapters {

  public static Ticker adaptTicker(
      final LiquiTicker ticker, final org.knowm.xchange.currency.CurrencyPair currencyPair) {
    final Ticker.Builder builder = new Ticker.Builder();
    builder.bid(ticker.getBuy());
    builder.ask(ticker.getSell());
    builder.high(ticker.getHigh());
    builder.low(ticker.getLow());
    builder.last(ticker.getLast());
    builder.volume(ticker.getVol());
    builder.currencyPair(currencyPair);
    final Date timestamp = new Date(ticker.getUpdated());
    builder.timestamp(timestamp);

    return builder.build();
  }

  public static OrderBook adaptOrderBook(
      final LiquiDepth liquiDepth, final org.knowm.xchange.currency.CurrencyPair currencyPair) {
    return new OrderBook(
        null,
        LiquiAdapters.adaptAsks(liquiDepth.getAsks(), currencyPair),
        adaptBids(liquiDepth.getBids(), currencyPair));
  }

  public static List<LimitOrder> adaptAsks(
      final List<LiquiPublicAsk> orders,
      final org.knowm.xchange.currency.CurrencyPair currencyPair) {
    return orders.stream().map(ask -> adaptOrder(ask, currencyPair)).collect(Collectors.toList());
  }

  public static List<LimitOrder> adaptBids(
      final List<LiquiPublicBid> orders,
      final org.knowm.xchange.currency.CurrencyPair currencyPair) {
    return orders.stream().map(ask -> adaptOrder(ask, currencyPair)).collect(Collectors.toList());
  }

  public static LimitOrder adaptOrder(
      final LiquiPublicAsk order, final org.knowm.xchange.currency.CurrencyPair currencyPair) {
    final BigDecimal volume = order.getVolume();
    return new LimitOrder(OrderType.ASK, volume, currencyPair, "", null, order.getPrice());
  }

  public static LimitOrder adaptOrder(
      final LiquiPublicBid order, final org.knowm.xchange.currency.CurrencyPair currencyPair) {
    final BigDecimal volume = order.getVolume();
    return new LimitOrder(OrderType.BID, volume, currencyPair, "", null, order.getPrice());
  }

  public static Trades adaptTrades(
      final List<LiquiPublicTrade> liquiTrades,
      final org.knowm.xchange.currency.CurrencyPair currencyPair) {
    final List<Trade> trades = new ArrayList<>();
    for (final LiquiPublicTrade trade : liquiTrades) {
      trades.add(adaptTrade(trade, currencyPair));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptTrade(
      final LiquiPublicTrade trade, final org.knowm.xchange.currency.CurrencyPair currencyPair) {
    final OrderType type = adaptOrderType(trade.getType());
    final BigDecimal originalAmount = trade.getAmount();
    final Date timestamp = new Date((long) (trade.getTimestamp() * 1000L));
    final BigDecimal price = trade.getPrice();
    final long tradeId = trade.getTradeId();

    return new Trade(type, originalAmount, currencyPair, price, timestamp, String.valueOf(tradeId));
  }

  public static OrderType adaptOrderType(final LiquiTradeType liquiTradeType) {

    return liquiTradeType.equals(LiquiTradeType.BUY) ? OrderType.BID : OrderType.ASK;
  }

  public static String adaptOrderId(final LiquiTrade trade) {
    return String.valueOf(trade.getOrderId());
  }

  public static OpenOrders adaptActiveOrders(final Map<Long, LiquiOrderInfo> orders) {

    final List<LimitOrder> openOrders = new ArrayList<>();
    for (final Entry<Long, LiquiOrderInfo> entry : orders.entrySet()) {
      openOrders.add(adaptActiveOrder(entry.getValue(), entry.getKey()));
    }

    return new OpenOrders(openOrders);
  }

  public static LimitOrder adaptActiveOrder(final LiquiOrderInfo orderInfo, final long id) {

    final OrderType type = adaptOrderType(orderInfo.getType());

    final BigDecimal originalAmount = orderInfo.getStartAmount();
    final BigDecimal filledAmount = orderInfo.getStartAmount().subtract(orderInfo.getAmount());
    final org.knowm.xchange.currency.CurrencyPair pair = orderInfo.getPair();
    final Date timestamp = new Date(orderInfo.getTimestampCreated() * 1000L);

    final OrderStatus status = adaptOrderStatus(orderInfo.getStatus());

    return new LimitOrder(
        type,
        originalAmount,
        pair,
        String.valueOf(id),
        timestamp,
        orderInfo.getRate(),
        orderInfo.getRate(),
        filledAmount,
        null,
        status);
  }

  public static OrderStatus adaptOrderStatus(final String status) {
    switch (status) {
      case "0":
        return OrderStatus.NEW;
      case "1":
        return OrderStatus.FILLED;
      case "2":
        return OrderStatus.CANCELED;
      case "3":
        return OrderStatus.PARTIALLY_FILLED;
    }
    throw new RuntimeException("Unknown order status");
  }

  public static UserTrades adaptTradesHistory(final Map<Long, LiquiUserTrade> liquiTrades) {
    final List<UserTrade> trades = new ArrayList<>();
    for (final Entry<Long, LiquiUserTrade> entry : liquiTrades.entrySet()) {
      trades.add(adaptTrade(entry.getValue(), entry.getKey()));
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static UserTrade adaptTrade(final LiquiUserTrade liquiTrade, final Long tradeId) {
    final OrderType orderType = adaptOrderType(liquiTrade.getType());
    final BigDecimal originalAmount = liquiTrade.getAmount();
    final org.knowm.xchange.currency.CurrencyPair pair = liquiTrade.getPair();
    final Date timestamp = new Date(liquiTrade.getTimestamp() * 1000L);
    final BigDecimal price = liquiTrade.getRate();

    return new UserTrade(
        orderType,
        originalAmount,
        pair,
        price,
        timestamp,
        String.valueOf(tradeId),
        String.valueOf(tradeId),
        new BigDecimal("0"),
        null);
  }

  public static Order adaptOrderInfo(final LiquiOrderInfo info) {
    final OrderType orderType = adaptOrderType(info.getType());
    final org.knowm.xchange.currency.CurrencyPair pair = info.getPair();
    final BigDecimal amount = info.getStartAmount().subtract(info.getAmount());
    final BigDecimal startAmount = info.getStartAmount();
    final BigDecimal rate = info.getRate();
    final Date timestamp = new Date(info.getTimestampCreated() * 1000L);

    return new LimitOrder(orderType, startAmount, amount, pair, "", timestamp, rate);
  }

  public static ExchangeMetaData adaptToExchangeMetaData(final Map<String, LiquiPairInfo> infos) {
    final Map<org.knowm.xchange.currency.CurrencyPair, CurrencyPairMetaData> currencyPairs =
        new HashMap<>();
    final Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (final Entry<String, LiquiPairInfo> entry : infos.entrySet()) {
      final org.knowm.xchange.currency.CurrencyPair pair = adaptCurrencyPair(entry.getKey());
      final BigDecimal fee = entry.getValue().getFee();
      final BigDecimal minAmount = entry.getValue().getMinAmount();
      final BigDecimal maxAmount = entry.getValue().getMaxAmount();
      final int priceScale = entry.getValue().getDecimalPlaces();

      currencyPairs.put(pair, new CurrencyPairMetaData(fee, minAmount, maxAmount, priceScale));

      if (!currencies.containsKey(pair.getBase())) currencies.put(pair.getBase(), null);

      if (!currencies.containsKey(pair.getCounter())) currencies.put(pair.getCounter(), null);
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, null);
  }

  public static org.knowm.xchange.currency.CurrencyPair adaptCurrencyPair(final String pair) {
    final String[] split = pair.split("_");
    return org.knowm.xchange.currency.CurrencyPair.build(split[0], split[1]);
  }

  public static AccountInfo adaptAccountInfo(final LiquiAccountInfo info) {
    final Map<Currency, BigDecimal> funds = info.getFunds().getFunds();
    final List<Balance> balances =
        funds
            .entrySet()
            .stream()
            .map(
                entry ->
                    new Builder()
                        .setCurrency(entry.getKey())
                        .setTotal(entry.getValue())
                        .createBalance())
            .collect(Collectors.toList());

    final Wallet wallet = Wallet.build("Liqui wallet", balances);

    return AccountInfo.build(wallet);
  }
}
