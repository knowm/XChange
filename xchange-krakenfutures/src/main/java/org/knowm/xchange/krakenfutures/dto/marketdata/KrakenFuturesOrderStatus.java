package org.knowm.xchange.krakenfutures.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.Util;

/** @author Panchen */
@Getter
@ToString
public class KrakenFuturesOrderStatus {

  private final Date receivedTime;
  private final String status;
  private final String orderId;

  public KrakenFuturesOrderStatus(
      @JsonProperty("receivedTime") String strReceivedTime,
      @JsonProperty("status") String status,
      @JsonProperty("order_id") String orderId) {

    this.receivedTime = Util.parseDate(strReceivedTime);
    this.status = status;
    this.orderId = orderId;
  }
}
