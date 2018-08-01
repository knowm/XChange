package org.knowm.xchange.huobi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.huobi.dto.account.HuobiBalanceRecord;
import org.knowm.xchange.huobi.dto.account.HuobiBalanceSum;
import org.knowm.xchange.huobi.dto.account.HuobiFundingRecord;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAsset;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAssetPair;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;

public class HuobiAdapters {

  private static BigDecimal fee = new BigDecimal("0.002"); // Trading fee at Huobi is 0.2 %

  public static Ticker adaptTicker(HuobiTicker huobiTicker, CurrencyPair currencyPair) {
    Ticker.Builder builder = new Ticker.Builder();
    builder.open(huobiTicker.getOpen());
    builder.ask(huobiTicker.getAsk().getPrice());
    builder.bid(huobiTicker.getBid().getPrice());
    builder.last(huobiTicker.getClose());
    builder.high(huobiTicker.getHigh());
    builder.low(huobiTicker.getLow());
    builder.volume(huobiTicker.getVol());
    builder.timestamp(huobiTicker.getTs());
    builder.currencyPair(currencyPair);
    return builder.build();
  }

  static ExchangeMetaData adaptToExchangeMetaData(
      HuobiAssetPair[] assetPairs, HuobiAsset[] assets, ExchangeMetaData staticMetaData) {

    HuobiUtils.setHuobiAssets(assets);
    HuobiUtils.setHuobiAssetPairs(assetPairs);

    Map<CurrencyPair, CurrencyPairMetaData> pairsMetaData = staticMetaData.getCurrencyPairs();
    Map<CurrencyPair, CurrencyPairMetaData> pairs = new HashMap<>();
    for (HuobiAssetPair assetPair : assetPairs) {
      CurrencyPair pair = adaptCurrencyPair(assetPair.getKey());
      pairs.put(pair, adaptPair(assetPair, pairsMetaData.getOrDefault(pair, null)));
    }

    Map<Currency, CurrencyMetaData> currenciesMetaData = staticMetaData.getCurrencies();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    for (HuobiAsset asset : assets) {
      Currency currency = adaptCurrency(asset.getAsset());
      CurrencyMetaData metadata = currenciesMetaData.getOrDefault(currency, null);
      BigDecimal withdrawalFee = metadata == null ? null : metadata.getWithdrawalFee();
      int scale = metadata == null ? 8 : metadata.getScale();
      currencies.put(currency, new CurrencyMetaData(scale, withdrawalFee));
    }

    return new ExchangeMetaData(pairs, currencies, null, null, false);
  }

  private static CurrencyPair adaptCurrencyPair(String currencyPair) {
    return HuobiUtils.translateHuobiCurrencyPair(currencyPair);
  }

  private static CurrencyPairMetaData adaptPair(
      HuobiAssetPair pair, CurrencyPairMetaData metadata) {
    BigDecimal minQty = metadata == null ? null : metadata.getMinimumAmount();

    return new CurrencyPairMetaData(
        fee,
        minQty, // Min amount
        null, // Max amount
        new Integer(pair.getPricePrecision()) // Price scale
        );
  }

  private static Currency adaptCurrency(String currency) {
    return HuobiUtils.translateHuobiCurrencyCode(currency);
  }

  public static Wallet adaptWallet(Map<String, HuobiBalanceSum> huobiWallet) {
    List<Balance> balances = new ArrayList<>(huobiWallet.size());
    for (Map.Entry<String, HuobiBalanceSum> record : huobiWallet.entrySet()) {
      Currency currency = adaptCurrency(record.getKey());
      Balance balance =
          new Balance(
              currency,
              record.getValue().getTotal(),
              record.getValue().getAvailable(),
              record.getValue().getFrozen());
      balances.add(balance);
    }
    return new Wallet(balances);
  }

  public static Map<String, HuobiBalanceSum> adaptBalance(HuobiBalanceRecord[] huobiBalance) {
    Map<String, HuobiBalanceSum> map = new HashMap<>();
    for (HuobiBalanceRecord record : huobiBalance) {
      HuobiBalanceSum sum = map.get(record.getCurrency());
      if (sum == null) {
        sum = new HuobiBalanceSum();
        map.put(record.getCurrency(), sum);
      }
      if (record.getType().equals("trade")) {
        sum.setAvailable(record.getBalance());
      } else if (record.getType().equals("frozen")) {
        sum.setFrozen(record.getBalance());
      }
    }
    return map;
  }

  public static OpenOrders adaptOpenOrders(HuobiOrder[] openOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (HuobiOrder openOrder : openOrders) {
      if (openOrder.isLimit()) {
        LimitOrder order = (LimitOrder) adaptOrder(openOrder);
        limitOrders.add(order);
      }
    }
    return new OpenOrders(limitOrders);
  }

  private static Order adaptOrder(HuobiOrder openOrder) {
    Order order = null;
    OrderType orderType = adaptOrderType(openOrder.getType());
    CurrencyPair currencyPair = adaptCurrencyPair(openOrder.getSymbol());
    if (openOrder.isMarket()) {
      order =
          new MarketOrder(
              orderType,
              openOrder.getAmount(),
              currencyPair,
              String.valueOf(openOrder.getId()),
              openOrder.getCreatedAt(),
              openOrder
                  .getFieldCashAmount()
                  .divide(openOrder.getFieldAmount(), 8, BigDecimal.ROUND_DOWN),
              openOrder.getFieldAmount(),
              openOrder.getFieldFees(),
              null);
    }
    if (openOrder.isLimit()) {
      order =
          new LimitOrder(
              orderType,
              openOrder.getAmount(),
              openOrder.getFieldAmount(),
              currencyPair,
              String.valueOf(openOrder.getId()),
              openOrder.getCreatedAt(),
              openOrder.getPrice());

      if (openOrder.getFieldAmount().compareTo(BigDecimal.ZERO) > 0) {
        order.setAveragePrice(
            openOrder
                .getFieldCashAmount()
                .divide(openOrder.getFieldAmount(), 8, BigDecimal.ROUND_DOWN));
      }
    }
    if (order != null) {
      order.setOrderStatus(adaptOrderStatus(openOrder.getState()));
    }
    return order;
  }

  /**
   * Huobi currently doesn't have trade history API. We simulate it by using the orders history.
   *
   * @param order
   * @return
   */
  private static UserTrade adaptTrade(LimitOrder order) {
    BigDecimal feeAmount =
        order
            .getCumulativeAmount()
            .multiply(order.getLimitPrice())
            .multiply(fee)
            .setScale(8, RoundingMode.DOWN);
    return new UserTrade(
        order.getType(),
        order.getCumulativeAmount(),
        order.getCurrencyPair(),
        order.getLimitPrice(),
        order.getTimestamp(),
        null, // Trade id
        order.getId(), // Original order id
        feeAmount,
        order.getCurrencyPair().counter);
  }

  private static OrderStatus adaptOrderStatus(String huobiStatus) {
    OrderStatus result = OrderStatus.UNKNOWN;
    switch (huobiStatus) {
      case "pre-submitted":
        result = OrderStatus.PENDING_NEW;
        break;
      case "submitting":
        result = OrderStatus.PENDING_NEW;
        break;
      case "submitted":
        result = OrderStatus.NEW;
        break;
      case "partial-filled":
        result = OrderStatus.PARTIALLY_FILLED;
        break;
      case "partial-canceled":
        result = OrderStatus.PARTIALLY_CANCELED;
        break;
      case "filled":
        result = OrderStatus.FILLED;
        break;
      case "canceled":
        result = OrderStatus.CANCELED;
        break;
    }
    return result;
  }

  private static OrderType adaptOrderType(String orderType) {
    if (orderType.startsWith("buy")) {
      return OrderType.BID;
    }
    if (orderType.startsWith("sell")) {
      return OrderType.ASK;
    }
    return null;
  }

  public static List<Order> adaptOrders(List<HuobiOrder> huobiOrders) {
    List<Order> orders = new ArrayList<>();
    for (HuobiOrder order : huobiOrders) {
      orders.add(adaptOrder(order));
    }
    return orders;
  }

  public static UserTrades adaptTradeHistory(HuobiOrder[] openOrders) {
    OpenOrders orders = adaptOpenOrders(openOrders);
    List<UserTrade> trades = new ArrayList<>();
    for (LimitOrder order : orders.getOpenOrders()) {
      trades.add(adaptTrade(order));
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static List<FundingRecord> adaptFundingHistory(HuobiFundingRecord[] fundingRecords) {
    List<FundingRecord> records = new ArrayList<>();
    for (HuobiFundingRecord record : fundingRecords) {
      records.add(adaptFundingRecord(record));
    }
    return records;
  }

  public static FundingRecord adaptFundingRecord(HuobiFundingRecord r) {

    return new FundingRecord(
        r.getAddress(),
        r.getCreatedAt(),
        Currency.getInstance(r.getCurrency()),
        r.getAmount(),
        Long.toString(r.getId()),
        r.getTxhash(),
        r.getType(),
        adaptFundingStatus(r),
        null,
        r.getFee(),
        null);
  }

  private static Status adaptFundingStatus(HuobiFundingRecord record) {
    if (record.getType() == FundingRecord.Type.WITHDRAWAL) {
      return adaptWithdrawalStatus(record.getState());
    }
    return adaptDepostStatus(record.getState());
  }

  private static Status adaptWithdrawalStatus(String state) {
    switch (state) {
      case "pre-transfer":
      case "submitted":
      case "reexamine":
      case "pass":
      case "wallet-transfer":
        return Status.PROCESSING;
      case "canceled":
        return Status.CANCELLED;
      case "confirmed":
        return Status.COMPLETE;
      case "wallet-reject":
      case "reject	":
      case "confirm-error":
        return Status.FAILED;
      case "repealed":

      default:
        return null;
    }
  }

  private static Status adaptDepostStatus(String state) {
    switch (state) {
      case "confirming":
      case "safe":
        return Status.PROCESSING;
      case "confirmed":
        return Status.COMPLETE;
      case "unknown":
      case "orphan":
        return Status.FAILED;
      default:
        return null;
    }
  }
}
