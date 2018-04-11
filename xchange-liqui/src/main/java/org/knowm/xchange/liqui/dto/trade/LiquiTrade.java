package org.knowm.xchange.liqui.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class LiquiTrade {

  private final BigDecimal received;
  private final BigDecimal remains;
  private final long orderId;
  private final long initOrderId;
  private final Map<String, BigDecimal> funds;
  private final Object trades;

  public LiquiTrade(
      @JsonProperty("received") final String received,
      @JsonProperty("remains") final String remains,
      @JsonProperty("order_id") final long orderId,
      @JsonProperty("init_order_id") final long initOrderId,
      @JsonProperty("funds") final Map<String, String> funds,
      @JsonProperty("trades") final Object trades) {

    this.received = new BigDecimal(received);
    this.remains = new BigDecimal(remains);
    this.orderId = orderId;
    this.initOrderId = initOrderId;
    this.funds =
        funds
            .entrySet()
            .stream()
            .collect(Collectors.toMap((Map.Entry::getKey), (e -> new BigDecimal(e.getValue()))));
    this.trades = trades;
  }

  public BigDecimal getReceived() {
    return received;
  }

  public BigDecimal getRemains() {
    return remains;
  }

  public long getOrderId() {
    return orderId;
  }

  public long getInitOrderId() {
    return initOrderId;
  }

  public Map<String, BigDecimal> getFunds() {
    return funds;
  }

  public Object getTrades() {
    return trades;
  }

  @Override
  public String toString() {
    return "LiquiTrade{"
        + "received='"
        + received
        + '\''
        + ", remains='"
        + remains
        + '\''
        + ", orderId="
        + orderId
        + ", initOrderId="
        + initOrderId
        + ", funds="
        + funds
        + ", trades="
        + trades
        + '}';
  }
}
