package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexOpenOrder {

  @JsonProperty("client_oid")
  /** the order ID customised by yourself */
  private String clientOid;

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("filled_notional")
  /** amount filled */
  private BigDecimal filledNotional;

  @JsonProperty("filled_size")
  /** quantity filled */
  private BigDecimal filledSize;

  private String funds;

  @JsonProperty("instrument_id")
  /** trading pair */
  private String instrumentId;

  /** the total buying amount. This value will be returned for market orders */
  private BigDecimal notional;

  @JsonProperty("order_id")
  /** order ID */
  private String orderId;

  @JsonProperty("order_type")
  /** 0: Normal limit order 1: Post only 2: Fill Or Kill 3: Immediatel Or Cancel */
  private OrderPlacementType orderType;

  /** price */
  private BigDecimal price;

  @JsonProperty("price_avg")
  /** Average price */
  private BigDecimal priceAvg;

  @JsonProperty("product_id")
  private String productId;

  /** buy or sell */
  private Side side;

  /** quantity */
  private BigDecimal size;

  /**
   * Order Status("-2":Failed,"-1":Cancelled,"0":Open ,"1":Partially Filled, "2":Fully
   * Filled,"3":Submitting,"4":Cancelling,）
   */
  private String state;

  /** order status */
  private String status;

  private String timestamp;

  /** limit,market(defaulted as limit) */
  private String type;

  public Date getCreatedAt() {
    return Date.from(Instant.parse(createdAt));
  }

  public Date getTimestamp() {
    return Date.from(Instant.parse(timestamp));
  }
}
