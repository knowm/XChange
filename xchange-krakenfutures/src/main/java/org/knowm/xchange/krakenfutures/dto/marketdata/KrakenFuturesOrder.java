package org.knowm.xchange.krakenfutures.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.Util;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Jean-Christophe Laruelle */
@Getter
@ToString
public class KrakenFuturesOrder extends KrakenFuturesResult {

  private final Date serverTime;
  private final KrakenFuturesOrderStatus orderStatus;
  private final String orderId;

  public KrakenFuturesOrder(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("sendStatus") KrakenFuturesOrderStatus orderStatus,
      @JsonProperty("error") String error,
      @JsonProperty("orderId") String orderId) {

    super(result, error);

    this.serverTime = Util.parseDate(strServerTime);
    this.orderStatus = orderStatus;
    this.orderId = orderStatus == null ? orderId : orderStatus.getOrderId();
  }
}
