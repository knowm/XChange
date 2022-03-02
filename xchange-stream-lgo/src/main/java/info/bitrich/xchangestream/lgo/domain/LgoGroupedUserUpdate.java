package info.bitrich.xchangestream.lgo.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.dto.Order;

public class LgoGroupedUserUpdate {

  private final Map<String, Order> allOpenOrders;
  private final List<Order> updatedOrders;
  private final List<LgoBatchOrderEvent> events;
  private final long batchId;
  private final String type;

  public LgoGroupedUserUpdate() {
    updatedOrders = Collections.emptyList();
    events = Collections.emptyList();
    batchId = 0;
    type = "";
    allOpenOrders = new ConcurrentHashMap<>();
  }

  public LgoGroupedUserUpdate(
      Map<String, Order> allOpenOrders,
      List<Order> updatedOrders,
      List<LgoBatchOrderEvent> events,
      long batchId,
      String type) {
    this.allOpenOrders = allOpenOrders;
    this.updatedOrders = updatedOrders;
    this.events = events;
    this.batchId = batchId;
    this.type = type;
  }

  public List<Order> getUpdatedOrders() {
    return updatedOrders;
  }

  public Map<String, Order> getAllOpenOrders() {
    return allOpenOrders;
  }

  public List<LgoBatchOrderEvent> getEvents() {
    return events;
  }

  public long getBatchId() {
    return batchId;
  }

  public String getType() {
    return type;
  }
}
