package org.knowm.xchange.upbit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBook;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBookData;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBooks;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTicker;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTickers;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrade;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrades;
import org.knowm.xchange.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UpbitAdapters {

  public static final Logger log = LoggerFactory.getLogger(UpbitAdapters.class);

  private UpbitAdapters() {}

  public static OrderBook adaptOrderBook(UpbitOrderBooks upbitOrderBooks) {
    UpbitOrderBook upbitOrderBook = upbitOrderBooks.getUpbitOrderBooks()[0];
    String market = upbitOrderBook.getMarket();
    CurrencyPair currencyPair =
        new CurrencyPair(
            Currency.getInstance(market.split("-")[0]), Currency.getInstance(market.split("-")[1]));
    Map<OrderType, List<LimitOrder>> orderbookMap =
        adaptMarketOrderToLimitOrder(upbitOrderBook.getOrderbookUnits(), currencyPair);
    return new OrderBook(
        DateUtils.fromMillisUtc(upbitOrderBook.getTimestamp().longValue()),
        orderbookMap.get(OrderType.ASK),
        orderbookMap.get(OrderType.BID));
  }

  private static Map<OrderType, List<LimitOrder>> adaptMarketOrderToLimitOrder(
      UpbitOrderBookData[] upbitOrders, CurrencyPair currencyPair) {

    List<LimitOrder> asks = new ArrayList<>(upbitOrders.length);
    List<LimitOrder> bids = new ArrayList<>(upbitOrders.length);
    for (int i = 0; i < upbitOrders.length; i++) {
      UpbitOrderBookData upbitOrder = upbitOrders[i];
      OrderType orderType = OrderType.ASK;
      BigDecimal price = upbitOrder.getAskPrice();
      BigDecimal amount = upbitOrder.getAskSize();
      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, null, price);
      asks.add(limitOrder);
      orderType = OrderType.BID;
      price = upbitOrder.getBidPrice();
      amount = upbitOrder.getBidSize();
      limitOrder = new LimitOrder(orderType, amount, currencyPair, null, null, price);
      bids.add(limitOrder);
    }
    Map<OrderType, List<LimitOrder>> map = new HashMap<>();
    map.put(OrderType.ASK, asks);
    map.put(OrderType.BID, bids);
    return map;
  }

  public static Ticker adaptTicker(UpbitTickers tickers) {
    UpbitTicker ticker = tickers.getTickers()[0];
    String market = ticker.getMarket();

    CurrencyPair currencyPair =
        new CurrencyPair(
            Currency.getInstance(market.split("-")[0]), Currency.getInstance(market.split("-")[1]));
    final Date date =
        DateUtils.fromMillisUtc(Long.valueOf(ticker.getTimestamp().longValue()) * 1000);
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .high(ticker.getHigh_price())
        .low(ticker.getLow_price())
        .last(ticker.getTrade_price())
        .volume(ticker.getTrade_volume())
        .open(ticker.getOpening_price())
        .timestamp(date)
        .build();
  }

  public static Trades adaptTrades(UpbitTrades trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>(trades.getUpbitTrades().length);
    for (UpbitTrade trade : trades.getUpbitTrades()) {
      tradeList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradeList, 0, Trades.TradeSortType.SortByTimestamp);
  }

  private static Trade adaptTrade(UpbitTrade trade, CurrencyPair currencyPair) {
    OrderType orderType = OrderType.BID;
    if (OrderType.ASK.toString().equals(trade.getAskBid())) orderType = OrderType.ASK;

    return new Trade(
        orderType,
        trade.getTradeVolume(),
        currencyPair,
        trade.getTradePrice(),
        DateUtils.fromMillisUtc(trade.getTimestamp().longValue()),
        "");
  }
}
