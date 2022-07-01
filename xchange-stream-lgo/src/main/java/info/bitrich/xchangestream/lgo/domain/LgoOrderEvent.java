package info.bitrich.xchangestream.lgo.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = LgoPendingOrderEvent.class, name = "pending"),
  @JsonSubTypes.Type(value = LgoOpenOrderEvent.class, name = "open"),
  @JsonSubTypes.Type(value = LgoMatchOrderEvent.class, name = "match"),
  @JsonSubTypes.Type(value = LgoInvalidOrderEvent.class, name = "invalid"),
  @JsonSubTypes.Type(value = LgoDoneOrderEvent.class, name = "done"),
  @JsonSubTypes.Type(value = LgoReceivedOrderEvent.class, name = "received"),
  @JsonSubTypes.Type(value = LgoFailedOrderEvent.class, name = "failed")
})
public abstract class LgoOrderEvent {

  /** Type of the event (all events). */
  private final String type;

  /** Identifier of the order concerned by the event (all events) */
  private final String orderId;

  /** Date when the event happend, UTC (all events) */
  private final Date time;

  protected LgoOrderEvent(String type, String orderId, Date time) {
    this.type = type;
    this.orderId = orderId;
    this.time = time;
  }

  public String getType() {
    return type;
  }

  public String getOrderId() {
    return orderId;
  }

  public Date getTime() {
    return time;
  }
}
