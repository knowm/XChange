package org.knowm.xchange.cryptofacilities.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

// {"order":"send","order_tag":"1","orderType":"lmt","symbol":"FI_XBTUSD_180511","side":"buy","size":1,"limitPrice":1.0,"cliOrdId":"my_another_client_id"
/** https://www.cryptofacilities.com/resources/hc/en-us/articles/360000547454-Batch-Order */
@JsonInclude(Include.NON_NULL)
public class OrderPlacement implements OrderCommand {
  /** Always send */
  public final String order = "send";
  /**
   * An arbitrary string provided client-side to tag the order for the purpose of mapping order
   * sending instructions to the API’s response
   */
  @JsonProperty("order_tag")
  public final String orderTag;
  /** either lmt for a limit order, post for a post-only limit order or stp for a stop order */
  public final OrderType orderType;
  /** The symbol of the Futures */
  public final String symbol;
  /** The direction of the order, either buy for a buy order or sell for a sell order */
  public final OrderSide side;
  /** The size associated with the order */
  public final BigDecimal size;
  /** The limit price associated with the order. Must not have more than 2 decimal places */
  public final BigDecimal limitPrice;
  /**
   * Optional. The stop price associated with a stop order. Required if orderType is stp. Must not
   * have more than 2 decimal places. Note that for stp orders, limitPrice is also required and
   * denotes the worst price at which the stp order can get filled
   */
  public final BigDecimal stopPrice;
  /** Optional. The order identity that is specified from the user. It must be globally unique. */
  public final String cliOrdId;

  public OrderPlacement(
      String orderTag,
      OrderType orderType,
      String symbol,
      OrderSide side,
      BigDecimal size,
      BigDecimal limitPrice,
      BigDecimal stopPrice,
      String cliOrdId) {
    this.orderTag = orderTag;
    this.orderType = orderType;
    this.symbol = symbol;
    this.side = side;
    this.size = size;
    this.limitPrice = limitPrice;
    this.stopPrice = stopPrice;
    this.cliOrdId = cliOrdId;
  }

  @Override
  public String toString() {
    return "OrderPlacement ["
        + (order != null ? "order=" + order + ", " : "")
        + (orderTag != null ? "orderTag=" + orderTag + ", " : "")
        + (orderType != null ? "orderType=" + orderType + ", " : "")
        + (symbol != null ? "symbol=" + symbol + ", " : "")
        + (side != null ? "side=" + side + ", " : "")
        + (size != null ? "size=" + size + ", " : "")
        + (limitPrice != null ? "limitPrice=" + limitPrice + ", " : "")
        + (stopPrice != null ? "stopPrice=" + stopPrice + ", " : "")
        + (cliOrdId != null ? "cliOrdId=" + cliOrdId : "")
        + "]";
  }
}
