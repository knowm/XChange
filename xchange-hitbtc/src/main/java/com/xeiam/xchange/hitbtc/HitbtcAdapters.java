package com.xeiam.xchange.hitbtc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbol;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrade;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;

/**
 * @author kpysniak
 */
public class HitbtcAdapters {

  private static final BigDecimal LOT_MULTIPLIER = new BigDecimal("100");

  /**
   * Singleton
   */
  private HitbtcAdapters() {

  }

  public static List<CurrencyPair> adaptCurrencyPairs(HitbtcSymbols hitbtcSymbols) {

    List<CurrencyPair> currencyPairList = new ArrayList<CurrencyPair>();

    for (HitbtcSymbol hitbtcSymbol : hitbtcSymbols.getHitbtcSymbols()) {
      String symbolString = hitbtcSymbol.getSymbol();
      CurrencyPair currencyPair = null;
      if (symbolString.startsWith("DOGE")) {
        String counterSymbol = symbolString.substring(4);
        currencyPair = new CurrencyPair("DOGE", counterSymbol);
      }
      else {
        String base = symbolString.substring(0, 3);
        String counterSymbol = symbolString.substring(3);
        currencyPair = new CurrencyPair(base, counterSymbol);
      }

      currencyPairList.add(currencyPair);
    }

    return currencyPairList;
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

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();
  }

  public static OrderBook adaptOrderBook(HitbtcOrderBook hitbtcOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptMarketOrderToLimitOrder(hitbtcOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptMarketOrderToLimitOrder(hitbtcOrderBook.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(new Date(), asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(BigDecimal[][] hitbtcOrders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>(hitbtcOrders.length);

    for (int i = 0; i < hitbtcOrders.length; i++) {
      BigDecimal[] hitbtcOrder = hitbtcOrders[i];

      BigDecimal price = hitbtcOrder[0];
      BigDecimal amount = hitbtcOrder[1];

      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, new Date(), price);
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
      if (longTradeId > lastTradeId)
        lastTradeId = longTradeId;
      Trade trade = new Trade(null, amount, currencyPair, price, timestamp, tid, tid);
      trades.add(trade);
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByTimestamp);
  }

  public static OpenOrders adaptOpenOrders(HitbtcOrder[] openOrdersRaw) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>(openOrdersRaw.length);

    for (int i = 0; i < openOrdersRaw.length; i++) {
      HitbtcOrder o = openOrdersRaw[i];

      OrderType type = o.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;

      String base = o.getSymbol().substring(0, 3);
      String counter = o.getSymbol().substring(3, 6);

      LimitOrder order = new LimitOrder(type, o.getExecQuantity(), new CurrencyPair(base, counter), o.getClientOrderId(), new Date(o.getLastTimestamp()), o.getOrderPrice());

      openOrders.add(order);
    }

    return new OpenOrders(openOrders);
  }

  public static Trades adaptTradeHistory(HitbtcOwnTrade[] tradeHistoryRaw) {

    List<Trade> trades = new ArrayList<Trade>(tradeHistoryRaw.length);
    for (int i = 0; i < tradeHistoryRaw.length; i++) {
      HitbtcOwnTrade t = tradeHistoryRaw[i];
      OrderType type = t.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;

      String base = t.getSymbol().substring(0, 3);
      String counter = t.getSymbol().substring(3, 6);

      Trade trade = new Trade(type, t.getExecQuantity().divide(LOT_MULTIPLIER), new CurrencyPair(base, counter), t.getExecPrice(), new Date(t.getTimestamp()), t.getClientOrderId());

      trades.add(trade);
    }

    return new Trades(trades, TradeSortType.SortByID);
  }

  public static AccountInfo adaptAccountInfo(HitbtcBalance[] accountInfoRaw) {

    List<Wallet> wallets = new ArrayList<Wallet>(accountInfoRaw.length);

    for (int i = 0; i < accountInfoRaw.length; i++) {
      HitbtcBalance balance = accountInfoRaw[i];

      Wallet wallet = new Wallet(balance.getCurrencyCode(), balance.getCash().add(balance.getReserved()), balance.getCurrencyCode());
      wallets.add(wallet);

    }
    return new AccountInfo(null, wallets);
  }

}
