package org.knowm.xchange.dragonex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OrderHistory {

  private final List<UserOrder> list;
  public final long count;

  public OrderHistory(
      @JsonProperty("list") List<UserOrder> list, @JsonProperty("count") long count) {
    this.list = list;
    this.count = count;
  }

  public List<UserOrder> getList() {
    return list;
  }

  @Override
  public String toString() {
    return "OrderHistory [" + (list != null ? "list=" + list + ", " : "") + "count=" + count + "]";
  }
}
