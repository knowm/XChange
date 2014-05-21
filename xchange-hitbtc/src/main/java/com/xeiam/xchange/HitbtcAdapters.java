package com.xeiam.xchange;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.*;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kpysniak
 */
public class HitbtcAdapters {

  /**
   * Singleton
   */
  private HitbtcAdapters() { }

  public static ExchangeInfo adaptExchangeInfo(HitbtcSymbols hitbtcSymbols) {

    List<CurrencyPair> currencyPairList = new ArrayList<CurrencyPair>();

    for (HitbtcSymbol hitbtcSymbol : hitbtcSymbols.getHitbtcSymbols()) {
      String base = hitbtcSymbol.getSymbol().substring(0, 3);
      String counterSymbol = hitbtcSymbol.getSymbol().substring(3);
      CurrencyPair currencyPair = new CurrencyPair(base, counterSymbol);

      currencyPairList.add(currencyPair);
    }

    return new ExchangeInfo(currencyPairList);
  }

  /**
   * Adapts a BTCCentralTicker to a Ticker Object
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

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last)
        .withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume)
        .withTimestamp(timestamp).build();
  }


  public static OrderBook adaptOrderBook(HitbtcOrderBook hitbtcOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = adaptMarketOrderToLimitOrder(hitbtcOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptMarketOrderToLimitOrder(hitbtcOrderBook.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(new Date(), asks, bids);
  }

  /**
   *
   * @param hitbtcOrders
   * @param orderType
   * @param currencyPair
   * @return
   */
  private static List<LimitOrder> adaptMarketOrderToLimitOrder(BigDecimal[][] hitbtcOrders, OrderType orderType,
                                                               CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>(hitbtcOrders.length);

    for (BigDecimal[] hitbtcOrder : hitbtcOrders) {

      BigDecimal price = hitbtcOrder[0];
      BigDecimal amount = hitbtcOrder[1];

      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, new Date(), price);
      orders.add(limitOrder);
    }

    return orders;
  }

  public static Trades adaptTrades(HitbtcTrades hitbtcTrades, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<Trade>();

    for (HitbtcTrade hitbtcTrade : hitbtcTrades.getHitbtcTrades()) {


      Date timestamp = new Date(hitbtcTrade.getDate());
      BigDecimal price = hitbtcTrade.getPrice();
      BigDecimal amount = hitbtcTrade.getAmount();
      String tid = hitbtcTrade.getTid();

      Trade trade = new Trade(null, amount, currencyPair, price, timestamp, tid, tid);
      trades.add(trade);
    }

    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }

}
