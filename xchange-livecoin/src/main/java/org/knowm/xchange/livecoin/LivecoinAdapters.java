package org.knowm.xchange.livecoin;

import static org.knowm.xchange.currency.Currency.getInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
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
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.livecoin.dto.account.LivecoinBalance;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinAllOrderBooks;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrder;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinUserOrder;
import org.knowm.xchange.utils.DateUtils;

public class LivecoinAdapters {

  private static final int FALLBACK_PRICE_SCALE = 8;

  // user for all their pairs
  private static final int LIVECOIN_BASE_SCALE = 8;

  public static CurrencyPair adaptCurrencyPair(LivecoinRestriction product) {
    String[] data = product.getCurrencyPair().split("\\/");
    return new CurrencyPair(data[0], data[1]);
  }

  public static OrderBook adaptOrderBook(LivecoinOrderBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(
      List<LivecoinOrder> levels, OrderType orderType, CurrencyPair currencyPair) {

    if (levels == null || levels.isEmpty()) {
      return Collections.emptyList();
    }
    List<LimitOrder> allLevels = new ArrayList<>(levels.size());
    for (LivecoinOrder ask : levels) {
      if (ask != null) {
        allLevels.add(
            new LimitOrder(orderType, ask.getQuantity(), currencyPair, "0", null, ask.getRate()));
      }
    }

    return allLevels;
  }

  public static Map<CurrencyPair, LivecoinOrderBook> adaptToCurrencyPairKeysMap(
      LivecoinAllOrderBooks orderBooksRaw) {

    Set<Map.Entry<String, LivecoinOrderBook>> entries =
        orderBooksRaw.getOrderBooksByPair().entrySet();
    Map<CurrencyPair, LivecoinOrderBook> converted = new HashMap<>(entries.size());
    for (Map.Entry<String, LivecoinOrderBook> entry : entries) {
      String[] currencyPairSplit = entry.getKey().split("/");
      CurrencyPair currencyPair = new CurrencyPair(currencyPairSplit[0], currencyPairSplit[1]);
      converted.put(currencyPair, entry.getValue());
    }
    return converted;
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      ExchangeMetaData exchangeMetaData, List<LivecoinRestriction> products) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();
    for (LivecoinRestriction product : products) {
      CurrencyPair pair = adaptCurrencyPair(product);
      CurrencyPairMetaData staticMetaData = exchangeMetaData.getCurrencyPairs().get(pair);
      CurrencyPairMetaData.Builder builder;
      if (staticMetaData == null) {
        builder = new CurrencyPairMetaData.Builder();
      } else {
        builder = CurrencyPairMetaData.Builder.from(staticMetaData);
      }
      if (product.getPriceScale() != null) {
        builder.priceScale(product.getPriceScale());
      } else if (staticMetaData != null && staticMetaData.getPriceScale() == null) {
        builder.priceScale(FALLBACK_PRICE_SCALE);
      }
      if (!(staticMetaData != null && staticMetaData.getBaseScale() != null)) {
        builder.baseScale(LIVECOIN_BASE_SCALE);
      }
      if (product.getMinLimitQuantity() != null) {
        builder.minimumAmount(product.getMinLimitQuantity());
      }
      currencyPairs.put(pair, builder.build());

      if (!currencies.containsKey(pair.base)) currencies.put(pair.base, null);
      if (!currencies.containsKey(pair.counter)) currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  public static Trades adaptTrades(List<LivecoinTrade> tradesRaw, CurrencyPair currencyPair) {

    if (tradesRaw.isEmpty()) {
      return new Trades(Collections.emptyList());
    }
    List<Trade> trades = new ArrayList<>(tradesRaw.size());

    for (LivecoinTrade trade : tradesRaw) {
      OrderType type = trade.getType().equals("SELL") ? OrderType.BID : OrderType.ASK;
      Trade t =
          new Trade.Builder()
              .type(type)
              .originalAmount(trade.getQuantity())
              .currencyPair(currencyPair)
              .price(trade.getPrice())
              .timestamp(parseDate(trade.getTime()))
              .id(String.valueOf(trade.getId()))
              .build();

      trades.add(t);
    }

    return new Trades(trades, tradesRaw.get(0).getId(), TradeSortType.SortByID);
  }

  private static Date parseDate(Long rawDateLong) {
    return new Date(rawDateLong * 1000);
  }

  public static Ticker adaptTicker(LivecoinTicker ticker, CurrencyPair currencyPair) {
    BigDecimal last = ticker.getLast();
    BigDecimal bid = ticker.getBestBid();
    BigDecimal ask = ticker.getBestAsk();
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal volume = ticker.getVolume();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .build();
  }

  public static Collection<Order> adaptUserOrders(Collection<LivecoinUserOrder> orders) {
    return orders.stream().map(LivecoinAdapters::adaptUserOrder).collect(Collectors.toList());
  }

  public static Order adaptUserOrder(LivecoinUserOrder order) {
    OrderType type;
    boolean limit;
    switch (order.getType()) {
      case "MARKET_SELL":
        type = OrderType.ASK;
        limit = false;
        break;
      case "LIMIT_SELL":
        type = OrderType.ASK;
        limit = true;
        break;
      case "LIMIT_BUY":
        type = OrderType.BID;
        limit = true;
        break;
      case "MARKET_BUY":
        type = OrderType.BID;
        limit = false;
        break;
      default:
        throw new ExchangeException(
            "Order id=" + order.getId() + " has unknown type " + order.getType());
    }
    CurrencyPair pair = new CurrencyPair(order.getCurrencyPair());
    Order.OrderStatus status;
    if (order.getRemainingQuantity().compareTo(BigDecimal.ZERO) == 0) {
      status = Order.OrderStatus.FILLED;
    } else {
      status =
          order.getRemainingQuantity().compareTo(order.getQuantity()) < 0
              ? Order.OrderStatus.PARTIALLY_FILLED
              : Order.OrderStatus.PENDING_NEW;
    }

    Order.Builder builder;
    if (limit) {
      builder =
          new LimitOrder.Builder(type, pair)
              .limitPrice(order.getPrice())
              .remainingAmount(order.getRemainingQuantity());
    } else {
      builder = new MarketOrder.Builder(type, pair).averagePrice(order.getPrice());
    }
    return builder
        .originalAmount(order.getQuantity())
        .id(order.getId().toString())
        .timestamp(DateUtils.fromUnixTime(order.getIssueTime()))
        .orderStatus(status)
        .build();
  }

  public static UserTrade adaptUserTrade(Map map) {
    OrderType type = OrderType.BID;
    if (map.get("type").toString().equals("SELL")) type = OrderType.ASK;

    Currency ccyA = Currency.getInstance(map.get("fixedCurrency").toString());
    Currency ccyB = Currency.getInstance(map.get("variableCurrency").toString());

    BigDecimal amountA = new BigDecimal(map.get("amount").toString());
    BigDecimal amountB = new BigDecimal(map.get("variableAmount").toString());
    int scale = Math.max(amountA.scale(), amountB.scale());
    BigDecimal price = amountB.divide(amountA, scale, RoundingMode.HALF_UP);

    String id = map.get("id").toString();

    return new UserTrade.Builder()
        .type(type)
        .originalAmount(amountA)
        .currencyPair(new CurrencyPair(ccyA, ccyB))
        .price(price)
        .timestamp(DateUtils.fromMillisUtc(Long.parseLong(map.get("date").toString())))
        .id(id)
        .orderId(Optional.ofNullable(map.get("externalKey")).map(Object::toString).orElse(null))
        .feeAmount(new BigDecimal(map.get("fee").toString()))
        .feeCurrency(getInstance(map.get("taxCurrency").toString()))
        .build();
  }

  public static FundingRecord adaptFundingRecord(Map map) {
    FundingRecord.Type type = FundingRecord.Type.WITHDRAWAL;
    if (map.get("type").toString().equals("DEPOSIT")) type = FundingRecord.Type.DEPOSIT;

    return new FundingRecord(
        Optional.ofNullable(map.get("externalKey")).map(Object::toString).orElse(null),
        DateUtils.fromMillisUtc(Long.parseLong(map.get("date").toString())),
        getInstance(map.get("fixedCurrency").toString()),
        new BigDecimal(map.get("amount").toString()),
        map.get("id").toString(),
        null,
        type,
        FundingRecord.Status.COMPLETE,
        null,
        new BigDecimal(map.get("fee").toString()),
        null);
  }

  public static Wallet adaptWallet(List<LivecoinBalance> livecoinBalances) {
    Map<Currency, Balance.Builder> balanceBuildersByCurrency = new HashMap<>();
    for (LivecoinBalance livecoinBalance : livecoinBalances) {

      // Livecoin has a currency Bricktox (XBT) which is different from XChange Bitcoin (XBT). See
      // Currency.XBT.
      // The "get all currencies" call to Livecoin returns all currencies including those with 0
      // balance.
      // As a result, XBT overrides BTC, so BTC becomes 0 (in most cases).
      // Excluding XBT.
      if (livecoinBalance.getCurrency().equals("XBT")) {
        continue;
      }

      Currency currency = getInstance(livecoinBalance.getCurrency());

      Balance.Builder builder = balanceBuildersByCurrency.get(currency);
      if (builder == null) {
        builder = new Balance.Builder().currency(currency);
        balanceBuildersByCurrency.put(currency, builder);
      }
      BigDecimal value = livecoinBalance.getValue();
      switch (livecoinBalance.getType()) {
        case "total":
          builder.total(value);
          break;
        case "available":
          builder.available(value);
          break;
        case "trade":
          builder.frozen(value);
          break;
      }
    }
    return Wallet.Builder.from(
            balanceBuildersByCurrency.values().stream()
                .map(Balance.Builder::build)
                .collect(Collectors.toList()))
        .build();
  }
}
