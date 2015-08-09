package com.xeiam.xchange.hitbtc;

import static com.xeiam.xchange.currency.Currencies.DOGE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;
import com.xeiam.xchange.dto.meta.MarketMetaData;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbol;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrade;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades;
import com.xeiam.xchange.hitbtc.dto.meta.HitbtcMetaData;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;

public class HitbtcAdapters {

  public static final char DELIM = '_';

  /**
   * Singleton
   */
  private HitbtcAdapters() {

  }

  public static List<CurrencyPair> adaptCurrencyPairs(HitbtcSymbols hitbtcSymbols) {

    List<CurrencyPair> currencyPairList = new ArrayList<CurrencyPair>();

    for (HitbtcSymbol hitbtcSymbol : hitbtcSymbols.getHitbtcSymbols()) {
      currencyPairList.add(adaptSymbol(hitbtcSymbol));
    }

    return currencyPairList;
  }

  public static CurrencyPair adaptSymbol(String symbolString) {

    if (symbolString.startsWith(DOGE)) {
      String counterSymbol = symbolString.substring(4);
      return new CurrencyPair(DOGE, counterSymbol);
    } else {
      String base = symbolString.substring(0, 3);
      String counterSymbol = symbolString.substring(3);
      return new CurrencyPair(base, counterSymbol);
    }
  }

  public static CurrencyPair adaptSymbol(HitbtcSymbol hitbtcSymbol) {

    return new CurrencyPair(hitbtcSymbol.getCommodity(), hitbtcSymbol.getCurrency());
  }

  /**
   * Adapts a HitbtcTicker to a Ticker Object
   *
   * @param hitbtcTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(HitbtcTicker hitbtcTicker, CurrencyPair currencyPair) {

    BigDecimal bid = hitbtcTicker.getBid();
    BigDecimal ask = hitbtcTicker.getAsk();
    BigDecimal high = hitbtcTicker.getHigh();
    BigDecimal low = hitbtcTicker.getLow();
    BigDecimal last = hitbtcTicker.getLast();
    BigDecimal volume = hitbtcTicker.getVolume();
    Date timestamp = new Date(hitbtcTicker.getTimetamp());

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();
  }

  public static OrderBook adaptOrderBook(HitbtcOrderBook hitbtcOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptMarketOrderToLimitOrder(hitbtcOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptMarketOrderToLimitOrder(hitbtcOrderBook.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(BigDecimal[][] hitbtcOrders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>(hitbtcOrders.length);

    for (int i = 0; i < hitbtcOrders.length; i++) {
      BigDecimal[] hitbtcOrder = hitbtcOrders[i];

      BigDecimal price = hitbtcOrder[0];
      BigDecimal amount = hitbtcOrder[1];

      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, null, price);
      orders.add(limitOrder);
    }

    return orders;
  }

  public static Trades adaptTrades(HitbtcTrades hitbtcTrades, CurrencyPair currencyPair) {

    HitbtcTrade[] allHitbtcTrades = hitbtcTrades.getHitbtcTrades();
    List<Trade> trades = new ArrayList<Trade>(allHitbtcTrades.length);
    long lastTradeId = 0;
    for (int i = 0; i < allHitbtcTrades.length; i++) {
      HitbtcTrade hitbtcTrade = allHitbtcTrades[i];

      Date timestamp = new Date(hitbtcTrade.getDate());
      BigDecimal price = hitbtcTrade.getPrice();
      BigDecimal amount = hitbtcTrade.getAmount();
      String tid = hitbtcTrade.getTid();
      long longTradeId = tid == null ? 0 : Long.parseLong(tid);
      if (longTradeId > lastTradeId) {
        lastTradeId = longTradeId;
      }
      Trade trade = new Trade(null, amount, currencyPair, price, timestamp, tid);
      trades.add(trade);
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByTimestamp);
  }

  public static OpenOrders adaptOpenOrders(HitbtcOrder[] openOrdersRaw) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>(openOrdersRaw.length);

    for (int i = 0; i < openOrdersRaw.length; i++) {
      HitbtcOrder o = openOrdersRaw[i];

      OrderType type = adaptOrderType(o.getSide());

      LimitOrder order = new LimitOrder(type, o.getExecQuantity(), adaptSymbol(o.getSymbol()), o.getClientOrderId(), new Date(o.getLastTimestamp()),
          o.getOrderPrice());

      openOrders.add(order);
    }

    return new OpenOrders(openOrders);
  }

  public static OrderType adaptOrderType(String side) {

    return side.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static UserTrades adaptTradeHistory(HitbtcOwnTrade[] tradeHistoryRaw, ExchangeMetaData metaData) {

    List<UserTrade> trades = new ArrayList<UserTrade>(tradeHistoryRaw.length);
    for (int i = 0; i < tradeHistoryRaw.length; i++) {
      HitbtcOwnTrade t = tradeHistoryRaw[i];
      OrderType type = adaptOrderType(t.getSide());

      CurrencyPair pair = adaptSymbol(t.getSymbol());

      // minimumAmount is equal to lot size
      BigDecimal tradableAmount = t.getExecQuantity().multiply(metaData.getMarketMetaDataMap().get(pair).getMinimumAmount());
      Date timestamp = new Date(t.getTimestamp());
      String id = Long.toString(t.getTradeId());

      UserTrade trade = new UserTrade(type, tradableAmount, pair, t.getExecPrice(), timestamp, id, t.getClientOrderId(), t.getFee(),
          pair.counterSymbol);

      trades.add(trade);
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptAccountInfo(HitbtcBalance[] accountInfoRaw) {

    List<Wallet> wallets = new ArrayList<Wallet>(accountInfoRaw.length);

    for (int i = 0; i < accountInfoRaw.length; i++) {
      HitbtcBalance balance = accountInfoRaw[i];

      Wallet wallet = new Wallet(balance.getCurrencyCode(), balance.getCash().add(balance.getReserved()), balance.getCash(), balance.getReserved(),
          balance.getCurrencyCode());
      wallets.add(wallet);

    }
    return new AccountInfo(null, wallets);
  }

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return pair == null ? null : pair.baseSymbol + pair.counterSymbol;
  }

  public static String createOrderId(Order order, long nonce) {

    if (order.getId() == null || "".equals(order.getId())) {
      // encoding side in client order id
      return order.getType().name().substring(0, 1) + DELIM + adaptCurrencyPair(order.getCurrencyPair()) + DELIM + nonce;
    } else {
      return order.getId();
    }
  }

  public static OrderType readOrderType(String orderId) {

    return orderId.charAt(0) == 'A' ? OrderType.ASK : OrderType.BID;
  }

  public static String readSymbol(String orderId) {

    int start = orderId.indexOf(DELIM);
    int end = orderId.indexOf(DELIM, start + 1);
    return orderId.substring(start + 1, end);
  }

  public static String getSide(OrderType type) {

    return type == OrderType.BID ? "buy" : "sell";
  }

  public static ExchangeMetaData adaptToExchangeMetaData(HitbtcSymbols symbols, HitbtcMetaData hitbtcMetaData) {

    Map<CurrencyPair, MarketMetaData> marketMetaDataMap = new HashMap<CurrencyPair, MarketMetaData>();
    for (HitbtcSymbol symbol : symbols.getHitbtcSymbols()) {
      CurrencyPair pair = adaptSymbol(symbol);
      MarketMetaData meta = new MarketMetaData(symbol.getTakeLiquidityRate(), symbol.getLot(), symbol.getStep().scale());

      marketMetaDataMap.put(pair, meta);
    }

    return new ExchangeMetaData(marketMetaDataMap, hitbtcMetaData.currency, null, null, null);
  }

}
