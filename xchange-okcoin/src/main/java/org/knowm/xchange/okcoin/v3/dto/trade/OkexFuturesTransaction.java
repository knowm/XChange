package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexFuturesTransaction {

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

  @JsonProperty("created_at")
  private String createdAt;

  /** Taker or Maker (T or M) */
  @JsonProperty("exec_type")
  private String execType;

  private BigDecimal fee;
  /** bills side (buy, sell or points_fee) */
  private Side side;

  public Date getCreatedAt() {
    return Date.from(Instant.parse(createdAt));
  }
}
