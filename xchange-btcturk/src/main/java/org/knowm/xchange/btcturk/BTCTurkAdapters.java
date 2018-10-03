package org.knowm.xchange.btcturk;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;

/** @author semihunaldi Various adapters for converting from BTCTurk DTOs to XChange DTOs */
public final class BTCTurkAdapters {

  private BTCTurkAdapters() {}

  /**
   * Adapts a BTCTurkTicker to a Ticker Object
   *
   * @param btcTurkTicker The exchange specific ticker
   * @return The ticker
   */
  public static Ticker adaptTicker(BTCTurkTicker btcTurkTicker) {
    BTCTurk.Pair pair = btcTurkTicker.getPair();
    BigDecimal high = btcTurkTicker.getHigh();
    BigDecimal last = btcTurkTicker.getLast();
    Date timestamp = new Date(btcTurkTicker.getTimestamp());
    BigDecimal bid = btcTurkTicker.getBid();
    BigDecimal volume = btcTurkTicker.getVolume();
    BigDecimal low = btcTurkTicker.getLow();
    BigDecimal ask = btcTurkTicker.getAsk();
    BigDecimal open = btcTurkTicker.getOpen();
    BigDecimal average = btcTurkTicker.getAverage();

    return new Ticker.Builder()
        .currencyPair(pair != null ? pair.pair : null)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .vwap(average)
        .open(open)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  /**
   * Adapts a BTCTurkTrade[] to a Trades Object
   *
   * @param btcTurkTrades The BTCTurk trades
   * @param currencyPair (e.g. BTC/TRY)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BTCTurkTrade[] btcTurkTrades, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>();
    int lastTradeId = 0;
    for (BTCTurkTrade btcTurkTrade : btcTurkTrades) {
      if (btcTurkTrade.getTid() > lastTradeId) {
        lastTradeId = btcTurkTrade.getTid();
      }
      trades.add(adaptTrade(btcTurkTrade, currencyPair, 1));
    }
    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }

  /**
   * Adapts a BTCTurkTrade to a Trade Object
   *
   * @param btcTurkTrade The BTCTurkTrade trade
   * @param currencyPair (e.g. BTC/TRY)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange Trade
   */
  public static Trade adaptTrade(
      BTCTurkTrade btcTurkTrade, CurrencyPair currencyPair, int timeScale) {

    final String tradeId = String.valueOf(btcTurkTrade.getTid());
    Date date =
        DateUtils.fromMillisUtc(
            btcTurkTrade.getDate()
                * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new Trade(
        null, btcTurkTrade.getAmount(), currencyPair, btcTurkTrade.getPrice(), date, tradeId);
  }

  /**
   * Adapts org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/TRY)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(
      BTCTurkOrderBook btcTurkOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks =
        createOrders(currencyPair, Order.OrderType.ASK, btcTurkOrderBook.getAsks());
    List<LimitOrder> bids =
        createOrders(currencyPair, Order.OrderType.BID, btcTurkOrderBook.getBids());
    return new OrderBook(btcTurkOrderBook.getTimestamp(), asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(
          ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(
        orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {
    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }
}
