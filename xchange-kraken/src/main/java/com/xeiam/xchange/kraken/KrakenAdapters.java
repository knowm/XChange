package com.xeiam.xchange.kraken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.*;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.kraken.dto.account.KrakenDepositAddress;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicOrder;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrade;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderDescription;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResponse;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;
import com.xeiam.xchange.kraken.dto.trade.KrakenUserTrade;

public class KrakenAdapters {

  public static OrderBook adaptOrderBook(KrakenDepth krakenDepth, CurrencyPair currencyPair) {

    OrdersContainer asksOrdersContainer = adaptOrders(krakenDepth.getAsks(), currencyPair, OrderType.ASK);
    OrdersContainer bidsOrdersContainer = adaptOrders(krakenDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())),
        asksOrdersContainer.getLimitOrders(), bidsOrdersContainer.getLimitOrders());
  }

  public static OrdersContainer adaptOrders(List<KrakenPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    long maxTimestamp = -1 * Long.MAX_VALUE;
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.size());

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

    List<Trade> trades = new ArrayList<Trade>();
    for (KrakenPublicTrade krakenTrade : krakenTrades) {
      trades.add(adaptTrade(krakenTrade, currencyPair));
    }

    return new Trades(trades, last, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptTrade(KrakenPublicTrade krakenPublicTrade, CurrencyPair currencyPair) {

    OrderType type = adaptOrderType(krakenPublicTrade.getType());
    BigDecimal tradableAmount = krakenPublicTrade.getVolume();
    Date timestamp = new Date((long) (krakenPublicTrade.getTime() * 1000L));

    return new Trade(type, tradableAmount, currencyPair, krakenPublicTrade.getPrice(), timestamp, "0");
  }

  public static Wallet adaptWallet(Map<String, BigDecimal> krakenWallet) {

    List<Balance> balances = new ArrayList<Balance>(krakenWallet.size());
    for (Entry<String, BigDecimal> balancePair : krakenWallet.entrySet()) {
      Currency currency = adaptCurrency(balancePair.getKey());
      Balance balance = new Balance(currency, balancePair.getValue());
      balances.add(balance);
    }
    return new Wallet(balances);
  }

  public static Set<CurrencyPair> adaptCurrencyPairs(Collection<String> krakenCurrencyPairs) {

    Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();
    for (String krakenCurrencyPair : krakenCurrencyPairs) {
      currencyPairs.add(adaptCurrencyPair(krakenCurrencyPair));
    }
    return currencyPairs;
  }

  public static Currency adaptCurrency(String krakenCurrencyCode) {

    String currencyCode = (krakenCurrencyCode.length() == 4) ? krakenCurrencyCode.substring(1) : krakenCurrencyCode;

    return Currency.getInstance(currencyCode).getCommonlyUsedCurrency();
  }

  public static CurrencyPair adaptCurrencyPair(String krakenCurrencyPair) {

    Currency firstCurrency = adaptCurrency(krakenCurrencyPair.substring(0, 4));
    Currency secondCurrency = adaptCurrency(krakenCurrencyPair.substring(4));

    return new CurrencyPair(firstCurrency, secondCurrency);
  }

  public static OpenOrders adaptOpenOrders(Map<String, KrakenOrder> krakenOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
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
    Currency tradableIdentifier = adaptCurrency(orderDescription.getAssetPair().substring(0, 3));
    Currency transactionCurrency = adaptCurrency(orderDescription.getAssetPair().substring(3));
    Date timestamp = new Date((long) (krakenOrder.getOpenTimestamp() * 1000L));

    return new LimitOrder(type, tradableAmount, new CurrencyPair(tradableIdentifier, transactionCurrency), id, timestamp,
        orderDescription.getPrice());
  }

  public static UserTrades adaptTradesHistory(Map<String, KrakenTrade> krakenTrades) {

    List<UserTrade> trades = new ArrayList<UserTrade>();
    for (Entry<String, KrakenTrade> krakenTradeEntry : krakenTrades.entrySet()) {
      trades.add(adaptTrade(krakenTradeEntry.getValue(), krakenTradeEntry.getKey()));
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static KrakenUserTrade adaptTrade(KrakenTrade krakenTrade, String tradeId) {

    OrderType orderType = adaptOrderType(krakenTrade.getType());
    BigDecimal tradableAmount = krakenTrade.getVolume();
    String krakenAssetPair = krakenTrade.getAssetPair();
    Currency tradableIdentifier = adaptCurrency(krakenAssetPair.substring(0, 4));
    Currency transactionCurrency = adaptCurrency(krakenAssetPair.substring(4));
    Date timestamp = new Date((long) (krakenTrade.getUnixTimestamp() * 1000L));
    BigDecimal averagePrice = krakenTrade.getAverageClosePrice();
    BigDecimal price = (averagePrice == null) ? krakenTrade.getPrice() : averagePrice;

    return new KrakenUserTrade(orderType, tradableAmount, new CurrencyPair(tradableIdentifier, transactionCurrency), price, timestamp, tradeId,
        krakenTrade.getOrderTxId(), krakenTrade.getFee(), transactionCurrency, krakenTrade.getCost());
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
}
