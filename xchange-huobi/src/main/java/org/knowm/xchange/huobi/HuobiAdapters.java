package org.knowm.xchange.huobi;

import java.math.BigDecimal;
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
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.huobi.dto.account.HuobiBalanceRecord;
import org.knowm.xchange.huobi.dto.account.HuobiBalanceSum;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAsset;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAssetPair;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;

public class HuobiAdapters {

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

  private static CurrencyPairMetaData adaptPair(HuobiAssetPair pair, CurrencyPairMetaData metadata) {
    BigDecimal minQty = metadata == null ? null : metadata.getMinimumAmount();
      
    return new CurrencyPairMetaData(
    		new BigDecimal("0.002"), // Trading fee at Huobi is 0.2 % 
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
    	    // API returns orders history with all statuses. Add only completed orders...
    	    if (order.getStatus() != OrderStatus.CANCELED &&
    	    		order.getStatus() != OrderStatus.FILLED &&
    	    		order.getStatus() != OrderStatus.REJECTED)
    	    {
    	    		limitOrders.add(order);
    	    }
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
      order.setAveragePrice(
          openOrder
              .getFieldCashAmount()
              .divide(openOrder.getFieldAmount(), 8, BigDecimal.ROUND_DOWN));
    }
    if (order != null) {
      order.setOrderStatus(adaptOrderStatus(openOrder.getState()));
    }
    return order;
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
}
