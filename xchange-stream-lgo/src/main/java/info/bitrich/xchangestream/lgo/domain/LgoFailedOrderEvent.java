package info.bitrich.xchangestream.lgo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/** Order could not be added to a batch. No batchId for this event. */
public class LgoFailedOrderEvent extends LgoAckOrderEvent {

  /** Reference set by the trader */
  private final String reference;

  /**
   * Reason of failure (InvalidQuantity, InvalidPrice, InvalidAmount, InvalidPriceIncrement,
   * InvalidProduct, InsufficientFunds)
   */
  private final String reason;

  public LgoFailedOrderEvent(
      @JsonProperty("order_id") String orderId,
      @JsonProperty("reference") String reference,
      @JsonProperty("type") String type,
      @JsonProperty("time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date time,
      @JsonProperty("reason") String reason) {
    super(type, orderId, time);
    this.reference = reference;
    this.reason = reason;
  }

  public String getReference() {
    return reference;
  }

  public String getReason() {
    return reason;
  }
}
