package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexSwapTransaction {

  @JsonProperty("trade_id")
  /** bill ID */
  private String tradeId;

  @JsonProperty("instrument_id")
  /** trading pair */
  private String instrumentId;

  /** price */
  private BigDecimal price;

  /** Quantity */
  @JsonProperty("order_qty")
  private BigDecimal orderQty;

  @JsonProperty("order_id")
  /** order ID */
  private String orderId;

  @JsonProperty("client_oid")
  private String clientOid;

  private String timestamp;

  /** Taker or Maker (T or M) */
  @JsonProperty("exec_type")
  private String execType;

  private BigDecimal fee;

  /** bills side (buy, sell or points_fee) */
  private SwapSide side;

  public Date getTimestamp() {
    return Date.from(Instant.parse(timestamp));
  }
}
