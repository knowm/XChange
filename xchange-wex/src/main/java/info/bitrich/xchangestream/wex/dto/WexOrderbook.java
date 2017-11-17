package info.bitrich.xchangestream.wex.dto;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Lukas Zaoralek on 16.11.17.
 */
public class WexOrderbook {
  private final BigDecimal zero = new BigDecimal(0);

  private final SortedMap<BigDecimal, LimitOrder> asks;
  private final SortedMap<BigDecimal, LimitOrder> bids;

  public WexOrderbook() {
    asks = new TreeMap<>();
    bids = new TreeMap<>(java.util.Collections.reverseOrder());
  }

  public WexOrderbook(OrderBook orderBook) {
    this();
    createFromOrderbook(orderBook);
  }

  public void createFromOrderbook(OrderBook orderBook) {
    List<LimitOrder> levels = orderBook.getAsks();
    createFromLevels(levels.toArray(new LimitOrder[levels.size()]));
    levels = orderBook.getBids();
    createFromLevels(levels.toArray(new LimitOrder[levels.size()]));
  }

  public void createFromLevels(LimitOrder[] levels) {
    for (LimitOrder level : levels) {
      SortedMap<BigDecimal, LimitOrder> orderBookSide = level.getType() == Order.OrderType.ASK ? asks : bids;
      orderBookSide.put(level.getLimitPrice(), level);
    }
  }

  public void updateLevel(LimitOrder level) {
    SortedMap<BigDecimal, LimitOrder> orderBookSide = level.getType() == Order.OrderType.ASK ? asks : bids;
    boolean shouldDelete = level.getOriginalAmount().compareTo(zero) == 0;
    BigDecimal price = level.getLimitPrice();
    orderBookSide.remove(price);
    if (!shouldDelete) {
      orderBookSide.put(price, level);
    }
  }

  public void updateLevels(LimitOrder[] levels) {
    for (LimitOrder level : levels) {
      updateLevel(level);
    }
  }

  public List<LimitOrder> getSide(Order.OrderType side) {
    Collection<LimitOrder> sideLevels = side == Order.OrderType.ASK ? asks.values() : bids.values();
    return new ArrayList<>(sideLevels);
  }

  public OrderBook toOrderbook() {
    return new OrderBook(null, getSide(Order.OrderType.ASK), getSide(Order.OrderType.BID));
  }
}
