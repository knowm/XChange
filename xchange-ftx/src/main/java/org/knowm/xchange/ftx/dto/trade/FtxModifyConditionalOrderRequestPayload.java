package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class FtxModifyConditionalOrderRequestPayload {

  private final BigDecimal orderPrice;

  private final BigDecimal triggerPrice;

  private final BigDecimal trailValue;

  private final BigDecimal size;

  public FtxModifyConditionalOrderRequestPayload(
      @JsonProperty("orderPrice") BigDecimal orderPrice,
      @JsonProperty("triggerPrice") BigDecimal triggerPrice,
      @JsonProperty("trailValue") BigDecimal trailValue,
      @JsonProperty("size") BigDecimal size) {
    this.orderPrice = orderPrice;
    this.triggerPrice = triggerPrice;
    this.trailValue = trailValue;
    this.size = size;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public BigDecimal getTriggerPrice() {
    return triggerPrice;
  }

  public BigDecimal getTrailValue() {
    return trailValue;
  }

  public BigDecimal getSize() {
    return size;
  }

  @Override
  public String toString() {
    return "FtxModifyOrderRequestPayload{"
        + "orderPrice="
        + orderPrice
        + "triggerPrice="
        + triggerPrice
        + ", trailValue="
        + trailValue
        + ", size="
        + size
        + '}';
  }
}
