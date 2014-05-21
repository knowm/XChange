package com.xeiam.xchange.bitmarket;

import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.currency.CurrencyPair;
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
public class BitMarketAdapters {

  /**
   * Singleton
   */
  private BitMarketAdapters() { }

  /**
   * Adapts BitMarket ticker to Ticker.
   * @param bitMarketTicker
   * @param currencyPair
   * @return
   */
  public static Ticker adaptTicker(BitMarketTicker bitMarketTicker, CurrencyPair currencyPair) {

    BigDecimal bid = bitMarketTicker.getBid();
    BigDecimal ask = bitMarketTicker.getAsk();
    BigDecimal high = bitMarketTicker.getHigh();
    BigDecimal low = bitMarketTicker.getLow();
    BigDecimal volume = bitMarketTicker.getVolume();

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair)
        .withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();
  }

  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for(BigDecimal[] order : orders) {
      System.out.println("Amount: " + order[1] + ", exchange rate: " + order[0]);
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(BitMarketOrderBook bitMarketOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook = new OrderBook(new Date(),
        transformArrayToLimitOrders(bitMarketOrderBook.getAsks(), OrderType.ASK, currencyPair),
        transformArrayToLimitOrders(bitMarketOrderBook.getBids(), OrderType.BID, currencyPair));

    return orderBook;
  }

  public static Trades adaptTrades(BitMarketTrade[] bitMarketTrades, CurrencyPair currencyPair) {
    List<Trade> tradeList = new ArrayList<Trade>();

    for (BitMarketTrade bitMarketTrade : bitMarketTrades) {

      Trade trade = new Trade(OrderType.BID, bitMarketTrade.getAmount(), currencyPair,
          bitMarketTrade.getPrice(), new Date(bitMarketTrade.getDate()), bitMarketTrade.getTid());

      tradeList.add(trade);
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }
}
