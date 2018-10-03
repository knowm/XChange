package org.knowm.xchange.dto.trade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.dto.Order;

/**
 * DTO representing open orders
 *
 * <p>Open orders are orders that you have placed with the exchange that have not yet been matched
 * to a counter-party.
 */
public final class OpenOrders implements Serializable {

  private final List<LimitOrder> openOrders;
  private final List<? extends Order> hiddenOrders;

  /**
   * Constructor
   *
   * @param openOrders The list of open orders
   */
  public OpenOrders(List<LimitOrder> openOrders) {
    this.openOrders = openOrders;
    this.hiddenOrders = Collections.emptyList();
  }

  /**
   * Constructor
   *
   * @param openOrders The list of open orders
   * @param hiddenOrders The list of orders which are active but hidden from the order book.
   */
  public OpenOrders(List<LimitOrder> openOrders, List<Order> hiddenOrders) {
    this.openOrders = openOrders;
    this.hiddenOrders = hiddenOrders;
  }

  /** @return LimitOrders which are shown on the order book. */
  public List<LimitOrder> getOpenOrders() {
    return openOrders;
  }

  /** @return All Orders which are shown on the order book. */
  public List<Order> getAllOpenOrders() {
    List<Order> allOpenOrders = new ArrayList<>(openOrders);
    allOpenOrders.addAll(hiddenOrders);
    return allOpenOrders;
  }

  /** @return Orders which are not shown on the order book, such as untriggered stop orders. */
  public List<? extends Order> getHiddenOrders() {
    return hiddenOrders;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    addToToString(sb, getOpenOrders(), "Open orders");
    addToToString(sb, getHiddenOrders(), "Hidden orders");
    return sb.toString();
  }

  private void addToToString(StringBuilder sb, List<? extends Order> orders, String description) {
    sb.append(description);
    sb.append(": ");
    if (orders.isEmpty()) {
      sb.append("None\n");
    } else {
      sb.append("\n");
      for (Order order : orders) {
        sb.append("  [order=");
        sb.append(order.toString());
        sb.append("]\n");
      }
    }
  }
}
