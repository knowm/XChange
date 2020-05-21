package org.knowm.xchange.bittrex.service.batch.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BatchOrder {
  @JsonProperty("Resource")
  private String resource = "Order";

  @JsonProperty("Operation")
  private Operation operation;

  @JsonProperty("Payload")
  private OrderPayload payload;

  public BatchOrder(Operation operation, OrderPayload payload) {
    this.operation = operation;
    this.payload = payload;
  }
}
