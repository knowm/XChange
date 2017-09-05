package org.knowm.xchange.kraken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
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
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kraken.dto.account.KrakenDepositAddress;
import org.knowm.xchange.kraken.dto.account.KrakenLedger;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAsset;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPair;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrade;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderDescription;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderResponse;
import org.knowm.xchange.kraken.dto.trade.KrakenTrade;
import org.knowm.xchange.kraken.dto.trade.KrakenType;
import org.knowm.xchange.kraken.dto.trade.KrakenUserTrade;

public class KrakenAdapters {

  public static OrderBook adaptOrderBook(KrakenDepth krakenDepth, CurrencyPair currencyPair) {

    OrdersContainer asksOrdersContainer = adaptOrders(krakenDepth.getAsks(), currencyPair, OrderType.ASK);
    OrdersContainer bidsOrdersContainer = adaptOrders(krakenDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())),
        asksOrdersContainer.getLimitOrders(), bidsOrdersContainer.getLimitOrders());
  }

  public static OrdersContainer adaptOrders(List<KrakenPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    long maxTimestamp = -1 * Long.MAX_VALUE;
    List<LimitOrder> limitOrders = new ArrayList<>(orders.size());

    for (KrakenPublicOrder order : orders) {
      if (order.getTimestamp() > maxTimestamp) {
        maxTimestamp = order.getTimestamp();
      }
      limitOrders.add(adaptOrder(order, orderType, currencyPair));
    }
    return new OrdersContainer(maxTimestamp * 1000, limitOrders);

  }

  public static class OrdersContainer {

    private final long timestamp;
    private final List<LimitOrder> limitOrders;

    /**
     * Constructor
     *
     * @param timestamp
     * @param limitOrders
     */
    public OrdersContainer(long timestamp, List<LimitOrder> limitOrders) {

      this.timestamp = timestamp;
      this.limitOrders = limitOrders;
    }

    public long getTimestamp() {

      return timestamp;
    }

    public List<LimitOrder> getLimitOrders() {

      return limitOrders;
    }
  }

  public static LimitOrder adaptOrder(KrakenPublicOrder order, OrderType orderType, CurrencyPair currencyPair) {

    Date timeStamp = new Date(order.getTimestamp() * 1000);
    BigDecimal volume = order.getVolume();

    return new LimitOrder(orderType, volume, currencyPair, "", timeStamp, order.getPrice());
  }

  public static Ticker adaptTicker(KrakenTicker krakenTicker, CurrencyPair currencyPair) {

    Ticker.Builder builder = new Ticker.Builder();
    builder.ask(krakenTicker.getAsk().getPrice());
    builder.bid(krakenTicker.getBid().getPrice());
    builder.last(krakenTicker.getClose().getPrice());
    builder.high(krakenTicker.get24HourHigh());
    builder.low(krakenTicker.get24HourLow());
    builder.vwap(krakenTicker.get24HourVolumeAvg());
    builder.volume(krakenTicker.get24HourVolume());
    builder.currencyPair(currencyPair);
    return builder.build();
  }

  public static Trades adaptTrades(List<KrakenPublicTrade> krakenTrades, CurrencyPair currencyPair, long last) {

    List<Trade> trades = new ArrayList<>();
    for (KrakenPublicTrade krakenTrade : krakenTrades) {
      trades.add(adaptTrade(krakenTrade, currencyPair));
    }

    return new Trades(trades, last, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptTrade(KrakenPublicTrade krakenPublicTrade, CurrencyPair currencyPair) {

    OrderType type = adaptOrderType(krakenPublicTrade.getType());
    BigDecimal tradableAmount = krakenPublicTrade.getVolume();
    Date timestamp = new Date((long) (krakenPublicTrade.getTime() * 1000L));

    return new Trade(type, tradableAmount, currencyPair, krakenPublicTrade.getPrice(), timestamp, String.valueOf((long) (krakenPublicTrade.getTime() *
        10000L)));
  }

  public static Wallet adaptWallet(Map<String, BigDecimal> krakenWallet) {

    List<Balance> balances = new ArrayList<>(krakenWallet.size());
    for (Entry<String, BigDecimal> balancePair : krakenWallet.entrySet()) {
      Currency currency = adaptCurrency(balancePair.getKey());
      Balance balance = new Balance(currency, balancePair.getValue());
      balances.add(balance);
    }
    return new Wallet(balances);
  }

  public static Set<CurrencyPair> adaptCurrencyPairs(Collection<String> krakenCurrencyPairs) {

    Set<CurrencyPair> currencyPairs = new HashSet<>();
    for (String krakenCurrencyPair : krakenCurrencyPairs) {
      currencyPairs.add(adaptCurrencyPair(krakenCurrencyPair));
    }
    return currencyPairs;
  }

  public static Currency adaptCurrency(String krakenCurrencyCode) {
    return KrakenUtils.translateKrakenCurrencyCode(krakenCurrencyCode);
  }

  public static CurrencyPair adaptCurrencyPair(String krakenCurrencyPair) {
    return KrakenUtils.translateKrakenCurrencyPair(krakenCurrencyPair);
  }

  public static OpenOrders adaptOpenOrders(Map<String, KrakenOrder> krakenOrders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (Entry<String, KrakenOrder> krakenOrderEntry : krakenOrders.entrySet()) {
      KrakenOrder krakenOrder = krakenOrderEntry.getValue();
      KrakenOrderDescription orderDescription = krakenOrder.getOrderDescription();

      if (!"limit".equals(orderDescription.getOrderType().toString())) {
        // how to handle stop-loss, take-profit, stop-loss-limit, and so on orders?
        // ignore anything but a plain limit order for now
        continue;
      }

      limitOrders.add(adaptLimitOrder(krakenOrder, krakenOrderEntry.getKey()));
    }
    return new OpenOrders(limitOrders);

  }

  public static LimitOrder adaptLimitOrder(KrakenOrder krakenOrder, String id) {

    KrakenOrderDescription orderDescription = krakenOrder.getOrderDescription();
    OrderType type = adaptOrderType(orderDescription.getType());
    BigDecimal tradableAmount = krakenOrder.getVolume().subtract(krakenOrder.getVolumeExecuted());
    CurrencyPair pair = adaptCurrencyPair(orderDescription.getAssetPair());
    Date timestamp = new Date((long) (krakenOrder.getOpenTimestamp() * 1000L));

    return new LimitOrder(type, tradableAmount, pair, id, timestamp,
        orderDescription.getPrice());
  }

  public static UserTrades adaptTradesHistory(Map<String, KrakenTrade> krakenTrades) {

    List<UserTrade> trades = new ArrayList<>();
    for (Entry<String, KrakenTrade> krakenTradeEntry : krakenTrades.entrySet()) {
      trades.add(adaptTrade(krakenTradeEntry.getValue(), krakenTradeEntry.getKey()));
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static KrakenUserTrade adaptTrade(KrakenTrade krakenTrade, String tradeId) {

    OrderType orderType = adaptOrderType(krakenTrade.getType());
    BigDecimal tradableAmount = krakenTrade.getVolume();
    String krakenAssetPair = krakenTrade.getAssetPair();
    CurrencyPair pair = adaptCurrencyPair(krakenAssetPair);
    Date timestamp = new Date((long) (krakenTrade.getUnixTimestamp() * 1000L));
    BigDecimal averagePrice = krakenTrade.getAverageClosePrice();
    BigDecimal price = (averagePrice == null) ? krakenTrade.getPrice() : averagePrice;

    return new KrakenUserTrade(orderType, tradableAmount, pair, price, timestamp, tradeId,
        krakenTrade.getOrderTxId(), krakenTrade.getFee(), pair.counter, krakenTrade.getCost());
  }

  public static OrderType adaptOrderType(KrakenType krakenType) {

    return krakenType.equals(KrakenType.BUY) ? OrderType.BID : OrderType.ASK;
  }

  public static String adaptKrakenDepositAddress(KrakenDepositAddress[] krakenDepositAddress) {
    return krakenDepositAddress[0].getAddress();
  }

  public static String adaptOrderId(KrakenOrderResponse orderResponse) {

    List<String> orderIds = orderResponse.getTransactionIds();
    return (orderIds == null || orderIds.isEmpty()) ? "" : orderIds.get(0);
  }

  public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData originalMetaData, Map<String, KrakenAssetPair> krakenPairs,
      Map<String, KrakenAsset> krakenAssets) {

    Map<CurrencyPair, CurrencyPairMetaData> pairs = new HashMap<>();
    // add assets before pairs to Utils!
    KrakenUtils.setKrakenAssets(krakenAssets);
    KrakenUtils.setKrakenAssetPairs(krakenPairs);

    pairs.putAll(originalMetaData.getCurrencyPairs());
    for (String krakenPairCode : krakenPairs.keySet()) {
      //  skip dark markets!
      if (!krakenPairCode.endsWith(".d")) {
        KrakenAssetPair krakenPair = krakenPairs.get(krakenPairCode);
        pairs.put(adaptCurrencyPair(krakenPairCode), adaptPair(krakenPair, pairs.get(adaptCurrencyPair(krakenPairCode))));
      }
    }

    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    currencies.putAll(originalMetaData.getCurrencies());
    for (String krakenAssetCode : krakenAssets.keySet()) {
      KrakenAsset krakenAsset = krakenAssets.get(krakenAssetCode);
      Currency currencyCode = KrakenAdapters.adaptCurrency(krakenAssetCode);
      currencies.put(currencyCode, new CurrencyMetaData(krakenAsset.getScale()));
    }

    return new ExchangeMetaData(pairs, currencies, originalMetaData == null ? null : originalMetaData.getPublicRateLimits(),
        originalMetaData == null ? null : originalMetaData.getPrivateRateLimits(),
        originalMetaData == null ? null : originalMetaData.isShareRateLimits());
  }

  private static CurrencyPairMetaData adaptPair(KrakenAssetPair krakenPair, CurrencyPairMetaData OriginalMeta) {
    if (OriginalMeta != null) {
      return new CurrencyPairMetaData(krakenPair.getFees().get(0).getPercentFee().divide(new BigDecimal(100)), OriginalMeta.getMinimumAmount(),
          OriginalMeta.getMaximumAmount(), krakenPair.getPairScale());
    } else {
      return new CurrencyPairMetaData(krakenPair.getFees().get(0).getPercentFee().divide(new BigDecimal(100)), null, null, krakenPair.getPairScale());
    }
  }

  public static List<FundingRecord> adaptFundingHistory(Map<String, KrakenLedger> krakenLedgerInfo) {

    final List<FundingRecord> fundingRecords = new ArrayList<>();
    for (Entry<String, KrakenLedger> ledgerEntry : krakenLedgerInfo.entrySet()) {
      final KrakenLedger krakenLedger = ledgerEntry.getValue();
      if (krakenLedger.getLedgerType() != null) {
        final Currency currency = adaptCurrency(krakenLedger.getAsset());
        if (currency != null) {
          final Date timestamp = new Date((long) (krakenLedger.getUnixTime() * 1000L));
          final FundingRecord.Type type = FundingRecord.Type.fromString(krakenLedger.getLedgerType().name());
          if (type != null) {
            final String internalId = krakenLedger.getRefId(); // or ledgerEntry.getKey()?
            FundingRecord fundingRecordEntry = new FundingRecord(null, timestamp,
                currency, krakenLedger.getTransactionAmount(), internalId, null,
                FundingRecord.Type.fromString(krakenLedger.getLedgerType().name()),
                FundingRecord.Status.COMPLETE, krakenLedger.getBalance(), krakenLedger.getFee(), null);
            fundingRecords.add(fundingRecordEntry);
          }
        }
      }
    }
    return fundingRecords;
  }
}
