package org.knowm.xchange.livecoin;

import static org.knowm.xchange.currency.Currency.getInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
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
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.livecoin.dto.account.LivecoinBalance;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinAllOrderBooks;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;
import org.knowm.xchange.livecoin.service.LivecoinAsksBidsData;
import org.knowm.xchange.utils.DateUtils;

public class LivecoinAdapters {

  private static final SimpleDateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private LivecoinAdapters() {}

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
      LivecoinAsksBidsData[] levels, OrderType orderType, CurrencyPair currencyPair) {

    if (levels == null || levels.length == 0) {
      return Collections.EMPTY_LIST;
    }
    List<LimitOrder> allLevels = new ArrayList<>(levels.length);
    for (LivecoinAsksBidsData ask : levels) {
      allLevels.add(
          new LimitOrder(orderType, ask.getQuantity(), currencyPair, "0", null, ask.getRate()));
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
      BigDecimal minSize =
          product.getMinLimitQuantity() == null ? BigDecimal.ZERO : product.getMinLimitQuantity();
      minSize = minSize.setScale(product.getPriceScale(), BigDecimal.ROUND_UNNECESSARY);

      CurrencyPair pair = adaptCurrencyPair(product);

      CurrencyPairMetaData staticMetaData = exchangeMetaData.getCurrencyPairs().get(pair);
      int priceScale = staticMetaData == null ? 8 : staticMetaData.getPriceScale();

      if (currencyPairs.containsKey(pair)) {
        CurrencyPairMetaData existing = currencyPairs.get(pair);
        currencyPairs.put(
            pair,
            new CurrencyPairMetaData(
                existing.getTradingFee(), minSize, existing.getMaximumAmount(), priceScale));
      } else {
        currencyPairs.put(pair, new CurrencyPairMetaData(null, minSize, null, priceScale));
      }

      if (!currencies.containsKey(pair.base)) currencies.put(pair.base, null);

      if (!currencies.containsKey(pair.counter)) currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  public static Trades adaptTrades(List<LivecoinTrade> tradesRaw, CurrencyPair currencyPair) {

    if (tradesRaw.isEmpty()) {
      return new Trades(Collections.EMPTY_LIST);
    }
    List<Trade> trades = new ArrayList<>(tradesRaw.size());

    for (LivecoinTrade trade : tradesRaw) {
      OrderType type = trade.getType().equals("SELL") ? OrderType.BID : OrderType.ASK;
      Trade t =
          new Trade(
              type,
              trade.getQuantity(),
              currencyPair,
              trade.getPrice(),
              parseDate(trade.getTime()),
              String.valueOf(trade.getId()));
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

  public static LimitOrder adaptOpenOrder(Map map) {
    String typeName = map.get("type").toString();

    OrderType type;
    switch (typeName) {
      case "MARKET_SELL":
        type = OrderType.ASK;
        break;
      case "LIMIT_SELL":
        type = OrderType.ASK;
        break;
      case "LIMIT_BUY":
        type = OrderType.BID;
        break;
      case "MARKET_BUY":
        type = OrderType.BID;
        break;
      default:
        throw new IllegalStateException("Don't understand " + map);
    }

    String ccyPair = map.get("currencyPair").toString();
    String[] pair = ccyPair.split("/");
    Currency ccyA = getInstance(pair[0]);
    Currency ccyB = getInstance(pair[1]);

    BigDecimal startingQuantity = new BigDecimal(map.get("quantity").toString());
    BigDecimal remainingQuantity = new BigDecimal(map.get("remainingQuantity").toString());

    Order.OrderStatus status =
        remainingQuantity.compareTo(startingQuantity) < 0
            ? Order.OrderStatus.PARTIALLY_FILLED
            : Order.OrderStatus.PENDING_NEW;

    return new LimitOrder(
        type,
        remainingQuantity,
        new CurrencyPair(ccyA, ccyB),
        map.get("id").toString(),
        DateUtils.fromUnixTime(Double.valueOf(map.get("issueTime").toString()).longValue()),
        Optional.ofNullable(map.get("price"))
            .map(Object::toString)
            .map(BigDecimal::new)
            .orElse(null),
        null,
        null,
        null,
        status);
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

    return new UserTrade(
        type,
        amountA,
        new CurrencyPair(ccyA, ccyB),
        price,
        DateUtils.fromMillisUtc(Long.valueOf(map.get("date").toString())),
        id,
        Optional.ofNullable(map.get("externalKey")).map(Object::toString).orElse(null),
        new BigDecimal(map.get("fee").toString()),
        getInstance(map.get("taxCurrency").toString()));
  }

  public static FundingRecord adaptFundingRecord(Map map) {
    FundingRecord.Type type = FundingRecord.Type.WITHDRAWAL;
    if (map.get("type").toString().equals("DEPOSIT")) type = FundingRecord.Type.DEPOSIT;

    return new FundingRecord(
        Optional.ofNullable(map.get("externalKey")).map(Object::toString).orElse(null),
        DateUtils.fromMillisUtc(Long.valueOf(map.get("date").toString())),
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
    return new Wallet(
        balanceBuildersByCurrency
            .values()
            .stream()
            .map(Balance.Builder::build)
            .collect(Collectors.toList()));
  }
}
