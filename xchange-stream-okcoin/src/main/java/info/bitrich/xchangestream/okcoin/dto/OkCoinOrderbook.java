package info.bitrich.xchangestream.okcoin.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;

/** Created by Lukas Zaoralek on 16.11.17. */
public class OkCoinOrderbook {
  private final BigDecimal zero = new BigDecimal(0);

  private final SortedMap<BigDecimal, BigDecimal[]> asks;
  private final SortedMap<BigDecimal, BigDecimal[]> bids;

  public OkCoinOrderbook() {
    asks =
        new TreeMap<>(
            java.util.Collections
                .reverseOrder()); // Because okcoin adapter uses reverse sort for asks!!!
    bids = new TreeMap<>();
  }

  public OkCoinOrderbook(OkCoinDepth depth) {
    this();
    createFromDepth(depth);
  }

  public void createFromDepth(OkCoinDepth depth) {
    BigDecimal[][] depthAsks = depth.getAsks();
    BigDecimal[][] depthBids = depth.getBids();

    createFromDepthLevels(depthAsks, Order.OrderType.ASK);
    createFromDepthLevels(depthBids, Order.OrderType.BID);
  }

  public void createFromDepthLevels(BigDecimal[][] depthLevels, Order.OrderType side) {
    SortedMap<BigDecimal, BigDecimal[]> orderbookLevels = side == Order.OrderType.ASK ? asks : bids;
    for (BigDecimal[] level : depthLevels) {
      orderbookLevels.put(level[0], level);
    }
  }

  public void updateLevels(BigDecimal[][] depthLevels, Order.OrderType side) {
    for (BigDecimal[] level : depthLevels) {
      updateLevel(level, side);
    }
  }

  public void updateLevel(BigDecimal[] level, Order.OrderType side) {
    SortedMap<BigDecimal, BigDecimal[]> orderBookSide = side == Order.OrderType.ASK ? asks : bids;
    boolean shouldDelete = level[1].compareTo(zero) == 0;
    BigDecimal price = level[0];
    orderBookSide.remove(price);
    if (!shouldDelete) {
      orderBookSide.put(price, level);
    }
  }

  public BigDecimal[][] getSide(Order.OrderType side) {
    SortedMap<BigDecimal, BigDecimal[]> orderbookLevels = side == Order.OrderType.ASK ? asks : bids;
    Collection<BigDecimal[]> levels = orderbookLevels.values();
    return levels.toArray(new BigDecimal[orderbookLevels.size()][]);
  }

  public BigDecimal[][] getAsks() {
    return getSide(Order.OrderType.ASK);
  }

  public BigDecimal[][] getBids() {
    return getSide(Order.OrderType.BID);
  }

  public OkCoinDepth toOkCoinDepth(long epoch) {
    Date timestamp = new java.util.Date(epoch);
    return new OkCoinDepth(getAsks(), getBids(), timestamp);
  }
}
