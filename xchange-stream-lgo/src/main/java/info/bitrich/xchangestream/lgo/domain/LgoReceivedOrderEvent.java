package info.bitrich.xchangestream.lgo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/** Order was received by LGO platform. No batchId for this event. */
public class LgoReceivedOrderEvent extends LgoAckOrderEvent {

  private final String reference;

  public LgoReceivedOrderEvent(
      @JsonProperty("order_id") String orderId,
      @JsonProperty("reference") String reference,
      @JsonProperty("type") String type,
      @JsonProperty("time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date time) {
    super(type, orderId, time);
    this.reference = reference;
  }

  public String getReference() {
    return reference;
  }
}
