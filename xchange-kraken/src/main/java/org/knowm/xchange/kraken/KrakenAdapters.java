package org.knowm.xchange.kraken;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.kraken.dto.account.KrakenDepositAddress;
import org.knowm.xchange.kraken.dto.account.KrakenLedger;
import org.knowm.xchange.kraken.dto.account.KrakenTradeVolume;
import org.knowm.xchange.kraken.dto.account.KrakenVolumeFee;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAsset;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPair;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.dto.marketdata.KrakenFee;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrade;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenOrderBookResult;
import org.knowm.xchange.kraken.dto.trade.*;

public class KrakenAdapters {

  public static OrderBook adaptOrderBook(KrakenDepth krakenDepth, CurrencyPair currencyPair) {

    OrdersContainer asksOrdersContainer =
        adaptOrders(krakenDepth.getAsks(), currencyPair, OrderType.ASK);
    OrdersContainer bidsOrdersContainer =
        adaptOrders(krakenDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(
        new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())),
        asksOrdersContainer.getLimitOrders(),
        bidsOrdersContainer.getLimitOrders());
  }

  public static OrderBook adaptFuturesOrderBook(KrakenOrderBookResult result, CurrencyPair currencyPair){
    KrakenDepth krakenDepth = result.getOrderbook();
    OrdersContainer asksOrdersContainer = adaptOrders(krakenDepth.getAsks(), currencyPair, OrderType.ASK);
    OrdersContainer bidsOrdersContainer = adaptOrders(krakenDepth.getBids(), currencyPair, OrderType.BID);

    Date date;
    try {
      date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'").parse(result.getServerTime());
    } catch (ParseException e) {
      date = new Date(System.currentTimeMillis());
    }

    return new OrderBook(
            date,
            asksOrdersContainer.getLimitOrders(),
            bidsOrdersContainer.getLimitOrders());
  }

  public static OrdersContainer adaptOrders(
      List<KrakenPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    long maxTimestamp = -1 * Long.MAX_VALUE;
    List<LimitOrder> limitOrders = new ArrayList<>(orders.size());

    for (KrakenPublicOrder order : orders) {
      if (order.getTimestamp() > maxTimestamp) {
        maxTimestamp = order.getTimestamp();
      }
      limitOrders.add(adaptOrder(order, orderType, currencyPair));
    }
    return new OrdersContainer(
        (String.valueOf(maxTimestamp).length() >= 13) ? maxTimestamp : maxTimestamp * 1000,
        limitOrders);
  }

  public static OpenPositions adaptOpenPositions(
      Map<String, KrakenOpenPosition> krakenOpenPositionMap) {
    List<OpenPosition> openPositionsList = new ArrayList<>();

    krakenOpenPositionMap
        .values()
        .forEach(
            krakenOpenPosition -> {
              openPositionsList.add(
                  new OpenPosition.Builder()
                      .instrument(new CurrencyPair(krakenOpenPosition.getAssetPair()))
                      .type(
                          krakenOpenPosition.getType() == KrakenType.BUY
                              ? OpenPosition.Type.LONG
                              : OpenPosition.Type.SHORT)
                      .size(krakenOpenPosition.getCost())
                      .price(
                          krakenOpenPosition
                              .getCost()
                              .divide(
                                  krakenOpenPosition
                                      .getVolume()
                                      .subtract(krakenOpenPosition.getVolumeClosed()),
                                  RoundingMode.HALF_EVEN))
                      .build());
            });

    return new OpenPositions(openPositionsList);
  }

  public static List<Order> adaptOrders(Map<String, KrakenOrder> krakenOrdersMap) {

    return krakenOrdersMap.entrySet().stream()
        .map(krakenOrderEntry -> adaptOrder(krakenOrderEntry.getKey(), krakenOrderEntry.getValue()))
        .collect(Collectors.toList());
  }

  public static Order adaptOrder(String orderId, KrakenOrder krakenOrder) {

    OrderType orderType = adaptOrderType(krakenOrder.getOrderDescription().getType());
    CurrencyPair currencyPair = adaptCurrencyPair(krakenOrder.getOrderDescription().getAssetPair());

    OrderStatus orderStatus = adaptOrderStatus(krakenOrder.getStatus());
    BigDecimal filledAmount = krakenOrder.getVolumeExecuted();
    BigDecimal originalAmount = krakenOrder.getVolume();
    BigDecimal fee = krakenOrder.getFee();

    if (orderStatus == OrderStatus.NEW
        && filledAmount.compareTo(BigDecimal.ZERO) > 0
        && filledAmount.compareTo(originalAmount) < 0) {
      orderStatus = OrderStatus.PARTIALLY_FILLED;
    }

    Double time = krakenOrder.getOpenTimestamp() * 1000; // eg: "opentm":1519731205.9987
    Date timestamp = new Date(time.longValue());

    if (krakenOrder.getOrderDescription().getOrderType().equals(KrakenOrderType.LIMIT))
      return new LimitOrder(
          orderType,
          krakenOrder.getVolume(),
          currencyPair,
          orderId,
          timestamp,
          krakenOrder.getOrderDescription().getPrice(),
          krakenOrder.getPrice(),
          krakenOrder.getVolumeExecuted(),
          fee,
          orderStatus,
          krakenOrder.getUserRefId());

    if (krakenOrder.getOrderDescription().getOrderType().equals(KrakenOrderType.MARKET))
      return new MarketOrder(
          orderType,
          krakenOrder.getVolume(),
          currencyPair,
          orderId,
          timestamp,
          krakenOrder.getPrice(),
          krakenOrder.getVolumeExecuted(),
          fee,
          orderStatus,
          krakenOrder.getUserRefId());

    throw new NotYetImplementedForExchangeException();
  }

  public static LimitOrder adaptOrder(
      KrakenPublicOrder order, OrderType orderType, CurrencyPair currencyPair) {

    Date timeStamp = new Date(order.getTimestamp() * 1000);
    BigDecimal volume = order.getVolume();

    return new LimitOrder(orderType, volume, currencyPair, "", timeStamp, order.getPrice());
  }

  public static Ticker adaptTicker(KrakenTicker krakenTicker, CurrencyPair currencyPair) {

    Ticker.Builder builder = new Ticker.Builder();
    builder.open(krakenTicker.getOpen());
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

  public static List<Ticker> adaptTickers(Map<String, KrakenTicker> krackenTickers) {
    List<Ticker> tickers = new ArrayList<>();
    for (Map.Entry<String, KrakenTicker> ticker : krackenTickers.entrySet()) {
      CurrencyPair pair = KrakenUtils.translateKrakenCurrencyPair(ticker.getKey());
      tickers.add(adaptTicker(ticker.getValue(), pair));
    }
    return tickers;
  }

  public static Trades adaptTrades(
      List<KrakenPublicTrade> krakenTrades, CurrencyPair currencyPair, long last) {

    List<Trade> trades = new ArrayList<>();
    for (KrakenPublicTrade krakenTrade : krakenTrades) {
      trades.add(adaptTrade(krakenTrade, currencyPair));
    }

    return new Trades(trades, last, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptTrade(KrakenPublicTrade krakenPublicTrade, CurrencyPair currencyPair) {

    OrderType type = adaptOrderType(krakenPublicTrade.getType());
    BigDecimal originalAmount = krakenPublicTrade.getVolume();
    Date timestamp = new Date((long) (krakenPublicTrade.getTime() * 1000L));

    return new Trade.Builder()
        .type(type)
        .originalAmount(originalAmount)
        .currencyPair(currencyPair)
        .price(krakenPublicTrade.getPrice())
        .timestamp(timestamp)
        .id(String.valueOf((long) (krakenPublicTrade.getTime() * 10000L)))
        .build();
  }

  public static Wallet adaptWallet(Map<String, BigDecimal> krakenWallet) {

    List<Balance> balances = new ArrayList<>(krakenWallet.size());
    for (Entry<String, BigDecimal> balancePair : krakenWallet.entrySet()) {
      Currency currency;
      try {
        currency = adaptCurrency(balancePair.getKey());
      } catch (Exception e) {
        currency = Currency.getInstance(balancePair.getKey());
      }

      Balance balance = new Balance(currency, balancePair.getValue());
      balances.add(balance);
    }
    return Wallet.Builder.from(balances).build();
  }

  public static Set<CurrencyPair> adaptCurrencyPairs(Collection<String> krakenCurrencyPairs) {

    Set<CurrencyPair> currencyPairs = new HashSet<>();
    for (String krakenCurrencyPair : krakenCurrencyPairs) {
      CurrencyPair currencyPair = adaptCurrencyPair(krakenCurrencyPair);
      if (currencyPair != null) {
        currencyPairs.add(currencyPair);
      }
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

      limitOrders.add((LimitOrder) adaptOrder(krakenOrderEntry.getKey(), krakenOrder));
    }
    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradesHistory(Map<String, KrakenTrade> krakenTrades) {

    List<UserTrade> trades = new ArrayList<>();
    for (Entry<String, KrakenTrade> krakenTradeEntry : krakenTrades.entrySet()) {
      trades.add(adaptTrade(krakenTradeEntry.getValue(), krakenTradeEntry.getKey()));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static KrakenUserTrade adaptTrade(KrakenTrade krakenTrade, String tradeId) {

    OrderType orderType = adaptOrderType(krakenTrade.getType());
    BigDecimal originalAmount = krakenTrade.getVolume();
    String krakenAssetPair = krakenTrade.getAssetPair();
    CurrencyPair pair = adaptCurrencyPair(krakenAssetPair);
    Date timestamp = new Date((long) (krakenTrade.getUnixTimestamp() * 1000L));
    BigDecimal averagePrice = krakenTrade.getAverageClosePrice();
    BigDecimal price = (averagePrice == null) ? krakenTrade.getPrice() : averagePrice;

    return new KrakenUserTrade(
        orderType,
        originalAmount,
        pair,
        price,
        timestamp,
        tradeId,
        krakenTrade.getOrderTxId(),
        krakenTrade.getFee(),
        pair.counter,
        krakenTrade.getCost());
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

  public static ExchangeMetaData adaptToExchangeMetaData(
      ExchangeMetaData originalMetaData,
      Map<String, KrakenAssetPair> krakenPairs,
      Map<String, KrakenAsset> krakenAssets) {

    Map<CurrencyPair, CurrencyPairMetaData> pairs = new HashMap<>();
    // add assets before pairs to Utils!
    KrakenUtils.setKrakenAssets(krakenAssets);
    KrakenUtils.setKrakenAssetPairs(krakenPairs);

    for (String krakenPairCode : krakenPairs.keySet()) {
      //  skip dark markets!
      if (!krakenPairCode.endsWith(".d")) {
        KrakenAssetPair krakenPair = krakenPairs.get(krakenPairCode);
        pairs.put(
            adaptCurrencyPair(krakenPairCode),
            adaptPair(krakenPair, pairs.get(adaptCurrencyPair(krakenPairCode))));
      }
    }

    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    for (String krakenAssetCode : krakenAssets.keySet()) {
      KrakenAsset krakenAsset = krakenAssets.get(krakenAssetCode);
      Currency currencyCode = KrakenAdapters.adaptCurrency(krakenAssetCode);
      currencies.put(currencyCode, new CurrencyMetaData(krakenAsset.getScale(), null));
    }

    return new ExchangeMetaData(
        pairs,
        currencies,
        originalMetaData == null ? null : originalMetaData.getPublicRateLimits(),
        originalMetaData == null ? null : originalMetaData.getPrivateRateLimits(),
        originalMetaData == null ? null : originalMetaData.isShareRateLimits());
  }

  public static Map<CurrencyPair, Fee> adaptFees(KrakenTradeVolume krakenTradeVolume) {
    Map<CurrencyPair, Fee> feeMap = new HashMap<>();

    // Compute Taker Fees
    for (Map.Entry<String, KrakenVolumeFee> entry : krakenTradeVolume.getFees().entrySet()) {
      feeMap.computeIfAbsent(
          KrakenUtils.translateKrakenCurrencyPair(entry.getKey()),
          currencyPair -> new Fee(null, entry.getValue().getFee().divide(new BigDecimal(100))));
    }

    // Compute Maker Fees
    for (Map.Entry<String, KrakenVolumeFee> entry : krakenTradeVolume.getFeesMaker().entrySet()) {
      feeMap.computeIfPresent(
          KrakenUtils.translateKrakenCurrencyPair(entry.getKey()),
          (currencyPair, fee) ->
              fee =
                  new Fee(
                      entry.getValue().getFee().divide(new BigDecimal(100)), fee.getTakerFee()));
      feeMap.computeIfAbsent(
          KrakenUtils.translateKrakenCurrencyPair(entry.getKey()),
          currencyPair -> new Fee(entry.getValue().getFee().divide(new BigDecimal(100)), null));
    }

    return feeMap;
  }

  protected static FeeTier[] adaptFeeTiers(List<KrakenFee> makerFees, List<KrakenFee> takerFees) {
    Collections.sort(makerFees);
    Collections.sort(takerFees);
    List<FeeTier> resultFeeTiers = new ArrayList<FeeTier>();
    int makerFeeIdx = 0;
    int takerFeeIdx = 0;

    while (makerFeeIdx < makerFees.size() || takerFeeIdx < takerFees.size()) {
      int curMakerIdx = Math.min(makerFeeIdx, makerFees.size() - 1);
      int curTakerIdx = Math.min(takerFeeIdx, takerFees.size() - 1);

      BigDecimal quantityMaker = makerFees.get(curMakerIdx).getVolume();
      BigDecimal quantityTaker = takerFees.get(curTakerIdx).getVolume();

      BigDecimal resultQuantity = null;
      BigDecimal resultMakerFee = null;
      BigDecimal resultTakerFee = null;
      int makerVolCompTakerVol = quantityMaker.compareTo(quantityTaker);
      if ((makerVolCompTakerVol > 0 || makerFeeIdx >= makerFees.size())
          && takerFeeIdx < takerFees.size()) {
        if (makerFeeIdx < 1) {
          throw new IllegalStateException(
              "Kraken exchange specified fee tiers such that the maker fee was unspecified before a nonzero quantity was traded.");
        }
        KrakenFee takerFeeData = takerFees.get(curTakerIdx);
        resultTakerFee = takerFeeData.getPercentFee();
        resultMakerFee = makerFees.get(makerFeeIdx - 1).getPercentFee();
        resultQuantity = takerFeeData.getVolume();
        takerFeeIdx++;
      } else if ((makerVolCompTakerVol < 0 || takerFeeIdx >= takerFees.size())
          && makerFeeIdx < makerFees.size()) {
        if (takerFeeIdx < 1) {
          throw new IllegalStateException(
              "Kraken exchange specified fee tiers such that the taker fee was unspecified before a nonzero quantity was traded.");
        }
        KrakenFee makerFeeData = makerFees.get(curMakerIdx);
        resultMakerFee = makerFeeData.getPercentFee();
        resultTakerFee = takerFees.get(takerFeeIdx - 1).getPercentFee();
        resultQuantity = makerFeeData.getVolume();
        makerFeeIdx++;
      } else { // makerVolCompTakerVol == 0 && makerFeeIdx < makerFees.size() && takerFeeIdx <
        // takerFees.size()
        KrakenFee makerFeeData = makerFees.get(curMakerIdx);
        resultMakerFee = makerFeeData.getPercentFee();
        resultTakerFee = takerFees.get(curTakerIdx).getPercentFee();
        resultQuantity = makerFeeData.getVolume();

        takerFeeIdx++;
        makerFeeIdx++;
      }
      resultFeeTiers.add(
          new FeeTier(
              resultQuantity,
              new Fee(resultMakerFee.movePointLeft(2), resultTakerFee.movePointLeft(2))));
    }

    return resultFeeTiers.toArray(new FeeTier[resultFeeTiers.size()]);
  }

  private static CurrencyPairMetaData adaptPair(
      KrakenAssetPair krakenPair, CurrencyPairMetaData OriginalMeta) {
    return new CurrencyPairMetaData(
        krakenPair.getFees().get(0).getPercentFee().divide(new BigDecimal(100)),
        krakenPair.getOrderMin(),
        null,
        krakenPair.getPairScale(),
        krakenPair.getVolumeLotScale(),
        adaptFeeTiers(krakenPair.getFees_maker(), krakenPair.getFees()),
        KrakenUtils.translateKrakenCurrencyCode(krakenPair.getFeeVolumeCurrency()));
  }

  public static List<FundingRecord> adaptFundingHistory(
      Map<String, KrakenLedger> krakenLedgerInfo) {

    final List<FundingRecord> fundingRecords = new ArrayList<>();
    for (Entry<String, KrakenLedger> ledgerEntry : krakenLedgerInfo.entrySet()) {
      final KrakenLedger krakenLedger = ledgerEntry.getValue();
      if (krakenLedger.getLedgerType() != null) {
        final Currency currency = adaptCurrency(krakenLedger.getAsset());
        if (currency != null) {
          final Date timestamp = new Date((long) (krakenLedger.getUnixTime() * 1000L));
          final FundingRecord.Type type =
              FundingRecord.Type.fromString(krakenLedger.getLedgerType().name());
          if (type != null) {
            final String internalId = krakenLedger.getRefId(); // or ledgerEntry.getKey()?
            FundingRecord fundingRecordEntry =
                new FundingRecord(
                    null,
                    timestamp,
                    currency,
                    krakenLedger.getTransactionAmount(),
                    internalId,
                    null,
                    FundingRecord.Type.fromString(krakenLedger.getLedgerType().name()),
                    FundingRecord.Status.COMPLETE,
                    krakenLedger.getBalance(),
                    krakenLedger.getFee(),
                    null);
            fundingRecords.add(fundingRecordEntry);
          }
        }
      }
    }
    return fundingRecords;
  }

  public static OrderStatus adaptOrderStatus(KrakenOrderStatus status) {
    switch (status) {
      case PENDING:
        return OrderStatus.PENDING_NEW;
      case OPEN:
        return OrderStatus.NEW;
      case CLOSED:
        return OrderStatus.FILLED;
      case CANCELED:
        return OrderStatus.CANCELED;
      case EXPIRED:
        return OrderStatus.EXPIRED;
      default:
        return null;
    }
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
}
