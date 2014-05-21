package com.xeiam.xchange.bitbay;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.bitbay.dto.marketdata.*;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kpysniak
 */
public class BitbayAdapters {

  /**
   * Singleton
   */
  private BitbayAdapters() { }

  /**
   * Adapts a BitbayTicker to a Ticker Object
   *
   * @param bitbayTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitbayTicker bitbayTicker, CurrencyPair currencyPair) {

    BigDecimal ask = bitbayTicker.getAsk();
    BigDecimal bid = bitbayTicker.getBid();
    BigDecimal high = bitbayTicker.getMax();
    BigDecimal low = bitbayTicker.getMin();
    BigDecimal volume = bitbayTicker.getVolume();
    BigDecimal last = bitbayTicker.getLast();
    Date timestamp = new Date();

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last)
        .withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume)
        .withTimestamp(timestamp).build();
  }

  /**
   *
   * @param orders
   * @param orderType
   * @param currencyPair
   * @return
   */
  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for(BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  /**
   *
   * @param bitbayOrderBook
   * @param currencyPair
   * @return
   */
  public static OrderBook adaptOrderBook(BitbayOrderBook bitbayOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook = new OrderBook(new Date(),
        transformArrayToLimitOrders(bitbayOrderBook.getAsks(), OrderType.ASK, currencyPair),
        transformArrayToLimitOrders(bitbayOrderBook.getBids(), OrderType.BID, currencyPair));

    return orderBook;
  }

  /**
   *
   * @param bitbayTrades
   * @param currencyPair
   * @return
   */
  public static Trades adaptTrades(BitbayTrade[] bitbayTrades, CurrencyPair currencyPair) {
    List<Trade> tradeList = new ArrayList<Trade>();

    for (BitbayTrade bitbayTrade : bitbayTrades) {

      Trade trade = new Trade(null, bitbayTrade.getAmount(), currencyPair,
          bitbayTrade.getPrice(), new Date(bitbayTrade.getDate()), bitbayTrade.getTid());

      tradeList.add(trade);
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }

}
