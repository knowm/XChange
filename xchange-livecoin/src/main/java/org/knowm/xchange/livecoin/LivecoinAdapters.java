package org.knowm.xchange.livecoin;

import static org.knowm.xchange.currency.Currency.valueOf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance.Builder;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
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
    return CurrencyPair.build(data[0], data[1]);
  }

  public static OrderBook adaptOrderBook(LivecoinOrderBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(
      LivecoinAsksBidsData[] levels, OrderType orderType, CurrencyPair currencyPair) {

    if (levels == null || levels.length == 0) {
      return Collections.emptyList();
    }
    List<LimitOrder> allLevels = new ArrayList<>(levels.length);
    for (LivecoinAsksBidsData ask : levels) {
      allLevels.add(
          new LimitOrder(orderType, ask.getQuantity(), currencyPair, "0", null, ask.getRate()));
    }

    return allLevels;
  }

  public static Map<CurrencyPair, LivecoinOrderBook> adaptToCurrencyPairKeysMap(
      Map<String, LivecoinOrderBook> orderBooksRaw) {

    Set<Entry<String, LivecoinOrderBook>> entries = orderBooksRaw.entrySet();
    Map<CurrencyPair, LivecoinOrderBook> converted = new HashMap<>(entries.size());
    for (Entry<String, LivecoinOrderBook> entry : entries) {
      String[] currencyPairSplit = entry.getKey().split("/");
      CurrencyPair currencyPair = CurrencyPair.build(currencyPairSplit[0], currencyPairSplit[1]);
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
      minSize = minSize.setScale(product.getPriceScale(), RoundingMode.UNNECESSARY);

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

      if (!currencies.containsKey(pair.getBase())) currencies.put(pair.getBase(), null);

      if (!currencies.containsKey(pair.getCounter())) currencies.put(pair.getCounter(), null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  public static Trades adaptTrades(LivecoinTrade[] nativeTrades, CurrencyPair currencyPair) {

    if (nativeTrades.length == 0) {
      return new Trades(Collections.<Trade>emptyList());
    }
    List<Trade> trades = new ArrayList<>(nativeTrades.length);

    for (LivecoinTrade trade : nativeTrades) {
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

    return new Trades(trades, nativeTrades[0].getId(), TradeSortType.SortByID);
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
    Currency ccyA = valueOf(pair[0]);
    Currency ccyB = valueOf(pair[1]);

    BigDecimal startingQuantity = new BigDecimal(map.get("quantity").toString());
    BigDecimal remainingQuantity = new BigDecimal(map.get("remainingQuantity").toString());

    OrderStatus status =
        remainingQuantity.compareTo(startingQuantity) < 0
            ? OrderStatus.PARTIALLY_FILLED
            : OrderStatus.PENDING_NEW;

    return new LimitOrder(
        type,
        remainingQuantity,
        CurrencyPair.build(ccyA, ccyB),
        map.get("id").toString(),
        DateUtils.fromUnixTime(Double.valueOf(map.get("issueTime").toString()).longValue()),
        new BigDecimal(map.get("price").toString()),
        null,
        null,
        null,
        status);
  }

  public static UserTrade adaptUserTrade(Map map) {
    OrderType type = OrderType.BID;
    if (map.get("type").toString().equals("SELL")) type = OrderType.ASK;

    Currency ccyA = valueOf(map.get("fixedCurrency").toString());
    Currency ccyB = valueOf(map.get("variableCurrency").toString());

    BigDecimal amountA = new BigDecimal(map.get("amount").toString());
    BigDecimal amountB = new BigDecimal(map.get("variableAmount").toString());
    int scale = Math.max(amountA.scale(), amountB.scale());
    BigDecimal price = amountB.divide(amountA, scale, RoundingMode.HALF_UP);

    String id = map.get("id").toString();

    return new UserTrade(
        type,
        amountA,
        CurrencyPair.build(ccyA, ccyB),
        price,
        DateUtils.fromMillisUtc(Long.valueOf(map.get("date").toString())),
        id,
        map.get("externalKey").toString(),
        new BigDecimal(map.get("fee").toString()),
        valueOf(map.get("taxCurrency").toString()));
  }

  public static FundingRecord adaptFundingRecord(Map map) {
    Type type = Type.WITHDRAWAL;
    if (map.get("type").toString().equals("DEPOSIT")) type = Type.DEPOSIT;

    return new FundingRecord(
        map.get("externalKey").toString(),
        DateUtils.fromMillisUtc(Long.valueOf(map.get("date").toString())),
        valueOf(map.get("fixedCurrency").toString()),
        new BigDecimal(map.get("amount").toString()),
        map.get("id").toString(),
        null,
        type,
        Status.COMPLETE,
        null,
        new BigDecimal(map.get("fee").toString()),
        null);
  }

  public static List<Wallet> adaptWallets(List<Map> data) {
    Map<Currency, WalletBuilder> wallets = new HashMap<>();
    for (Map balance : data) {
      String type = balance.get("type").toString();
      String ccy = balance.get("currency").toString();
      String value = balance.get("value").toString();

      Currency curr = valueOf(ccy);

      WalletBuilder builder = wallets.get(curr);
      if (builder == null) {
        builder = new WalletBuilder(curr);
      }
      builder.add(type, value);

      wallets.put(curr, builder);
    }

    List<Wallet> res = new ArrayList<>();
    for (WalletBuilder builder : wallets.values()) {
      res.add(builder.build());
    }

    return res;
  }

  static class WalletBuilder {
    private Currency currency;
    private Map<String, BigDecimal> map = new HashMap<>();

    WalletBuilder(Currency currency) {
      this.currency = currency;
    }

    public Wallet build() {
      return Wallet.build(
          currency.getCurrencyCode(),
          new Builder()
              .setCurrency(currency)
              .setTotal(map.get("total"))
              .setAvailable(map.get("available"))
              .setFrozen(map.get("trade"))
              .setBorrowed(BigDecimal.ZERO)
              .setLoaned(BigDecimal.ZERO)
              .setWithdrawing(BigDecimal.ZERO)
              .setDepositing(BigDecimal.ZERO)
              .createBalance());
    }

    public void add(String type, String value) {
      map.put(type, new BigDecimal(value));
    }
  }
}
