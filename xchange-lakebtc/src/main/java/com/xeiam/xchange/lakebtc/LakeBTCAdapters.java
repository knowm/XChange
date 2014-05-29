package com.xeiam.xchange.lakebtc;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.*;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTicker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kpysniak
 */
public class LakeBTCAdapters {

  /**
   * Singleton
   */
  private LakeBTCAdapters() { }

  public static Ticker adaptTicker(LakeBTCTicker lakeBTCTicker, CurrencyPair currencyPair) {

    BigDecimal ask = lakeBTCTicker.getAsk();
    BigDecimal bid = lakeBTCTicker.getBid();
    BigDecimal high = lakeBTCTicker.getHigh();
    BigDecimal low = lakeBTCTicker.getLow();
    BigDecimal last = lakeBTCTicker.getLast();
    BigDecimal volume = lakeBTCTicker.getVolume();
    Date timestamp = new Date();

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair)
        .withBid(bid).withAsk(ask).withHigh(high).withLow(low).withLast(last)
        .withTimestamp(timestamp).withVolume(volume).build();
  }

  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for(BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }
  public static OrderBook adaptOrderBook(LakeBTCOrderBook lakeBTCOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook = new OrderBook(new Date(),
        transformArrayToLimitOrders(lakeBTCOrderBook.getAsks(), OrderType.ASK, currencyPair),
        transformArrayToLimitOrders(lakeBTCOrderBook.getBids(), OrderType.BID, currencyPair));

    return orderBook;
  }

}
