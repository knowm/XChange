package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;

/** @author Panchen */
@Getter
@ToString
public class KrakenFuturesOrderStatus {

  private final Date receivedTime;
  private final String status;
  private final String orderId;

  public KrakenFuturesOrderStatus(
      @JsonProperty("receivedTime") Date receivedTime,
      @JsonProperty("status") String status,
      @JsonProperty("order_id") String orderId) {

    this.receivedTime = receivedTime;
    this.status = status;
    this.orderId = orderId;
  }
}
