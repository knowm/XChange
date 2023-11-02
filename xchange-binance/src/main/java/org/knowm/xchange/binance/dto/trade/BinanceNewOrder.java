package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public final class BinanceNewOrder {

  /** Desired response type for BinanceNewOrder. */
  public enum NewOrderResponseType {
    ACK,
    RESULT,
    FULL
  }


  /** Desired response side effect type for BinanceNewOrder. */
  public enum SideEffectType	 {
    NO_SIDE_EFFECT,
    MARGIN_BUY,
    AUTO_REPAY
  }

  public final String symbol;
  public final long orderId;
  public final String clientOrderId;
  public final long transactTime;
  public final BigDecimal price;
  public final BigDecimal origQty;
  public final BigDecimal executedQty;
  public final OrderStatus status;
  public final TimeInForce timeInForce;
  public Boolean isIsolated=false;
  public final OrderType type;
  public final OrderSide side;
  public final List<BinanceTrade> fills;

  public BinanceNewOrder(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("transactTime") long transactTime,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("origQty") BigDecimal origQty,
      @JsonProperty("executedQty") BigDecimal executedQty,
      @JsonProperty("status") OrderStatus status,
      @JsonProperty("timeInForce") TimeInForce timeInForce,
      @JsonProperty("type") OrderType type,
      @JsonProperty("isIsolated") Boolean isIsolated,
      @JsonProperty("side") OrderSide side,
      @JsonProperty("fills") List<BinanceTrade> fills) {
    super();
    this.symbol = symbol;
    this.orderId = orderId;
    this.clientOrderId = clientOrderId;
    this.transactTime = transactTime;
    this.price = price;
    this.origQty = origQty;
    this.executedQty = executedQty;
    this.status = status;
    this.timeInForce = timeInForce;
    this.isIsolated=isIsolated;
    this.type = type;
    this.side = side;
    this.fills = fills;
  }
}
