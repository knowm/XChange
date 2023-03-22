package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class BatchOrder {

  private final List<OrderCommand> batchOrder;

  public BatchOrder(List<OrderCommand> batchOrder) {
    super();
    this.batchOrder = batchOrder;
  }

  public List<OrderCommand> getBatchOrder() {
    return batchOrder;
  }

  @Override
  public String toString() {
    try {
      // convert the object to json
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Could not convert to json.", e);
    }
  }
}
