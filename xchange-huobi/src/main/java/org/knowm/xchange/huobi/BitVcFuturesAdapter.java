package org.knowm.xchange.huobi;

import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesDepth;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTicker;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTrade;

public class BitVcFuturesAdapter {

  public static Ticker adaptTicker(BitVcFuturesTicker ticker, CurrencyPair currencyPair) {

    return new Ticker.Builder().currencyPair(currencyPair).last(ticker.getLast()).bid(ticker.getBuy()).ask(ticker.getSell()).high(ticker.getHigh())
        .low(ticker.getLow()).volume(ticker.getVol()).build();
  }

  public static OrderBook adaptOrderBook(BitVcFuturesDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptOrderBook(depth.getAsks(), ASK, currencyPair);
    List<LimitOrder> bids = adaptOrderBook(depth.getBids(), BID, currencyPair);

    // ask side is flipped
    Collections.sort(asks);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptOrderBook(BigDecimal[][] orders, OrderType type, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);

    for (int i = 0; i < orders.length; i++) {
      BigDecimal[] order = orders[i];

      LimitOrder limitOrder = new LimitOrder(type, order[1], currencyPair, null, null, order[0]);
      limitOrders.add(limitOrder);
    }
    return limitOrders;
  }

  public static Trades adaptTrades(BitVcFuturesTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>(trades.length);
    for (BitVcFuturesTrade trade : trades) {
      tradeList.add(adaptTrade(trade, currencyPair));
    }

    return new Trades(tradeList, TradeSortType.SortByID);
  }

  private static Trade adaptTrade(BitVcFuturesTrade trade, CurrencyPair currencyPair) {

    OrderType type = trade.getType().equals("buy") ? BID : ASK;
    return new Trade(type, trade.getAmount(), currencyPair, trade.getPrice(), trade.getDate(), trade.getId());
  }

}
