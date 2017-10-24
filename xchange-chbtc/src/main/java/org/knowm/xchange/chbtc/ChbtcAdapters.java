package org.knowm.xchange.chbtc;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.chbtc.dto.marketdata.ChbtcOrderBook;
import org.knowm.xchange.chbtc.dto.marketdata.ChbtcTicker;
import org.knowm.xchange.chbtc.dto.marketdata.ChbtcTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;

public final class ChbtcAdapters {

  private ChbtcAdapters() {
  }

  public static Ticker adaptTicker(ChbtcTicker t, CurrencyPair currencyPair) {
    return new Ticker.Builder().currencyPair(currencyPair).last(t.getLast()).bid(t.getBuy()).ask(t.getSell()).high(t.getHigh()).low(t.getLow())
        .volume(t.getVol()).timestamp(new Date()).build();
  }

  public static OrderBook adaptOrderBook(ChbtcOrderBook chbtcOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, chbtcOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, chbtcOrderBook.getBids());
    return new OrderBook(new Date(), asks, bids);
  }

  private static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  private static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {
    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, null, null, priceAndAmount.get(0));
  }

  public static Trades adaptTrades(ChbtcTrade[] transactions, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (ChbtcTrade tx : transactions) {
      lastTradeId = adaptTrade(currencyPair, trades, lastTradeId, tx);
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }

  private static long adaptTrade(CurrencyPair currencyPair, List<Trade> trades, long lastTradeId, ChbtcTrade tx) {
    Order.OrderType type;
    type = adaptOrderType(tx.getType());
    final long tradeId = tx.getTid();
    if (tradeId > lastTradeId) {
      lastTradeId = tradeId;
    }
    trades.add(new Trade(type, tx.getAmount(), currencyPair, tx.getPrice(), tx.getDate(), String.valueOf(tradeId)));
    return lastTradeId;
  }

  private static Order.OrderType adaptOrderType(ChbtcTrade.Type txType) {
    switch (txType) {
      case buy:
        return Order.OrderType.ASK;
      case sell:
        return Order.OrderType.BID;
    }
    throw new IllegalArgumentException("Illegal type: " + txType);
  }

  private static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {
    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

}
