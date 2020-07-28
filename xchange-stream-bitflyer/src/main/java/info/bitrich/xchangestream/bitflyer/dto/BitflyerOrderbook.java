package info.bitrich.xchangestream.bitflyer.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Created by Lukas Zaoralek on 14.11.17. */
public class BitflyerOrderbook {
  private final SortedMap<BigDecimal, BitflyerLimitOrder> asks;
  private final SortedMap<BigDecimal, BitflyerLimitOrder> bids;
  private final CurrencyPair pair;
  private final BigDecimal zero = new BigDecimal(0);

  public BitflyerOrderbook(CurrencyPair pair) {
    this.asks = new TreeMap<>();
    this.bids = new TreeMap<>(java.util.Collections.reverseOrder());
    this.pair = pair;
  }

  public BitflyerOrderbook(
      CurrencyPair pair, BitflyerLimitOrder[] asks, BitflyerLimitOrder[] bids) {
    this(pair);
    createFromLevels(asks, Order.OrderType.ASK);
    createFromLevels(bids, Order.OrderType.BID);
  }

  public void createFromLevels(BitflyerLimitOrder[] levels, Order.OrderType side) {
    SortedMap<BigDecimal, BitflyerLimitOrder> orderbookLevels =
        side == Order.OrderType.ASK ? asks : bids;
    for (BitflyerLimitOrder level : levels) {
      orderbookLevels.put(level.getPrice(), level);
    }
  }

  public BitflyerLimitOrder[] getLevels(Order.OrderType side) {
    SortedMap<BigDecimal, BitflyerLimitOrder> orderBookSide =
        side == Order.OrderType.ASK ? asks : bids;
    return orderBookSide.values().toArray(new BitflyerLimitOrder[orderBookSide.size()]);
  }

  public BitflyerLimitOrder[] getAsks() {
    return getLevels(Order.OrderType.ASK);
  }

  public BitflyerLimitOrder[] getBids() {
    return getLevels(Order.OrderType.BID);
  }

  public static List<LimitOrder> toLimitOrders(
      BitflyerLimitOrder[] levels, Order.OrderType side, CurrencyPair pair) {
    if (levels == null || levels.length == 0) return null;

    List<LimitOrder> limitOrders = new ArrayList<>(levels.length);
    for (BitflyerLimitOrder level : levels) {
      LimitOrder limitOrder = level.toLimitOrder(pair, side);
      limitOrders.add(limitOrder);
    }

    return limitOrders;
  }

  public OrderBook toOrderBook() {
    List<LimitOrder> orderbookAsks = toLimitOrders(getAsks(), Order.OrderType.ASK, pair);
    List<LimitOrder> orderbookBids = toLimitOrders(getBids(), Order.OrderType.BID, pair);
    return new OrderBook(null, orderbookAsks, orderbookBids);
  }

  public void updateLevels(BitflyerLimitOrder[] levels, Order.OrderType side) {
    SortedMap<BigDecimal, BitflyerLimitOrder> orderBookSide =
        side == Order.OrderType.ASK ? asks : bids;
    for (BitflyerLimitOrder level : levels) {
      BigDecimal price = level.getPrice();
      boolean shouldDelete = level.getSize().compareTo(zero) == 0;
      orderBookSide.remove(price);
      if (!shouldDelete) {
        orderBookSide.put(price, level);
      }
    }
  }
}
