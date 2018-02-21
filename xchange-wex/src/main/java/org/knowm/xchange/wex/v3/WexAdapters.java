package org.knowm.xchange.wex.v3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.wex.v3.dto.account.WexAccountInfo;
import org.knowm.xchange.wex.v3.dto.marketdata.WexExchangeInfo;
import org.knowm.xchange.wex.v3.dto.marketdata.WexPairInfo;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTicker;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTrade;
import org.knowm.xchange.wex.v3.dto.meta.WexMetaData;
import org.knowm.xchange.wex.v3.dto.trade.WexOrder;
import org.knowm.xchange.wex.v3.dto.trade.WexOrderInfoResult;
import org.knowm.xchange.wex.v3.dto.trade.WexTradeHistoryResult;
import org.knowm.xchange.wex.v3.dto.trade.WexTransHistoryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Various adapters for converting from Wex DTOs to XChange DTOs
 */
public final class WexAdapters {

  public static final Logger log = LoggerFactory.getLogger(WexAdapters.class);

  /**
   * private Constructor
   */
  private WexAdapters() {

  }

  /**
   * Adapts a List of BTCEOrders to a List of LimitOrders
   *
   * @param bTCEOrders
   * @param currencyPair
   * @param orderTypeString
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> bTCEOrders, CurrencyPair currencyPair, String orderTypeString, String id) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    for (BigDecimal[] btceOrder : bTCEOrders) {
      limitOrders.add(adaptOrder(btceOrder[1], btceOrder[0], currencyPair, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a WexOrder to a LimitOrder
   *
   * @param amount
   * @param price
   * @param currencyPair
   * @param orderType
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType, String id) {

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);
  }

  /**
   * Adapts a BTCETradeV3 to a Trade Object
   *
   * @param bTCETrade Wex trade object v.3
   * @param currencyPair the currency pair
   * @return The XChange Trade
   */
  public static Trade adaptTrade(WexTrade bTCETrade, CurrencyPair currencyPair) {

    OrderType orderType = bTCETrade.getTradeType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = bTCETrade.getAmount();
    BigDecimal price = bTCETrade.getPrice();
    Date date = DateUtils.fromMillisUtc(bTCETrade.getDate() * 1000L);

    final String tradeId = String.valueOf(bTCETrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  /**
   * Adapts a BTCETradeV3[] to a Trades Object
   *
   * @param bTCETrades The Wex trade data returned by API v.3
   * @param currencyPair the currency pair
   * @return The trades
   */
  public static Trades adaptTrades(WexTrade[] bTCETrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>();
    long lastTradeId = 0;
    for (WexTrade bTCETrade : bTCETrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      long tradeId = bTCETrade.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(0, adaptTrade(bTCETrade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a WexTicker to a Ticker Object
   *
   * @param bTCETicker
   * @return
   */
  public static Ticker adaptTicker(WexTicker bTCETicker, CurrencyPair currencyPair) {

    BigDecimal last = bTCETicker.getLast();
    BigDecimal bid = bTCETicker.getSell();
    BigDecimal ask = bTCETicker.getBuy();
    BigDecimal high = bTCETicker.getHigh();
    BigDecimal low = bTCETicker.getLow();
    BigDecimal avg = bTCETicker.getAvg();
    BigDecimal volume = bTCETicker.getVolCur();
    Date timestamp = DateUtils.fromMillisUtc(bTCETicker.getUpdated() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).vwap(avg).volume(volume)
        .timestamp(timestamp).build();
  }

  public static Wallet adaptWallet(WexAccountInfo wexAccountInfo) {

    List<Balance> balances = new ArrayList<>();
    Map<String, BigDecimal> funds = wexAccountInfo.getFunds();

    for (String lcCurrency : funds.keySet()) {
      /* BTC-E signals DASH as DSH. This is a different coin. Translate in correct DASH name */
      BigDecimal fund = funds.get(lcCurrency);
      if (lcCurrency.equals("dsh")) {
        lcCurrency = "dash";
      }
      Currency currency = Currency.getInstance(lcCurrency);
      balances.add(new Balance(currency, fund));
    }
    return new Wallet(balances);
  }

  public static OpenOrders adaptOrders(Map<Long, WexOrder> btceOrderMap) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (Long id : btceOrderMap.keySet()) {
      WexOrder bTCEOrder = btceOrderMap.get(id);
      OrderType orderType = bTCEOrder.getType() == WexOrder.Type.buy ? OrderType.BID : OrderType.ASK;
      BigDecimal price = bTCEOrder.getRate();
      Date timestamp = DateUtils.fromMillisUtc(bTCEOrder.getTimestampCreated() * 1000L);
      CurrencyPair currencyPair = adaptCurrencyPair(bTCEOrder.getPair());

      limitOrders.add(new LimitOrder(orderType, bTCEOrder.getAmount(), currencyPair, Long.toString(id), timestamp, price));
    }
    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradeHistory(Map<Long, WexTradeHistoryResult> tradeHistory) {

    List<UserTrade> trades = new ArrayList<>(tradeHistory.size());
    for (Entry<Long, WexTradeHistoryResult> entry : tradeHistory.entrySet()) {
      WexTradeHistoryResult result = entry.getValue();
      OrderType type = result.getType() == WexTradeHistoryResult.Type.buy ? OrderType.BID : OrderType.ASK;
      BigDecimal price = result.getRate();
      BigDecimal originalAmount = result.getAmount();
      Date timeStamp = DateUtils.fromMillisUtc(result.getTimestamp() * 1000L);
      String orderId = String.valueOf(result.getOrderId());
      String tradeId = String.valueOf(entry.getKey());
      CurrencyPair currencyPair = adaptCurrencyPair(result.getPair());
      trades.add(new UserTrade(type, originalAmount, currencyPair, price, timeStamp, tradeId, orderId, null, (Currency) null));
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  /**
   * Adapts a WexOrderInfoResult to a LimitOrder
   *
   * @param orderId Order original id
   * @param orderInfo
   * @return
   */
  public static LimitOrder adaptOrderInfo(String orderId, WexOrderInfoResult orderInfo) {

    OrderType orderType = orderInfo.getType() == WexOrderInfoResult.Type.buy ? OrderType.BID : OrderType.ASK;
    BigDecimal price = orderInfo.getRate();
    Date timestamp = DateUtils.fromMillisUtc(orderInfo.getTimestampCreated() * 1000L);
    CurrencyPair currencyPair = adaptCurrencyPair(orderInfo.getPair());
    OrderStatus orderStatus = null;
    switch (orderInfo.getStatus()) {
      case 0:
        if (orderInfo.getAmount().compareTo(orderInfo.getStartAmount()) == 0) {
          orderStatus = OrderStatus.NEW;
        } else {
          orderStatus = OrderStatus.PARTIALLY_FILLED;
        }
        break;
      case 1:
        orderStatus = OrderStatus.FILLED;
        break;
      case 2:
      case 3:
        orderStatus = OrderStatus.CANCELED;
        break;
    }

    return new LimitOrder(orderType, orderInfo.getStartAmount(), currencyPair, orderId, timestamp, price, price, orderInfo.getStartAmount().subtract(orderInfo.getAmount()), null, orderStatus);
  }

  public static CurrencyPair adaptCurrencyPair(String btceCurrencyPair) {

    String[] currencies = btceCurrencyPair.split("_");
    /* BTC-E signals DASH as DSH. This is a different coin. Translate in correct DASH name */
    if (currencies[0].equals("dsh")) {
      currencies[0] = "dash";
    }
    if (currencies[1].equals("dsh")) {
      currencies[1] = "dash";
    }
    return new CurrencyPair(currencies[0].toUpperCase(), currencies[1].toUpperCase());
  }

  public static List<CurrencyPair> adaptCurrencyPairs(Iterable<String> btcePairs) {

    List<CurrencyPair> pairs = new ArrayList<>();
    for (String btcePair : btcePairs) {
      pairs.add(adaptCurrencyPair(btcePair));
    }

    return pairs;
  }

  public static ExchangeMetaData toMetaData(WexExchangeInfo wexExchangeInfo, WexMetaData wexMetaData) {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    if (wexExchangeInfo != null) {
      for (Entry<String, WexPairInfo> e : wexExchangeInfo.getPairs().entrySet()) {
        CurrencyPair pair = adaptCurrencyPair(e.getKey());
        CurrencyPairMetaData marketMetaData = toMarketMetaData(e.getValue(), wexMetaData);
        currencyPairs.put(pair, marketMetaData);

        addCurrencyMetaData(pair.base, currencies, wexMetaData);
        addCurrencyMetaData(pair.counter, currencies, wexMetaData);
      }
    }

    RateLimit[] publicRateLimits = new RateLimit[]{new RateLimit(wexMetaData.publicInfoCacheSeconds, 1, TimeUnit.SECONDS)};
    return new ExchangeMetaData(currencyPairs, currencies, publicRateLimits, null, false);
  }

  private static void addCurrencyMetaData(Currency symbol, Map<Currency, CurrencyMetaData> currencies, WexMetaData wexMetaData) {
    if (!currencies.containsKey(symbol)) {
      BigDecimal withdrawalFee = wexMetaData.getCurrencies().get(symbol) == null ? null : wexMetaData.getCurrencies().get(symbol).getWithdrawalFee();
      currencies.put(symbol, new CurrencyMetaData(wexMetaData.amountScale, withdrawalFee));
    }
  }

  public static CurrencyPairMetaData toMarketMetaData(WexPairInfo info, WexMetaData wexMetaData) {
    int priceScale = info.getDecimals();
    BigDecimal minimumAmount = withScale(info.getMinAmount(), wexMetaData.amountScale);
    BigDecimal feeFraction = info.getFee().movePointLeft(2);

    return new CurrencyPairMetaData(feeFraction, minimumAmount, null, priceScale);
  }

  private static BigDecimal withScale(BigDecimal value, int priceScale) {
    /*
     * Last time I checked BTC-e returned an erroneous JSON result, where the minimum price for LTC/EUR was .0001 and the price scale was 3
     */
    try {
      return value.setScale(priceScale, RoundingMode.UNNECESSARY);
    } catch (ArithmeticException e) {
      log.debug("Could not round {} to {} decimal places: {}", value, priceScale, e.getMessage());
      return value.setScale(priceScale, RoundingMode.CEILING);
    }
  }

  public static String getPair(CurrencyPair currencyPair) {
    /* BTC-E signals DASH as DSH. This is a different coin. Translate in correct DASH name */
    String base = currencyPair.base.getCurrencyCode();
    String counter = currencyPair.counter.getCurrencyCode();
    if (base.equals("DASH")) {
      base = "DSH";
    } else if (counter.equals("DASH")) {
      counter = "DSH";
    }
    return (base + "_" + counter).toLowerCase();
  }

  public static LimitOrder createLimitOrder(MarketOrder marketOrder, WexExchangeInfo wexExchangeInfo) {
    WexPairInfo wexPairInfo = wexExchangeInfo.getPairs().get(getPair(marketOrder.getCurrencyPair()));
    BigDecimal limitPrice = marketOrder.getType() == OrderType.BID ? wexPairInfo.getMaxPrice() : wexPairInfo.getMinPrice();
    return LimitOrder.Builder.from(marketOrder).limitPrice(limitPrice).build();
  }

  public static List<FundingRecord> adaptFundingRecords(Map<Long, WexTransHistoryResult> map) {
    List<FundingRecord> fundingRecords = new ArrayList<>();

    for (Long key : map.keySet()) {
      WexTransHistoryResult result = map.get(key);

      FundingRecord.Status status = FundingRecord.Status.COMPLETE;

      if (result.getStatus().equals(WexTransHistoryResult.Status.entered))//looks like the enum has the wrong name maybe?
        status = FundingRecord.Status.FAILED;
      else if (result.getStatus().equals(WexTransHistoryResult.Status.waiting))
        status = FundingRecord.Status.PROCESSING;

      FundingRecord.Type type;//todo
      if (result.getType().equals(WexTransHistoryResult.Type.BTC_deposit))
        type = FundingRecord.Type.DEPOSIT;
      else if (result.getType().equals(WexTransHistoryResult.Type.BTC_withdrawal))
        type = FundingRecord.Type.WITHDRAWAL;
      else
        continue;

      Date date = DateUtils.fromUnixTime(result.getTimestamp());
      fundingRecords.add(new FundingRecord(
          null,
          date,
          Currency.getInstance(result.getCurrency()),
          result.getAmount(),
          String.valueOf(key),
          null,
          type,
          status,
          null,
          null,
          result.getDescription()
      ));
    }
    return fundingRecords;
  }
}
