package org.knowm.xchange.bitcoinde;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrder;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author matthewdowney
 */
public final class BitcoindeAdapters {

  /**
   * Private constructor.
   */
  private BitcoindeAdapters() {

  }

    public static final Comparator<LimitOrder> ASK_COMPARATOR = new Comparator<LimitOrder>() {
    @Override
    public int compare(LimitOrder o1, LimitOrder o2) {
      return o1.getLimitPrice().compareTo(o2.getLimitPrice());
    }
  };
  public static final Comparator<LimitOrder> BID_COMPARATOR = new Comparator<LimitOrder>() {
    @Override
    public int compare(LimitOrder o1, LimitOrder o2) {
      return o2.getLimitPrice().compareTo(o1.getLimitPrice());
    }
  };

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook object to an OrderBook object.
   *
   * @param bitcoindeOrderbookWrapper the exchange specific OrderBook object
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(BitcoindeOrderbookWrapper bitcoindeOrderbookWrapper, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, bitcoindeOrderbookWrapper.getBitcoindeOrders().getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, bitcoindeOrderbookWrapper.getBitcoindeOrders().getBids());

    Collections.sort(bids, BID_COMPARATOR);
    Collections.sort(asks, ASK_COMPARATOR);
    return new OrderBook(null, asks, bids);
  }

  /**
   * Create a list of orders from a list of asks or bids.
   */
  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, BitcoindeOrder[] orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BitcoindeOrder order : orders) {
      limitOrders.add(createOrder(currencyPair, order, orderType, null, null));
    }
    return limitOrders;
  }

  /**
   * Create an individual order.
   */
  public static LimitOrder createOrder(CurrencyPair currencyPair, BitcoindeOrder bitcoindeOrder, Order.OrderType orderType, String orderId,
      Date timeStamp) {

    return new LimitOrder(orderType, bitcoindeOrder.getAmount(), currencyPair, orderId, timeStamp, bitcoindeOrder.getPrice());
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade[] object to a Trades object.
   *
   * @param bitcoindeTradesWrapper Exchange specific trades
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitcoindeTradesWrapper bitcoindeTradesWrapper, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (BitcoindeTrade bitcoindeTrade : bitcoindeTradesWrapper.getTrades()) {
      final long tid = bitcoindeTrade.getTid();
      if (tid > lastTradeId) {
        lastTradeId = tid;
      }
      trades.add(new Trade(null, bitcoindeTrade.getAmount(), currencyPair, bitcoindeTrade.getPrice(),
          DateUtils.fromMillisUtc(bitcoindeTrade.getDate() * 1000L), String.valueOf(tid)));
    }
    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

}
