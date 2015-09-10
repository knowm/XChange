package com.xeiam.xchange.bitcoinde;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author matthewdowney
 */
public final class BitcoindeAdapters {

  /**
   * Private constructor.
   */
  private BitcoindeAdapters() {

  }

  /**
   * Adapt a com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook object to an OrderBook object.
   * 
   * @param bitcoindeOrderBook the exchange specific OrderBook object
   * @param currencyPair (e.g. BTC/USD)
   * @param date the date of
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(BitcoindeOrderBook bitcoindeOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, bitcoindeOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, bitcoindeOrderBook.getBids());
    return new OrderBook(bitcoindeOrderBook.getTimeStamp(), asks, bids);
  }

  /**
   * Create a list of orders from a list of asks or bids.
   */
  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, BigDecimal[][] orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (BigDecimal[] order : orders) {
      limitOrders.add(createOrder(currencyPair, order, orderType, null, null));
    }
    return limitOrders;
  }

  /**
   * Create an individual order.
   */
  public static LimitOrder createOrder(CurrencyPair currencyPair, BigDecimal[] priceAndAmount, Order.OrderType orderType, String orderId,
      Date timeStamp) {

    return new LimitOrder(orderType, priceAndAmount[1], currencyPair, orderId, timeStamp, priceAndAmount[0]);
  }

  /**
   * Adapt a com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeRate object to a Ticker object.
   * 
   * @param bitcoindeRate The exchange specific rate
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Ticker
   */
  public static Ticker adaptTicker(BitcoindeRate bitcoindeRate, CurrencyPair currencyPair) {

    BigDecimal last = new BigDecimal(bitcoindeRate.getRate_weighted());
    return new Ticker.Builder().currencyPair(currencyPair).last(last).build();
  }

  /**
   * Adapt a com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeTrade[] object to a Trades object.
   * 
   * @param bitcoindeTrades Exchange specific trades
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitcoindeTrade[] bitcoindeTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (BitcoindeTrade bitcoindeTrade : bitcoindeTrades) {
      final long tid = bitcoindeTrade.getTid();
      if (tid > lastTradeId) {
        lastTradeId = tid;
      }
      trades.add(new Trade(null, new BigDecimal(bitcoindeTrade.getAmount()), currencyPair, bitcoindeTrade.getPrice(),
          DateUtils.fromMillisUtc(bitcoindeTrade.getDate() * 1000L), String.valueOf(tid)));
    }
    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

}
