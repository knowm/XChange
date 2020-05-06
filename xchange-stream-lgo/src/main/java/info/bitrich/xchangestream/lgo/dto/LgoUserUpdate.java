package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.lgo.domain.LgoBatchOrderEvent;
import java.util.List;

public class LgoUserUpdate extends LgoUserMessage {

  private final List<LgoBatchOrderEvent> orderEvents;

  public LgoUserUpdate(
      @JsonProperty("batch_id") long batchId,
      @JsonProperty("type") String type,
      @JsonProperty("channel") String channel,
      @JsonProperty("product_id") String productId,
      @JsonProperty("payload") List<LgoBatchOrderEvent> orderEvents) {
    super(batchId, type, channel, productId);
    this.orderEvents = orderEvents;
  }

  public List<LgoBatchOrderEvent> getOrderEvents() {
    return orderEvents;
  }
}
