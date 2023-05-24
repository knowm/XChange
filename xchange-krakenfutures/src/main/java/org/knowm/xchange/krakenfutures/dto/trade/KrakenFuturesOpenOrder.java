package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Jean-Christophe Laruelle */
@Getter
@ToString
public class KrakenFuturesOpenOrder extends KrakenFuturesResult {

  private final Date receivedTime;
  private final KrakenFuturesOrderStatus status;
  private final String orderId;
  private final KrakenFuturesOrderType orderType;
  private final String symbol;
  private final KrakenFuturesOrderSide side;
  private final BigDecimal unfilledSize;
  private final BigDecimal filledSize;
  private final BigDecimal limitPrice;
  private final BigDecimal stopPrice;
  private final boolean isReduceOnly;

  public KrakenFuturesOpenOrder(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("receivedTime") Date receivedTime,
      @JsonProperty("status") KrakenFuturesOrderStatus status,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("orderType") KrakenFuturesOrderType orderType,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") KrakenFuturesOrderSide side,
      @JsonProperty("unfilledSize") BigDecimal unfilledSize,
      @JsonProperty("filledSize") BigDecimal filledSize,
      @JsonProperty("limitPrice") BigDecimal limitPrice,
      @JsonProperty("stopPrice") BigDecimal stopPrice,
      @JsonProperty("reduceOnly") boolean isReduceOnly) {

    super(result, error);

    this.receivedTime = receivedTime;
    this.status = status;
    this.orderId = orderId;
    this.orderType = orderType;
    this.symbol = symbol;
    this.side = side;
    this.unfilledSize = unfilledSize;
    this.filledSize = filledSize;
    this.limitPrice = limitPrice;
    this.stopPrice = stopPrice;
    this.isReduceOnly = isReduceOnly;
  }
}
