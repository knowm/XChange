package org.knowm.xchange.coinsetter.dto.order.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinsetter.dto.CoinsetterResponse;

/**
 * Response of placing or canceling order.
 */
public class CoinsetterOrderResponse extends CoinsetterResponse {

  private final UUID uuid;
  private final String orderNumber;

  /**
   * @param uuid Order ID, if success
   * @param message If success, message will be "OK". Otherwise, it will be a description of the issue.
   * @param requestStatus Either "SUCCESS" or "FAILURE"
   * @param orderNumber Order number, if success
   */
  public CoinsetterOrderResponse(@JsonProperty("uuid") UUID uuid, @JsonProperty("message") String message,
      @JsonProperty("requestStatus") String requestStatus, @JsonProperty("orderNumber") String orderNumber) {

    super(message, requestStatus);
    this.uuid = uuid;
    this.orderNumber = orderNumber;
  }

  public UUID getUuid() {

    return uuid;
  }

  public String getOrderNumber() {

    return orderNumber;
  }

  @Override
  public String toString() {

    return "OrderResponse [uuid=" + uuid + ", orderNumber=" + orderNumber + "]";
  }

}
