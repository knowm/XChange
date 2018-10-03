package org.knowm.xchange.liqui.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class LiquiCancelOrder {

  private final long orderId;
  private final Map<String, BigDecimal> funds;

  public LiquiCancelOrder(
      @JsonProperty("order_id") final long orderId,
      @JsonProperty("funds") final Map<String, String> funds) {
    this.orderId = orderId;
    this.funds =
        funds
            .entrySet()
            .stream()
            .collect(Collectors.toMap((Map.Entry::getKey), (e -> new BigDecimal(e.getValue()))));
  }

  public long getOrderId() {
    return orderId;
  }

  public Map<String, BigDecimal> getFunds() {
    return funds;
  }

  @Override
  public String toString() {
    return "LiquiCancelOrder{" + "orderId=" + orderId + ", funds=" + funds + '}';
  }
}
