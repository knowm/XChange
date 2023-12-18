package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexTransaction {

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("exec_type")
  private String execType;

  private BigDecimal fee;

  @JsonProperty("instrument_id")
  /** trading pair */
  private String instrumentId;

  @JsonProperty("ledger_id")
  /** bill ID */
  private String ledgerId;

  /** liquidity side (T or M) */
  private String liquidity;

  @JsonProperty("order_id")
  /** order ID */
  private String orderId;

  /** price */
  private BigDecimal price;

  @JsonProperty("product_id")
  private String productId;

  /** bills side (buy, sell or points_fee) */
  private Side side;

  /** quantity */
  private BigDecimal size;

  /** create date */
  private String timestamp;

  public Date getCreatedAt() {
    return Date.from(Instant.parse(createdAt));
  }

  public Date getTimestamp() {
    return Date.from(Instant.parse(timestamp));
  }
}
