package org.knowm.xchange.livecoin;

import static org.knowm.xchange.currency.Currency.getInstance;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

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
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;
import org.knowm.xchange.livecoin.service.LivecoinAsksBidsData;
import org.knowm.xchange.utils.DateUtils;

public class LivecoinAdapters {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private LivecoinAdapters() {

  }

  public static CurrencyPair adaptCurrencyPair(LivecoinRestriction product) {
    String[] data = product.getCurrencyPair().split("\\/");
    return new CurrencyPair(data[0], data[1]);
  }

  public static OrderBook adaptOrderBook(LivecoinOrderBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(LivecoinAsksBidsData[] levels, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<>(levels.length);
    for (int i = 0; i < levels.length; i++) {
      LivecoinAsksBidsData ask = levels[i];

      allLevels.add(new LimitOrder(orderType, ask.getQuantity(), currencyPair, "0", null, ask.getRate()));
    }

    return allLevels;

  }

  public static Map<CurrencyPair, LivecoinOrderBook> adaptToCurrencyPairKeysMap(Map<String, LivecoinOrderBook> orderBooksRaw) {

    Set<Map.Entry<String, LivecoinOrderBook>> entries = orderBooksRaw.entrySet();
    Map<CurrencyPair, LivecoinOrderBook> converted = new HashMap<>(entries.size());
    for (Map.Entry<String, LivecoinOrderBook> entry : entries) {
      String[] currencyPairSplit = entry.getKey().split("/");
      CurrencyPair currencyPair = new CurrencyPair(currencyPairSplit[0], currencyPairSplit[1]);
      converted.put(currencyPair, entry.getValue());
    }
    return converted;
  }

  public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData exchangeMetaData, List<LivecoinRestriction> products) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();
    for (LivecoinRestriction product : products) {
      BigDecimal minSize = product.getMinLimitQuantity().setScale(product.getPriceScale(), BigDecimal.ROUND_UNNECESSARY);

      CurrencyPair pair = adaptCurrencyPair(product);

      CurrencyPairMetaData staticMetaData = exchangeMetaData.getCurrencyPairs().get(pair);
      int priceScale = staticMetaData == null ? 8 : staticMetaData.getPriceScale();

      if (currencyPairs.containsKey(pair)) {
        CurrencyPairMetaData existing = currencyPairs.get(pair);
        currencyPairs.put(pair, new CurrencyPairMetaData(existing.getTradingFee(), minSize, existing.getMaximumAmount(), priceScale));
      } else {
        currencyPairs.put(pair, new CurrencyPairMetaData(null, minSize, null, priceScale));
      }

      if (!currencies.containsKey(pair.base))
        currencies.put(pair.base, null);

      if (!currencies.containsKey(pair.counter))
        currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  public static Trades adaptTrades(LivecoinTrade[] nativeTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>(nativeTrades.length);

    for (int i = 0; i < nativeTrades.length; i++) {
      LivecoinTrade trade = nativeTrades[i];

      OrderType type = trade.getType().equals("SELL") ? OrderType.BID : OrderType.ASK;

      Trade t = new Trade(type, trade.getQuantity(), currencyPair, trade.getPrice(), parseDate(trade.getTime()), String.valueOf(trade.getId()));
      trades.add(t);
    }

    return new Trades(trades, nativeTrades[0].getId(), TradeSortType.SortByID);
  }

  private static Date parseDate(Long rawDateLong) {
    return new Date((long) rawDateLong * 1000);
  }

  public static Ticker adaptTicker(LivecoinTicker ticker, CurrencyPair currencyPair) {
    BigDecimal last = ticker.getLast();
    BigDecimal bid = ticker.getBestBid();
    BigDecimal ask = ticker.getBestAsk();
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal volume = ticker.getVolume();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();
  }

  public static LimitOrder adaptOpenOrder(Map map) {
    String typeName = map.get("type").toString();

    OrderType type;
    if (typeName.equals("MARKET_SELL"))
      type = OrderType.ASK;
    else if (typeName.equals("LIMIT_SELL"))
      type = OrderType.ASK;
    else if (typeName.equals("LIMIT_BUY"))
      type = OrderType.BID;
    else if (typeName.equals("MARKET_BUY"))
      type = OrderType.BID;
    else
      throw new IllegalStateException("Don't understand " + map);

    String ccyPair = map.get("currencyPair").toString();
    String[] pair = ccyPair.split("/");
    Currency ccyA = getInstance(pair[0]);
    Currency ccyB = getInstance(pair[1]);

    BigDecimal startingQuantity = new BigDecimal(map.get("quantity").toString());
    BigDecimal remainingQuantity = new BigDecimal(map.get("remainingQuantity").toString());

    Order.OrderStatus status = remainingQuantity.compareTo(startingQuantity) < 0 ? Order.OrderStatus.PARTIALLY_FILLED : Order.OrderStatus.PENDING_NEW;

    return new LimitOrder(
        type,
        remainingQuantity,
        new CurrencyPair(ccyA, ccyB),
        map.get("id").toString(),
        DateUtils.fromUnixTime(Long.valueOf(map.get("issueTime").toString())),
        new BigDecimal(map.get("price").toString()),
        null,
        null,
        null,
        status
    );
  }

  public static UserTrade adaptUserTrade(Map map) {
    OrderType type = OrderType.BID;
    if (map.get("type").toString().equals("SELL"))
      type = OrderType.ASK;

    Currency ccyA = Currency.getInstance(map.get("fixedCurrency").toString());
    Currency ccyB = Currency.getInstance(map.get("variableCurrency").toString());

    return new UserTrade(
        type,
        new BigDecimal(map.get("amount").toString()),
        new CurrencyPair(ccyA, ccyB),
        new BigDecimal(map.get("variableAmount").toString()),
        DateUtils.fromMillisUtc(Long.valueOf(map.get("date").toString())),
        map.get("id").toString(),
        map.get("externalKey").toString(),
        new BigDecimal(map.get("fee").toString()),
        getInstance(map.get("taxCurrency").toString())
    );
  }

  public static FundingRecord adaptFundingRecord(Map map) {
    FundingRecord.Type type = FundingRecord.Type.WITHDRAWAL;
    if (map.get("type").toString().equals("DEPOSIT"))
      type = FundingRecord.Type.DEPOSIT;

    return new FundingRecord(
        map.get("externalKey").toString(),
        DateUtils.fromMillisUtc(Long.valueOf(map.get("date").toString())),
        getInstance(map.get("fixedCurrency").toString()),
        new BigDecimal(map.get("amount").toString()),
        map.get("id").toString(),
        null,
        type,
        FundingRecord.Status.COMPLETE,
        null,
        new BigDecimal(map.get("fee").toString()),
        null
    );
  }

  public static List<Wallet> adaptWallets(List<Map> data) {
    Map<Currency, WalletBuilder> wallets = new HashMap<>();
    for (Map balance : data) {
      String type = balance.get("type").toString();
      String ccy = balance.get("currency").toString();
      String value = balance.get("value").toString();

      Currency curr = getInstance(ccy);

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
      return new Wallet(currency.getCurrencyCode(),
          new Balance(
              currency,
              map.get("total"),
              map.get("available"),
              map.get("trade"),
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              BigDecimal.ZERO
          ));
    }

    public void add(String type, String value) {
      map.put(type, new BigDecimal(value));
    }
  }
}
